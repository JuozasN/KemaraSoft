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

    public static int toUMAdr(RM realMachine, int vmBlock, int vmWord) {
        if (vmBlock < 0 || vmBlock > Utils.VM_MEM_BLOCK_COUNT ||
            vmWord < 0 || vmWord > Utils.BLOCK_WORD_COUNT)
            return -1;

        byte[] pagingTable = realMachine.getPagingTable();
        return (pagingTable[vmBlock]) * Utils.BLOCK_WORD_COUNT + vmWord;
    }

    public static int toWordAdr(int blockIndex) {
        if (blockIndex < 0 || blockIndex > Utils.UM_BLOCK_COUNT)
            return -1;

        return blockIndex * Utils.BLOCK_WORD_COUNT;
    }
}
