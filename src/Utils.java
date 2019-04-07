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
    private final static char[] HEX_CHAR_VALUES = "0123456789ABCDEF".toCharArray();

    public static int byteToInt(byte value){return (value&0xFF);}

    public static byte intToByte(int value){return (byte)(value&0xFF);}

    public static String byteToHexString(byte value) {
        char[] hexChars = new char[2];
        int v = byteToInt(value);
        hexChars[0] = HEX_CHAR_VALUES[v >>> 4];
        hexChars[1] = HEX_CHAR_VALUES[v & 0x0F];
        return new String(hexChars);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; ++i)
            sb.append(byteToHexString(bytes[i]));

        return sb.toString();
    }

    // strLen specifies length of hex bytes string to return
    public static String bytesToHexString(byte[] bytes, int strLen) {
        int maxBytesStrLen = bytes.length * 2;
        if (maxBytesStrLen < strLen) {
            strLen = maxBytesStrLen;
        }

        String hexString = bytesToHexString(bytes);

        int hexLen = hexString.length();
        return hexString.substring(hexLen - strLen, hexLen);
    }

    public static int getRandomInt(int min, int max) {
        SecureRandom random = new SecureRandom();

        return (random.nextInt(max-min+1) +  min);
    }

    public static Integer[] getRandIndexes(Integer[] integerArray, int blocks) {
        ArrayList<Integer> integerArrayList = new ArrayList<>(Arrays.asList(integerArray));
        Integer[] indexes = new Integer[blocks];
        int index;
        for(int i = 0; i < blocks; ++i) {
            index = getRandomInt(0, integerArrayList.size()-1);
            indexes[i] = integerArrayList.get(index);
            integerArrayList.remove(index);
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
