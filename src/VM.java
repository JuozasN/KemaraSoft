import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class VM {
    public final class VMRegIndexes {
        public static final int PC = 1;
        public static final int SP = 0;
    }

	private final TableController controller;
	private Block[] mem;
	private byte pc;
	private byte sp;

	public VM(TableController controller, Block[] mem){
		this.mem = mem;
		for(int i = 0; i < Utils.VM_MEM_BLOCK_COUNT; ++i)
			mem[i] = new Block();
		pc = 0;
		sp = Utils.intToByte(0xA0);
        this.controller = controller;
        this.controller.assignRMMemoryBlocksForVM();
	}

	public VM(TableController controller) {
		this(controller, new Block[Utils.VM_MEM_BLOCK_COUNT]);
	}

    public Block[] getMem() {
        return mem;
    }

    public void reset() {
        resetRegisters();
        resetMemory();
    }

    private void resetRegisters() {
        resetSP();
        setPC(Utils.intToByte(0));
    }

    private void resetMemory() {
        // reset only assigned memory blocks
        for(int i = 0; i < mem.length; ++i)
            for(int j = 0; j < Utils.BLOCK_WORD_COUNT; ++j)
                setValue(0, i, j);
    }

    public static byte strToByte(String str){
        if(str.length() > 2); // Invalid address interrupt here
        if(str.length() == 1)
            str = "0" + str;
        return((byte) ((Character.digit(str.charAt(0), 16) << 4)
                + Character.digit(str.charAt(1), 16)));
    }

	public byte getPc() {return pc;}

	public byte getSp(){return sp;}

	public void incrementSP(){
	    ++sp;
	    controller.setVMRegValue(VMRegIndexes.SP, Utils.byteToHexString(sp));
    }

    public void decrementSP(){
		--sp;
		controller.setVMRegValue(VMRegIndexes.SP, Utils.byteToHexString(sp));
	}

	private void resetSP() {
        sp = Utils.intToByte(0);
        controller.setVMRegValue(VMRegIndexes.SP, Utils.byteToHexString(sp));
    }

	public void setPC(byte value){
		pc = value;
		controller.setVMRegValue(VMRegIndexes.PC, Utils.byteToHexString(pc));
	}

	public void setValue(byte[] value, byte adr){
		int block = Utils.byteToInt(adr)/Utils.BLOCK_WORD_COUNT;
		int word = Utils.byteToInt(adr)%Utils.BLOCK_WORD_COUNT;
		mem[block].setWord(value, word);
		setUITableValues(block, word, Utils.bytesToHexString(value, 4));
	}

    private void setValue(int value, int block, int word) {
        byte[] byteValue = Utils.intToByteArray(value);
        mem[block].setWord(byteValue, word);
        setUITableValues(block, word, Utils.bytesToHexString(byteValue, 4));
    }

	private void setUITableValues(int block, int word, String value) {
        controller.setRMMemValuePaging(block, word, value);
        controller.setVMMemValue(block, word, value);
    }

	public Word getValue(byte adr){
		int row = Utils.byteToInt(adr)/Utils.BLOCK_WORD_COUNT;
		int col = Utils.byteToInt(adr)%Utils.BLOCK_WORD_COUNT;
		return mem[row].getWord(col);
	}

	public void loadProgram() throws ProgramInterrupt{
        BufferedReader br;
        String tempFilePath = "src/" + controller.filename.getText();
        try {
            br = new BufferedReader(new FileReader(tempFilePath));

            byte adr = 0;
            for (String line; (line = br.readLine()) != null; ) {
                String[] strArray = line.split(" ");
                for (String str : strArray) {
                    if (str.length() > 4) {
						throw new ProgramInterrupt(2, "Overflow");
                    }
                    setValue(str.getBytes(), adr);
                    adr++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error reading from file");
            System.exit(0);
        }
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
			case "HALT": throw new SystemInterrupt(3, "HALT!!!");
			default:
				throw new ProgramInterrupt(2, "Invalid command: " + command);
		}
		//System.out.format("Command %s executed successfully\n", command);
	}
	
	public void get() throws SystemInterrupt{
		throw new SystemInterrupt(1, "GET");
	}
	
	public void put() throws SystemInterrupt{
		throw new SystemInterrupt(2, "PUT");
	}
	
	public String read(){
		String value = getValue(pc).toString();
		setPC(++pc);
		return value;
	}
	
	public void push(String strAdr){
		byte adr = strToByte(strAdr);
		Word value = getValue(adr);
		setValue(value.getBytes(), sp);
		incrementSP();
	}
	
	public void pshc(String strVal){
		setValue(strVal.getBytes(), sp);
		incrementSP();
	}
	
	public Word pop(){
		decrementSP();
		return getValue(sp);
	}
	
	public void popm(String strAdr){
		byte adr = strToByte(strAdr);
		Word value = pop();
		setValue(value.getBytes(), adr);
	}
	
	public void top(String strAdr){
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
			throw new ProgramInterrupt(3, "Invalid assign(Type missmatch)");
		}
	}
	
	public void cmp(){
		int op2 = Integer.parseInt(pop().toString());
		int op1 = Integer.parseInt(pop().toString());
		if(op1 > op2) pshc("1");
		else if(op1 == op2) pshc("0");
		else pshc("-1");
	}
	
	public void jmp(int cond, String strAdr){
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
			byte adr = strToByte(strAdr);
			setPC(adr);
		}
	}
}