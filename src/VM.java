import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class VM {

	public final class VMRegIndexes {
        public static final byte PC = 1;
        public static final byte SP = 0;
    }

	private final OS os;
	private final RM rm;
	private Block[] mem;
	private Short pc;
	private Short sp;

	public VM(OS os, RM rm, Block[] mem){
		this.mem = mem;
		for(int i = 0; i < Utils.VM_MEM_BLOCK_COUNT; ++i)
			mem[i] = new Block();
        this.os = os;
        this.os.assignRMMemoryBlocksForVM();
        setPC((short) 0);
        setSP((short) 0xA0);
		this.rm = rm;
	}

	public VM(OS os, RM rm) {
		this(os, rm, new Block[Utils.VM_MEM_BLOCK_COUNT]);
	}

    public Block[] getMem() {
        return mem;
    }

    /**
	public void clear() {
		setRMRegValuePaging(VMRegIndexes.SP, (short) 0);
		setRMRegValuePaging(VMRegIndexes.PC, (short) 0);

		for(byte i = 0; i < mem.length; ++i)
			for(byte j = 0; j < Utils.BLOCK_WORD_COUNT; ++j)
				this.mem[i].setWord(j, 0);
	}

	public void load() {
		setRMRegValuePaging(VMRegIndexes.SP, sp);
		setRMRegValuePaging(VMRegIndexes.PC, pc);

		for(byte i = 0; i < mem.length; ++i)
			for(byte j = 0; j < Utils.BLOCK_WORD_COUNT; ++j)
				os.setVMMemValue(i, j, mem[i].getWordString(j));
//				setUITableValues(i, j, mem[i].getWordString(j));
	}
	**/

    public void reset() {
        resetRegisters();
        resetMemory();
    }

    private void resetRegisters() {
        resetSP();
        resetPC();
    }

    private void resetMemory() {
        // reset only assigned memory blocks
        for(byte i = 0; i < mem.length; ++i)
            for(byte j = 0; j < Utils.BLOCK_WORD_COUNT; ++j)
                setMemValue(0, i, j);
    }

    public static short strToShort(String str) throws ProgramInterrupt{
        if(str.length() > 2)
        	throw new ProgramInterrupt((byte)1, "Invalid address");
        if(str.length() == 1)
            str = "0" + str;
        return((short) ((Character.digit(str.charAt(0), 16) << 4)
                + Character.digit(str.charAt(1), 16)));
    }

    public static byte strToByte(String str) throws ProgramInterrupt{
        if(str.length() > 2)
			throw new ProgramInterrupt((byte)1, "Invalid address");
        if(str.length() == 1)
            str = "0" + str;
        return((byte) ((Character.digit(str.charAt(0), 16) << 4)
                + Character.digit(str.charAt(1), 16)));
    }

	public Short getPc() {return pc;}

	public Short getSp(){return sp;}

	public void incrementSP(){
	    ++sp;
	    setRMRegValuePaging(VMRegIndexes.SP, sp);
//	    os.setVMRegValue(VMRegIndexes.SP, Utils.byteToHexString(sp));
    }

    public void decrementSP(){
		--sp;
        setRMRegValuePaging(VMRegIndexes.SP, sp);
//		os.setVMRegValue(VMRegIndexes.SP, Utils.byteToHexString(sp));

	}

	private void setSP(Short value) {
	    sp = value;
	    setRMRegValuePaging(VMRegIndexes.SP, sp);
    }

	private void resetSP() {
        sp = (byte) 0;
        rm.setSP(sp);
    }

	public void setPC(Short value){
		pc = value;
        setRMRegValuePaging(VMRegIndexes.PC, pc);
	}

	private void resetPC() {
		pc = (byte) 0;
		rm.setPC(pc);
	}

	public void setRMRegValuePaging(byte register, Short value) {
	    // use paging mechanism to change RM registers
	    rm.setReg(Paging.getRMRegIndex(register), Paging.getUMAdr(value));
    }

    private void setMemValue(int value, byte block, byte word) {
        mem[block].setWord(word, value);
        setRMMemValuePaging(value, block, word);
    }

	public void setMemValue(int value, Short adr){
        byte block = (byte) (adr/Utils.BLOCK_WORD_COUNT);
        byte word = (byte) (adr%Utils.BLOCK_WORD_COUNT);
		setMemValue(value, block, word);
	}

	public void setMemValue(byte[] value, Short adr){
        byte block = (byte) (adr/Utils.BLOCK_WORD_COUNT);
        byte word = (byte) (adr%Utils.BLOCK_WORD_COUNT);
        mem[block].setWord(word, value);
        setRMMemValuePaging(Utils.bytesToShort(value), block, word);
	}

	// uses paging mechanism
	public void setRMMemValuePaging(int value, byte vmBlock, byte vmWord) {
		if (vmBlock < 0x0 || vmBlock > Utils.UM_BLOCK_COUNT-1 || vmWord < 0x0 || vmWord > Utils.BLOCK_WORD_COUNT-1)
			return;

		Short adr = Paging.getUMAdr(vmBlock, vmWord);
		if (adr == null) {
			System.err.println("ERROR converting VM memory address to RM memory address in method setRMMemValuePaging()");
			return;
		}
		rm.setValue(value, adr);
	}

	public Word getMemValue(Short adr){
		byte block = (byte) (adr/Utils.BLOCK_WORD_COUNT);
		byte word = (byte) (adr%Utils.BLOCK_WORD_COUNT);
		return mem[block].getWord(word);
	}
	
	public void exec() throws SystemInterrupt, ProgramInterrupt{
		boolean running = true;
		String command = read();
		switch(command){
			case "PUSH": push(read()); break;
			case "PSHC": pshc(read()); break;
			case "POPM": popm(read()); break;
			case "POP": pop(); break;
			case "TOP": top(read()); break;
			case "ADD": binOp(1); break;
			case "SUB": binOp(2); break;
			case "MULT": binOp(3); break;
			case "DIV": binOp(4); break;
			case "CMP": cmp(); break;
			case "JZ": jmp(1, read()); break;
			case "JP": jmp(2, read()); break;
			case "JN": jmp(3, read()); break;
			case "JMP": jmp(4,read()); break;
			case "GET": get(); break;
			case "PUT": put(); break;
			case "HALT": os.haltInterrupt(); break;
//			case "HALT": throw new SystemInterrupt(3, "HALT!!!");
			default:
				throw new ProgramInterrupt((byte)2, "Invalid command: " + command);
		}
		//System.out.format("Command %s executed successfully\n", command);
	}
	
	public void get() throws SystemInterrupt{
		throw new SystemInterrupt((byte)1, "GET");
	}
	
	public void put() throws SystemInterrupt{
		throw new SystemInterrupt((byte)2, "PUT");
	}
	
	public String read(){
		String value = getMemValue(pc).toString();
		setPC(++pc);
		return value;
	}
	
	public void push(String strAdr) throws ProgramInterrupt{
		Short adr = strToShort(strAdr);
		Word value = getMemValue(adr);
		setMemValue(value.getValue(), sp);
		incrementSP();
	}
	
	public void pshc(String strVal){
		setMemValue(strVal.getBytes(), sp);
		incrementSP();
	}
	
	public Word pop(){
		setMemValue(0, sp);
		decrementSP();
		return getMemValue(sp);
	}
	
	public void popm(String strAdr) throws ProgramInterrupt{
		Short adr = strToShort(strAdr);
		Word value = pop();
		setMemValue(value.getValue(), adr);
	}
	
	public void top(String strAdr) throws ProgramInterrupt{
		popm(strAdr);
		incrementSP();
	}
	
	public void binOp(int op) throws ProgramInterrupt{
		try{
			int op2 = Integer.parseInt(pop().toString());
			int op1 = Integer.parseInt(pop().toString());
			int result = 0;
			switch(op){
				case 1: result = op1+op2; // ADD
						break;
				case 2: result = op1-op2; // SUB
						break;
				case 3: result = op1*op2; // MULT
						break;
				case 4: result = op1/op2; // DIV
						break;
				default:
					System.err.println("Impossible error at VM.binOp method");
					System.exit(0);
			}
			pshc(String.valueOf(result));
		} catch(NumberFormatException e){
			throw new ProgramInterrupt((byte)3, "Invalid assign(Type missmatch)");
		}
	}
	
	public void cmp(){
		int op2 = Integer.parseInt(pop().toString());
		int op1 = Integer.parseInt(pop().toString());
		if(op1 > op2) pshc("1");
		else if(op1 == op2) pshc("0");
		else pshc("-1");
	}
	
	public void jmp(int cond, String strAdr) throws ProgramInterrupt{
		int val = Integer.parseInt(pop().toString());
		boolean doJump = false;
		switch(cond){
			case 1: if(val == 0) doJump = true; break;
			case 2: if(val > 0) doJump = true; break;
			case 3: if(val < 0) doJump = true; break;
			case 4: doJump = true; incrementSP(); break;
			default:
				System.err.println("Impossible error at VM.jmp() method");
				System.exit(0);
		}
		if(doJump){
			Short adr = strToShort(strAdr);
			setPC(adr);
		}
	}
}