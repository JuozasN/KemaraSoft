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

    private static Byte[] pagingTable = null;

    public static void  setPagingTable(Byte[] pagingTableReference) {
        if (pagingTableReference == null || pagingTableReference.length > Utils.BLOCK_WORD_COUNT) {
            System.err.println("Invalid paging table argument passed to setPagingTable() method");
            return;
        }

        pagingTable = new Byte[Utils.BLOCK_WORD_COUNT];
        for(int i = 0; i < pagingTableReference.length; ++i) {
            pagingTable[i] = Utils.intToByte(pagingTableReference[i]);
        }
    }

    public static Short getUMAdr(int vmAdr) {
        int vmBlock = vmAdr/Utils.BLOCK_WORD_COUNT;
        int vmWord = vmAdr%Utils.BLOCK_WORD_COUNT;

        return getUMAdr(vmBlock, vmWord);
    }

    public static Short getUMAdr(int vmBlock, int vmWord) {
        if(pagingTable == null)
            return null;

        if (vmBlock < 0 || vmBlock > Utils.VM_MEM_BLOCK_COUNT ||
            vmWord < 0 || vmWord > Utils.BLOCK_WORD_COUNT)
            return null;

        //byte[] pagingTable = realMachine.getPagingTable();
        return (short) (pagingTable[vmBlock] * Utils.BLOCK_WORD_COUNT + vmWord);
    }

    public static Short getWordAdr(byte blockIndex) {
        if (blockIndex < 0 || blockIndex > Utils.UM_BLOCK_COUNT)
            return null;

        return (short) (blockIndex * Utils.BLOCK_WORD_COUNT);
    }

    public static byte getRMRegIndex(int vm) {
        if (vm < 0 || vm > 1)
            return -1;

        if (vm == VM.VMRegIndexes.SP)
            return RM.RMRegIndexes.SP;
        else
            return RM.RMRegIndexes.PC;
    }
}
