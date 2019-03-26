import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

public class RM {
    private static final String EXTERNAL_MEM_FILE_PATH = "res/external_memory.txt";
    public static final int USER_MEMORY_BLOCK_COUNT = 0x40;
    public static final int EXTERNAL_MEMORY_BLOCK_COUNT = 0x100;
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
        File memFile = new File(EXTERNAL_MEM_FILE_PATH);
        try {
            if (memFile.createNewFile()) {
                externalMemoryFile = new RandomAccessFile(memFile, "rw");
                try {
                    externalMemoryFile.setLength(EXTERNAL_MEMORY_BLOCK_COUNT * Block.WORD_COUNT * Word.BYTE_COUNT);
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

    public void printExternalMemory() {
        try {
            System.out.println(externalMemoryFile.readLine());
        } catch(IOException e) {
            System.out.println("Error reading external memory file");
            e.printStackTrace();
        }
    }
}
