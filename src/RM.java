import javafx.scene.control.Tab;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RM {
    public final class RegisterIndexes {
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
    private byte ptr;       // puslapių lentelės registras
    private byte sp;        // steko viršūnės žodžio indeksas
    private byte pc;        // komandų skaitliukas
    private byte pi;        // programinių pertraukimų registras
    private byte si;        // supervizorinių pertraukimų registras
    private byte ti;        // taimerio registras
    private boolean isUserMode; // registras, kurio reikšmė nusako procesoriaus darbo režimą
    private boolean[] assignedMemBlocks;

    public RM(TableController controller) {
        this.controller = controller;
        this.um = new Block[Utils.UM_BLOCK_COUNT];
        for(int i = 0; i < Utils.UM_BLOCK_COUNT; ++i)
            um[i] = new Block();
        this.ptr = 0;
        this.sp = 0;
        this.pc = 0;
        this.pi = 0;
        this.si = 0;
        this.ti = 0;
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

    public void setPTR(byte value){
        ptr = value;
        controller.setRMRegValue(RegisterIndexes.PTR, String.format("%02X", Utils.byteToInt(ptr)));
    }

    public void incrementSP(){
        ++sp;
        controller.setRMRegValue(RegisterIndexes.SP, String.format("%02X", Utils.byteToInt(sp)));
    }

    public void decrementSP(){
        --sp;
        controller.setRMRegValue(RegisterIndexes.SP, String.format("%02X", Utils.byteToInt(sp)));
    }

    public void setPC(byte value){
        pc = value;
        controller.setRMRegValue(RegisterIndexes.PC, String.format("%02X", Utils.byteToInt(pc)));
    }

    public void setPI(byte value){
        pi = value;
        controller.setRMRegValue(RegisterIndexes.PI, String.format("%02X", Utils.byteToInt(pi)));
    }

    public void setSI(byte value){
        si = value;
        controller.setRMRegValue(RegisterIndexes.SI, String.format("%02X", Utils.byteToInt(si)));
    }

    public void setTI(byte value){
        ti = value;
        controller.setRMRegValue(RegisterIndexes.TI, String.format("%02X", Utils.byteToInt(ti)));
    }

    public void toggleMode(){
        if (this.isUserMode) {
            this.isUserMode = false;
            controller.setRMRegValue(RegisterIndexes.MODE, "KRNL");
        } else {
            this.isUserMode = true;
            controller.setRMRegValue(RegisterIndexes.MODE, "USER");
        }

    }

    public void setValue(byte[] value, byte adr){
        int row = Utils.byteToInt(adr)/Utils.BLOCK_WORD_COUNT;
        int col = Utils.byteToInt(adr)%Utils.BLOCK_WORD_COUNT;
        um[row].setWord(value, col);
        controller.setRMMemValue(row, col, new String(value));
    }

    public Word getValue(byte adr){
        int row = Utils.byteToInt(adr)/Utils.BLOCK_WORD_COUNT;
        int col = Utils.byteToInt(adr)%Utils.BLOCK_WORD_COUNT;
        return um[row].getWord(col);
    }

    public Integer[] getRandUnassignedBlocks(int blocks) {
        Integer[] unassignedMemBlocks = getUnassignedMemBlocks();
        return Utils.getRandIndexes(unassignedMemBlocks, blocks);
    }

    public int getRandUnassignedBlockIndex() {
        Integer[] unassignedMemBlocks = getUnassignedMemBlocks();
        return Utils.getRandomInt(0, unassignedMemBlocks.length);
    }

    public void assignBlocks(Integer[] indexes) {
        for(int i : indexes) {
            assignedMemBlocks[i] = true;
        }
    }

    public void assignBlock(int index) {
        assignedMemBlocks[index] = true;
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

    public void setPagingTable(byte tableAdr, Integer[] memBlocks) {
        // if registers are not empty -> push RM state to stack (implement in MOS)
        setPTR(tableAdr);

        byte[] value = new byte[1];
        for (int i = 0; i < memBlocks.length; ++i) {
            value[0] = Paging.toWordAdr(memBlocks[i]);
            setValue(value, Utils.intToByte(tableAdr + i));
        }
    }

    public byte[] getPagingTable() {
        byte[] pagingTable = new byte[Utils.BLOCK_WORD_COUNT];

        for(int i = 0; i < pagingTable.length; ++i) {
            pagingTable[i] = getValue(Utils.intToByte(ptr + i)).getBytes()[0];
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
