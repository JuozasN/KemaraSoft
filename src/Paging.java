import jdk.jshell.execution.Util;

public class Paging {
//    public static byte toUserMemory(byte VMAddress) {
//        if (VMAddress == null || VMAddress.length > 2)
//            return null;
//
//        byte[] UMAddress = new byte[2];
//
//
//        return UMAddress;
//    }

    public static int toUMAdr(RM realMachine, int adr) {
        int block = adr / Utils.BLOCK_WORD_COUNT;
        int word = adr % Utils.BLOCK_WORD_COUNT;
        byte[] pagingTable = realMachine.getPagingTable();

        return pagingTable[block] + word;
    }

    public static int toWordAdr(int blockIndex) {
        if (blockIndex < 0 || blockIndex > Utils.UM_BLOCK_COUNT)
            return -1;

        return blockIndex * Utils.BLOCK_WORD_COUNT;
    }
}
