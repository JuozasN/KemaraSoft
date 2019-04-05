import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {
    public static final int VM_MEM_BLOCK_COUNT = 0x10;      // virtual machine memory block count
    public static final int EM_BLOCK_COUNT = 0x100;         // external memory block count
    public static final int UM_BLOCK_COUNT = 0x40;          // user memory block count
    public static final int BLOCK_WORD_COUNT = 0x10;        // word count in block
    public static final int WORD_BYTE_COUNT = 0x4;               // byte count in word
    public static final String TASK_BEGIN = "$BGN";
    public static final String TASK_END = "$END";
    public static final String EM_FILE_PATH = "res/external_memory.txt";

    public static int byteToInt(byte value){return (int)(value&0xFF);}

    public static byte intToByte(int value){return (byte)(value&0xFF);}

    public static int getRandomInt(int min, int max) {
        SecureRandom random = new SecureRandom();

        return (random.nextInt(max-min+1) +  min);
    }

    public static Integer[] getRandIndexes(Integer[] unassignedMemBlocks, int blocks) {
        ArrayList<Integer> unassignedArr = new ArrayList<>(Arrays.asList(unassignedMemBlocks));
        Integer[] indexes = new Integer[blocks];
        int index;
        for(int i = 0; i < blocks; ++i) {
            index = getRandomInt(0, unassignedArr.size());
            indexes[i] = index;
            unassignedArr.remove(index);
        }

        return indexes;
    }
}
