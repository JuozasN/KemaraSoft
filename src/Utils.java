import java.nio.ByteBuffer;
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
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static int byteToInt(byte value){return (value&0xFF);}

    public static byte intToByte(int value){return (byte)(value&0xFF);}

    public static String bytesToHexString(byte[] bytes, int strLen) {
        char[] hexChars = new char[bytes.length * 2]; // 1 byte has values in range [0, FF]
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = byteToInt(bytes[j]);
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        String str = "";
        for(int i = strLen; i > 0; --i) {
            str += String.valueOf(hexChars[hexChars.length-i]);
        }

        return str;
    }

    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2]; // 1 byte has values in range [0, FF]
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = byteToInt(bytes[j]);
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }

    public static int getRandomInt(int min, int max) {
        SecureRandom random = new SecureRandom();

        return (random.nextInt(max-min+1) +  min);
    }

    public static Integer[] getRandIndexes(Integer[] unassignedMemBlocks, int blocks) {
        ArrayList<Integer> unassignedArr = new ArrayList<>(Arrays.asList(unassignedMemBlocks));
        Integer[] indexes = new Integer[blocks];
        int index;
        for(int i = 0; i < blocks; ++i) {
            index = getRandomInt(0, unassignedArr.size()-1);
            indexes[i] = unassignedArr.get(index);
            unassignedArr.remove(index);
        }

        return indexes;
    }

    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static int byteArrayToInt(byte[] value) {
        return ByteBuffer.wrap(value).getInt();
    }

    public static byte[] addToByteArray(byte[] byteArray, int addValue) {
        int valueResult = byteArrayToInt(byteArray) + addValue;
        return intToByteArray(valueResult);
    }
}
