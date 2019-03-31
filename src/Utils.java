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

    public static byte strToByte(String str){
        if(str.length() > 2); // Invalid adress interrupt here
        if(str.length() == 1)
            str = "0" + str;
        return((byte) ((Character.digit(str.charAt(0), 16) << 4)
                + Character.digit(str.charAt(1), 16)));
    }
}
