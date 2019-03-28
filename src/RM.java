import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RM {
    public static final int UM_BLOCK_COUNT = 0x40;
    private RandomAccessFile externalMemoryFile;
    // user memory array
    private byte ptr;
    private byte sp;
    private byte pc;
    private byte pi;
    private byte si;
    private byte ti;
    private boolean isUserMode;

    public RM() {
        this.ptr = 0;
        this.sp = 0;
        this.pc = 0;
        this.pi = 0;
        this.si = 0;
        this.ti = 0;
        this.isUserMode = true;

        initializeExternalMemoryFile();
    }

    private void initializeExternalMemoryFile() {
        File memFile = new File(Utils.EM_FILE_PATH);
        try {
            if (memFile.createNewFile()) {
                externalMemoryFile = new RandomAccessFile(memFile, "rw");
                try {
                    externalMemoryFile.setLength(Utils.EM_BLOCK_COUNT * Block.WORD_COUNT * Word.BYTE_COUNT);
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

    // write byte data to externam memory file at specified position
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

        if (wordBytes.length < Word.BYTE_COUNT) {
            for(int i = wordBytes.length; i < Word.BYTE_COUNT; ++i)
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
            for (int i = 0; i < Word.BYTE_COUNT; ++i) {
                System.out.print(externalMemoryFile.readChar());
            }
        } catch (IOException e) {
            System.err.println("Cannot seek word at block " + block + ", word " + word);
            e.printStackTrace();
        }
    }

    private int getBytePosition(int block, int word) {
        return block * Block.WORD_COUNT * Word.BYTE_COUNT + word * Word.BYTE_COUNT;
    }

    // gets a word from random access file
    private Word getWordEM(int block, int word) {
        byte[] wordBytes = new byte[Word.BYTE_COUNT];
        try {
            externalMemoryFile.seek(getBytePosition(block, word));
            for (int i = 0; i < Word.BYTE_COUNT; ++i)
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
