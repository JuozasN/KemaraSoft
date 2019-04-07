import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RM {
    public final class RMRegIndexes {
        public static final int PTR = 0;
        public static final int SP = 1;
        public static final int PC = 2;
        public static final int PI = 3;
        public static final int SI = 4;
        public static final int TI = 5;
        public static final int MODE = 6;
    }

    private final TableController controller;
    private RandomAccessFile externalMemoryFile;
    private Block[] um;     // user memory
    private byte[] ptr = new byte[2];       // puslapių lentelės registras
    private byte[] sp = new byte[2];        // steko viršūnės žodžio indeksas
    private byte[] pc = new byte[2];        // komandų skaitliukas
    private byte[] pi = new byte[2];        // programinių pertraukimų registras
    private byte[] si = new byte[2];        // supervizorinių pertraukimų registras
    private byte[] ti = new byte[2];        // taimerio registras
    private boolean isUserMode; // registras, kurio reikšmė nusako procesoriaus darbo režimą
    private boolean[] assignedMemBlocks;

    public RM(TableController controller) {
        this.controller = controller;
        this.um = new Block[Utils.UM_BLOCK_COUNT];
        for(int i = 0; i < Utils.UM_BLOCK_COUNT; ++i)
            um[i] = new Block();

        this.isUserMode = true;
        this.assignedMemBlocks = new boolean[Utils.UM_BLOCK_COUNT];

        //initializeExternalMemoryFile();
    }

    private void initializeExternalMemoryFile() {
        File memFile = new File(Utils.EM_FILE_PATH);
        try {
            if (memFile.createNewFile()) {
                externalMemoryFile = new RandomAccessFile(memFile, "rw");
                try {
                    externalMemoryFile.setLength(Utils.EM_BLOCK_COUNT * Utils.BLOCK_WORD_COUNT * Utils.WORD_BYTE_COUNT);
                } catch(IOException e) {
                    System.err.println("Error creating external random access file");
                    e.printStackTrace();
                }
            } else {
                externalMemoryFile = new RandomAccessFile(memFile, "rw");
            }
        } catch (IOException e) {
            System.err.println("Error creating external file");
            e.printStackTrace();
        }
    }

    public void reset() {
        resetRegisters();
        freeBlocks();
        // resetAssignedMemory(); << user memory resets while reseting VM memory
    }

    private void resetRegisters() {
        resetPTR();
        resetSP();
        setPC(0);
        setPI(0);
        setSI(0);
        setTI(0);
    }

    private void resetAssignedMemory() {
        // reset only assigned memory blocks
        Integer[] assignedBlockIndexes = getAssignedMemBlocks();
        for(int i = 0; i < assignedBlockIndexes.length; ++i) {
            for(int j = 0; j < Utils.BLOCK_WORD_COUNT; ++j) {
                setValue(0, assignedBlockIndexes[i], j);
            }
            assignedMemBlocks[i] = false;
        }
    }

    public void setPTR(int value){
        ptr = Utils.intToByteArray(value);
        controller.setRMRegValue(RMRegIndexes.PTR, Utils.bytesToHexString(ptr, 4));
    }

    private void resetPTR() {
        for(int i = 0; i < Utils.BLOCK_WORD_COUNT; ++i)
            setValue(0, Utils.byteArrayToInt(ptr) + i);

        setPTR(0);
    }

    public void incrementSP(){
        sp = Utils.addToByteArray(sp, 1);
        controller.setRMRegValue(RMRegIndexes.SP, Utils.bytesToHexString(ptr, 4));
    }

    public void decrementSP(){
        sp = Utils.addToByteArray(sp, -1);
        controller.setRMRegValue(RMRegIndexes.SP, Utils.bytesToHexString(ptr, 4));
    }

    private void resetSP() {
        sp = Utils.intToByteArray(0);
        controller.setRMRegValue(RMRegIndexes.SP, Utils.bytesToHexString(sp, 4));
    }

    public void setPC(int value){
        pc = Utils.intToByteArray(value);
        controller.setRMRegValue(RMRegIndexes.PC, Utils.bytesToHexString(ptr, 4));
    }

    public void setPI(int value){
        pi = Utils.intToByteArray(value);
        controller.setRMRegValue(RMRegIndexes.PI, Utils.bytesToHexString(ptr, 4));
    }

    public void setSI(int value){
        si = Utils.intToByteArray(value);
        controller.setRMRegValue(RMRegIndexes.SI, Utils.bytesToHexString(ptr, 4));
    }

    public void setTI(int value){
        ti = Utils.intToByteArray(value);
        controller.setRMRegValue(RMRegIndexes.TI, Utils.bytesToHexString(ptr, 4));
    }

    public void toggleMode(){
        if (this.isUserMode) {
            this.isUserMode = false;
            controller.setRMRegValue(RMRegIndexes.MODE, "KRNL");
        } else {
            this.isUserMode = true;
            controller.setRMRegValue(RMRegIndexes.MODE, "USER");
        }

    }

    public void setValue(byte[] value, int adr){
        int block = adr/Utils.BLOCK_WORD_COUNT;
        int word = adr%Utils.BLOCK_WORD_COUNT;
        um[block].setWord(value, word);
        controller.setRMMemValue(block, word, Utils.bytesToHexString(value, 4));
    }

    public void setValue(int value, int adr) {
        setValue(Utils.intToByteArray(value), adr);
    }

    private void setValue(int value, int block, int word) {
        byte[] byteValue = Utils.intToByteArray(value);
        um[block].setWord(byteValue, word);
        controller.setRMMemValue(block, word, Utils.bytesToHexString(byteValue, 4));
    }

    public Word getValue(int adr){
        int row = adr/Utils.BLOCK_WORD_COUNT;
        int col = adr%Utils.BLOCK_WORD_COUNT;
        return um[row].getWord(col);
    }

    public void assignBlocks(Integer[] indexes) {
        for(int i : indexes) {
            assignedMemBlocks[i] = true;
        }
    }

    private void freeBlocks(Integer[] indexes) {
        for(int i : indexes) {
            freeBlock(i);
        }
    }

    private void freeBlocks() {
        for(int i = 0; i < assignedMemBlocks.length; ++i) {
            freeBlock(i);
        }
    }

    public void assignBlock(int index) {
        assignedMemBlocks[index] = true;
    }

    private void freeBlock(int index) {
        assignedMemBlocks[index] = false;
    }

    public Integer[] getRandUnassignedBlocks(int blocks) {
        Integer[] unassignedMemBlocks = getUnassignedMemBlocks();
        return Utils.getRandIndexes(unassignedMemBlocks, blocks);
    }

    public int getRandUnassignedBlockIndex() {
        Integer[] unassignedMemBlocks = getUnassignedMemBlocks();
        int index = Utils.getRandomInt(0, unassignedMemBlocks.length-1);
        return unassignedMemBlocks[index];
    }

    private Integer[] getAssignedMemBlocks() {
        ArrayList<Integer> assignedBlocks = new ArrayList<>();
        for(int i = 0; i < assignedMemBlocks.length; ++i) {
            if(assignedMemBlocks[i])
                assignedBlocks.add(i);
        }

        Integer[] intArr = new Integer[assignedBlocks.size()];
        return assignedBlocks.toArray(intArr);
    }

    private Integer[] getUnassignedMemBlocks() {
        ArrayList<Integer> unassignedBlocks = new ArrayList<>();
        for (int i = 0; i < assignedMemBlocks.length; ++i) {
            if (!assignedMemBlocks[i])
                unassignedBlocks.add(i);
        }

        Integer[] intArr = new Integer[unassignedBlocks.size()];
        return unassignedBlocks.toArray(intArr);
    }

    public void setPagingTable(int tableAdr, Integer[] memBlocks) {
        // if registers are not empty -> push RM state to stack (implement in MOS)
        int tableWordAdr = Paging.toWordAdr(tableAdr);
        setPTR(tableWordAdr);

        byte[] value;
        for (int i = 0; i < memBlocks.length; ++i) {
            value = Utils.intToByteArray(memBlocks[i]);
            setValue(value, tableWordAdr + i);
        }
    }

    public byte[] getPagingTable() {
        byte[] pagingTable = new byte[Utils.BLOCK_WORD_COUNT];

        int ptrInt = Utils.byteArrayToInt(ptr);
        for(int i = 0; i < pagingTable.length; ++i) {
            // Last array byte holds block index because max value of index does not exceed 0xFF
            pagingTable[i] = getValue(ptrInt + i).getBytes()[Utils.WORD_BYTE_COUNT-1];
        }

        return pagingTable;
    }

    // write byte data to external memory file at specified position
    public void writeWordEM(Word data, int blockPos, int wordPos) {
        try {
            externalMemoryFile.seek(getBytePosition(blockPos, wordPos));
            writeWord(data);
        } catch (IOException e) {
            System.err.println("Cannot seek word at block " + blockPos + ", word " + wordPos);
            e.printStackTrace();
        }
    }

    private void writeWord(Word data) throws IOException {
        byte[] wordBytes = data.getBytes();
        for (byte b : wordBytes)
            externalMemoryFile.writeByte(b);

        if (wordBytes.length < Utils.WORD_BYTE_COUNT) {
            for(int i = wordBytes.length; i < Utils.WORD_BYTE_COUNT; ++i)
                externalMemoryFile.writeByte(0);
        }
    }

    public void printExternalMemory() {
        try {
            System.out.println(externalMemoryFile.readLine());
        } catch(IOException e) {
            System.out.println("Error reading external memory file");
            e.printStackTrace();
        }
    }

    public void printWordEM(int block, int word) {
        try {
            externalMemoryFile.seek(getBytePosition(block, word));
            for (int i = 0; i < Utils.WORD_BYTE_COUNT; ++i) {
                System.out.print(externalMemoryFile.readChar());
            }
        } catch (IOException e) {
            System.err.println("Cannot seek word at block " + block + ", word " + word);
            e.printStackTrace();
        }
    }

    private int getBytePosition(int block, int word) {
        return block * Utils.BLOCK_WORD_COUNT * Utils.WORD_BYTE_COUNT + word * Utils.WORD_BYTE_COUNT;
    }

    // gets a word from random access file
    private Word getWordEM(int block, int word) {
        byte[] wordBytes = new byte[Utils.WORD_BYTE_COUNT];
        try {
            externalMemoryFile.seek(getBytePosition(block, word));
            for (int i = 0; i < Utils.WORD_BYTE_COUNT; ++i)
                wordBytes[i] = externalMemoryFile.readByte();

        } catch (IOException e) {
            System.err.println("Cannot seek word at block " + block + ", word " + word);
            e.printStackTrace();
        }
        return new Word(wordBytes);
    }

    private void writeTask() {

    }
}
