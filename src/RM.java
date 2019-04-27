import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RM {
    public final class RMRegIndexes {
        public static final byte PTR = 0;
        public static final byte SP = 1;
        public static final byte PC = 2;
        public static final byte PI = 3;
        public static final byte SI = 4;
        public static final byte TI = 5;
        public static final byte MODE = 6;
    }

    private final TableController controller;
    private RandomAccessFile externalMemoryFile;
    private Block[] um;         // user memory
    private Short ptr;        // puslapių lentelės registras
    private Short sp;         // steko viršūnės žodžio indeksas
    private Short pc;         // komandų skaitliukas
    private byte pi;         // programinių pertraukimų registras
    private byte si;         // supervizorinių pertraukimų registras
    private byte ti;         // taimerio registras
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
        //resetSP();
        //setPC(0);
        setPI((byte) 0);
        setSI((byte) 0);
        setTI((byte) 0);
    }

    private void resetAssignedMemory() {
        // reset only assigned memory blocks
        Byte[] assignedBlockIndexes = getAssignedMemBlocks();
        for(byte i = 0; i < assignedBlockIndexes.length; ++i) {
            for(byte j = 0; j < Utils.BLOCK_WORD_COUNT; ++j) {
                setValue(0, assignedBlockIndexes[i], j);
            }
            assignedMemBlocks[i] = false;
        }
    }

    public void setPTR(Short value){
        ptr = value;
        controller.setRMRegValue(RMRegIndexes.PTR, Utils.shortToHexString(ptr));
    }

    public Short getPTR() {
        return this.ptr;
    }

    public void resetPTR() {
        for(int i = 0; i < Utils.BLOCK_WORD_COUNT; ++i)
            setValue(0, (short) (ptr + i));

        setPTR((short) 0);
    }

    public Short getSP() {
        return this.sp;
    }

    public void incrementSP(){
        ++sp;
        //controller.setRMRegValue(RMRegIndexes.SP, Utils.bytesToHexString(sp, 4));
    }

    public void decrementSP(){
        --sp;
        //controller.setRMRegValue(RMRegIndexes.SP, Utils.bytesToHexString(sp, 4));
    }

    public void resetSP() {
        sp = 0;
        //controller.setRMRegValue(RMRegIndexes.SP, Utils.bytesToHexString(sp, 4));
    }

    private void setSP(Short value) {
        sp = value;
    }

    public Short getPC() {
        return this.pc;
    }

    public void setPC(Short value){
        pc = value;
        //controller.setRMRegValue(RMRegIndexes.PC, Utils.bytesToHexString(pc, 4));
    }

    public void setPI(byte value){
        pi = value;
        controller.setRMRegValue(RMRegIndexes.PI, Utils.byteToHexString(pi));
    }

    public void setSI(byte value){
        si = value;
        controller.setRMRegValue(RMRegIndexes.SI, Utils.byteToHexString(si));
    }

    public void setTI(byte value){
        ti = value;
        controller.setRMRegValue(RMRegIndexes.TI, Utils.byteToHexString(ti));
    }

    public void decrementTI(){
        ti--;
        controller.setRMRegValue(RMRegIndexes.TI, Utils.byteToHexString(ti));
    }

    public byte getPI(){return this.pi;}
    public byte getSI(){return this.si;}
    public byte getTI(){return this.ti;}

    public void setReg(byte regIndex, Short value) {
        switch(regIndex) {
            //case RMRegIndexes.PTR:
            //    setPTR(value);
            case RMRegIndexes.SP:
                setSP(value);
            case RMRegIndexes.PC:
                setPC(value);
//            case RMRegIndexes.PI:
//                setPI(value);
//            case RMRegIndexes.SI:
//                setSI(value);
//            case RMRegIndexes.TI:
//                setTI(value);
        }
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

    public void setValue(Integer value, Short adr){
        byte block = (byte) (adr/Utils.BLOCK_WORD_COUNT);
        byte word = (byte) (adr%Utils.BLOCK_WORD_COUNT);
        um[block].setWord(word, value);
        controller.setRMMemValue(block, word, Utils.intToHexString(value));
    }

    private void setValue(Integer value, byte block, byte word) {
        um[block].setWord(word, value);
        controller.setRMMemValue(block, word, Utils.intToHexString(value));
    }

    public Word getValue(Short adr){
        byte block = (byte) (adr/Utils.BLOCK_WORD_COUNT);
        byte word = (byte) (adr%Utils.BLOCK_WORD_COUNT);
        return um[block].getWord(word);
    }

    public void assignBlocks(Byte[] indexes) {
        for(int i : indexes) {
            assignBlock(i);
        }
    }

    private void freeBlocks(Byte[] indexes) {
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

    public Byte[] getRandUnassignedBlocks(byte blocks) {
        Byte[] unassignedMemBlocks = getUnassignedMemBlocks();
        return Utils.getRandIndexes(unassignedMemBlocks, blocks);
    }

    public byte getRandUnassignedBlockIndex() {
        Byte[] unassignedMemBlocks = getUnassignedMemBlocks();
        byte index = (byte) Utils.getRandomInt(0, unassignedMemBlocks.length-1);
        return unassignedMemBlocks[index];
    }

    private Byte[] getAssignedMemBlocks() {
        ArrayList<Byte> assignedBlocks = new ArrayList<>();
        for(byte i = 0; i < assignedMemBlocks.length; ++i) {
            if(assignedMemBlocks[i])
                assignedBlocks.add(i);
        }

        Byte[] byteArr = new Byte[assignedBlocks.size()];
        return assignedBlocks.toArray(byteArr);
    }

    private Byte[] getUnassignedMemBlocks() {
        ArrayList<Byte> unassignedBlocks = new ArrayList<>();
        for (byte i = 0; i < assignedMemBlocks.length; ++i) {
            if (!assignedMemBlocks[i])
                unassignedBlocks.add(i);
        }

        Byte[] byteArr = new Byte[unassignedBlocks.size()];
        return unassignedBlocks.toArray(byteArr);
    }

    public void setPagingTable(byte tableAdr, Byte[] pagingTable) {
        Paging.setPagingTable(pagingTable);
        // if registers are not empty -> push RM state to stack (implement in MOS)
        Short tableWordAdr = Paging.getWordAdr(tableAdr);
        if (tableWordAdr == null) {
            System.err.println("ERROR getting paging table word address");
            return;
        }

        setPTR(tableWordAdr);

        byte value;
        for (byte i = 0; i < pagingTable.length; ++i) {
            value = pagingTable[i];
            setValue((int) value, (short) (tableWordAdr + i));
        }
    }

    public Byte[] getPagingTable() {
        Byte[] pagingTable = new Byte[Utils.BLOCK_WORD_COUNT];

        for(byte i = 0; i < pagingTable.length; ++i) {
            // can cast to byte because max value of index does not exceed 0xFF
            pagingTable[i] = (byte) getValue((short) (ptr + i)).getValue();
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
        int intValue = data.getValue();
        byte[] byteValue = Utils.intToByteArray(intValue);
        for (byte b : byteValue)
            externalMemoryFile.writeByte(b);

        if (byteValue.length < Utils.WORD_BYTE_COUNT) {
            for(int i = byteValue.length; i < Utils.WORD_BYTE_COUNT; ++i)
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
        return new Word(Utils.byteArrayToInt(wordBytes));
    }

    private void writeTask() {

    }
}
