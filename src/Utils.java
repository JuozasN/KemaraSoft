import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final byte VM_MEM_BLOCK_COUNT = 0x10;      // virtual machine memory block count
    public static final short EM_BLOCK_COUNT = 0x100;         // external memory block count
    public static final byte UM_BLOCK_COUNT = 0x40;          // user memory block count
    public static final byte KM_BLOCK_COUNT = 0x40;          // user memory block count
    public static final byte BLOCK_WORD_COUNT = 0x10;        // word count in block
    public static final byte WORD_BYTE_COUNT = 0x4;               // byte count in word
    public static String INITIAL_REG_VAL_STR = "0000";
    public static final String TASK_BEGIN = "$BGN";
    public static final String TASK_END = "$END";
    public static final String EM_FILE_PATH = "res/external_memory.txt";
    private final static char[] HEX_CHAR_VALUES = "0123456789ABCDEF".toCharArray();

    public static int byteToInt(byte value){return (value&0xFF);}

    public static byte intToByte(int value){return (byte)(value&0xFF);}

    public static String intToHexString(Integer value) {
        return String.format("%04X", value & 0xFFFF);
        //return value.toHexString(value).toUpperCase();
    }

    public static String shortToHexString(Short value) {
        return String.format("%04X", value & 0xFFFF);
    }

    public static String byteToHexString(byte value) {
        char[] hexChars = new char[2];
        int v = byteToInt(value);
        hexChars[0] = HEX_CHAR_VALUES[v >>> 4];
        hexChars[1] = HEX_CHAR_VALUES[v & 0x0F];
        return new String(hexChars);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) sb.append(byteToHexString(aByte));

        return sb.toString();
    }

    public static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes)
            sb.append((char) aByte);

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

    public static Byte[] getRandIndexes(Byte[] byteArray, byte blocks) {
        ArrayList<Byte> byteArrayList = new ArrayList<>(Arrays.asList(byteArray));
        Byte[] indexes = new Byte[blocks];
        byte index;
        for(byte i = 0; i < blocks; ++i) {
            index = (byte) getRandomInt(0, byteArrayList.size()-1);
            indexes[i] = byteArrayList.get(index);
            byteArrayList.remove(index);
        }

        return indexes;
    }

    public static byte[] intToByteArray(int value) {
        //return ByteBuffer.allocate(4).putInt(value).array();
        BigInteger bigInt = BigInteger.valueOf(value);
        return bigInt.toByteArray();
    }

    public static int byteArrayToInt(byte[] value) {
        if (value == null)
            return -1;

        byte arrLen = (byte) value.length;
        int intValue = value[arrLen-1] & 255;
        for(byte i = (byte) (arrLen-2); i >= 0; --i)
            intValue |= (value[i] & 255) << (8 * (arrLen-i-1));

        return intValue;
    }

//    public static int byteArrayToInt(byte[] value) {
//        return ByteBuffer.wrap(value).getInt();
//    }

    public static short byteArrayToShort(byte[] value) {
        return ByteBuffer.wrap(value).getShort();
    }

    public static byte[] addToByteArray(byte[] byteArray, int addValue) {
        int valueResult = byteArrayToInt(byteArray) + addValue;
        return intToByteArray(valueResult);
    }

    public static short getPositionByPriority(ArrayList<Process> processList, Process process) {
        /// implementation :)
        return -1;
    }

    public static void addByPriority(Process process){
        byte processPriority = process.getPriority();
        int index = 0;
        for(Process p: OS.processList){
            if(p.getPriority() < processPriority) break;
            index++;
        }
        OS.processList.add(index, process);
    }

    public static void addByPriority(ArrayList<Process> processList, Process process){
        byte processPriority = process.getPriority();
        int index = 0;
        for(Process p: processList){
            if(p.getPriority() < processPriority) break;
            index++;
        }
        processList.add(index, process);
    }
}
