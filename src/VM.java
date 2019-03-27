public class VM {
	public static final int BLOCK_COUNT = 0x10;
	private Block[] mem;
	private byte pc;
	private byte sp;

	
	VM(Block[] mem){
		this.mem = mem;
		for(int i = 0; i < BLOCK_COUNT; ++i)
			mem[i] = new Block();
		pc = 0;
		sp = intToByte(0xA0);
	}

    public Block[] getMem() {
        return mem;
    }

    public int byteToInt(byte value){return (int)(value&0xFF);}
	
	public byte intToByte(int value){return (byte)(value&0xFF);}
	
	public byte strToByte(String str){
		if(str.length() > 2); // Invalid adress interrupt here
		if(str.length() == 1)
			str = "0" + str;
		return((byte) ((Character.digit(str.charAt(0), 16) << 4)
                             + Character.digit(str.charAt(1), 16)));
	}
	
	public void setValue(byte[] value, byte adr){
		int row = byteToInt(adr)/0x10;
		int col = byteToInt(adr)%0x10;
		mem[row].setBlock(value, col);
	}
	
	public Word getValue(byte adr){
		int row = byteToInt(adr)/0x10;
		int col = byteToInt(adr)%0x10;
		return mem[row].getBlock(col);
	}
	
	
	public void exec(){
		boolean running = true;
		while(running){
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
				case "GET": get(read()); break;
				case "PUT": put(read()); break;
				case "HALT": running = false; break;
				default:
					System.err.println("Invalid command: " + command);
					// Invalid command interrupt here
					System.exit(0);
			}
			//System.out.format("Command %s executed successfully\n", command);
		}
	}
	
	public void get(String strAdr){
		strAdr += "0";
		byte adr = strToByte(strAdr);
		// Do output stuff
	}
	
	public void put(String strAdr){
		strAdr += "0";
		byte adr = strToByte(strAdr);
		// Do input stuff
	}
	
	public String read(){
		String value = getValue(pc).toString();
		pc++;
		return value;
	}
	
	public void push(String strAdr){
		byte adr = strToByte(strAdr);
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
		byte adr = strToByte(strAdr);
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
			pshc(String.valueOf(result));
		} catch(NumberFormatException e){
			// Invalid assign interrupt here
			System.exit(0);
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
			case 4: doJump = true; sp++; break;
			default:
				System.err.println("Impossible error at VM.jmp() method");
				System.exit(0);
		}
		if(doJump){
			byte adr = strToByte(strAdr);
			pc = adr;
		}
	}
}