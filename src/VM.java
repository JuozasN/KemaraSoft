public class VM {
	public static final int MEM_BLOCK_COUNT = 0x10;
	private Block[] mem;
	private byte pc;
	private byte sp;

	
	VM(Block[] mem){
		this.mem = mem;
		for(int i = 0; i < MEM_BLOCK_COUNT; ++i)
			mem[i] = new Block();
		pc = 0;
		sp = Utils.intToByte(0xA0);
	}

    public Block[] getMem() {
        return mem;
    }
	
	public void setValue(byte[] value, byte adr){
		int row = Utils.byteToInt(adr)/0x10;
		int col = Utils.byteToInt(adr)%0x10;
		mem[row].setBlock(value, col);
	}
	
	public Word getValue(byte adr){
		int row = Utils.byteToInt(adr)/0x10;
		int col = Utils.byteToInt(adr)%0x10;
		return mem[row].getBlock(col);
	}
	
	
	public void exec(){
		boolean running = true;
		while(running){
			String command = read();		
			switch(command){
				case "PUSH": 
					push(read());
					break;
				case "PSHC":
					pshc(read());
					break;
				case "POPM":
					popm(read());
					break;
				case "POP":
					pop();
					break;
				case "TOP":
					top(read());
					break;
				case "ADD":
					binOp(1);
					break;
				case "SUB":
					binOp(2);
					break;
				case "MULT":
					binOp(3);
					break;
				case "DIV":
					binOp(4);
					break;
				case "HALT":
					running = false;
					break;
				default:
					System.err.println("Invalid command");
					// Invalid command interrupt here
					System.exit(0);
			}
		}
	}
	
	public void execInput(int numVars){
		// Ughhh...
	}
	
	public void execOut(int numArgs){
		// Ughhh...
	}
	
	public String read(){
		String value = getValue(pc).toString();
		pc++;
		return value;
	}
	
	public void push(String strAdr){
		byte adr = Utils.strToByte(strAdr);
		Word value = getValue(adr);
		setValue(value.getBytes(), sp);
		sp++;
	}
	
	public void pshc(String strVal){
		setValue(strVal.getBytes(), sp);
		sp++;
	}
	
	public Word pop(){
		sp--;
		return getValue(sp);
	}
	
	public void popm(String strAdr){
		byte adr = Utils.strToByte(strAdr);
		Word value = pop();
		setValue(value.getBytes(), adr);
	}
	
	public void top(String strAdr){
		popm(strAdr);
		sp++;
	}
	
	public void binOp(int op){
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
			push(String.valueOf(result));
		} catch(NumberFormatException e){
			// Invalid assign interrupt here
			System.exit(0);
		}
	}
}