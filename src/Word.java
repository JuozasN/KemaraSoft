public class Word {
	public static final int BYTE_COUNT = 0x4;
	private byte[] bytes;
	
	public Word(){
		bytes = new byte[BYTE_COUNT];
	}

	public Word(byte[] bytes) {
		this();
		if (bytes != null && bytes.length <= 4)
			this.bytes = bytes;
	}
	
	public byte[] getBytes(){return bytes;}
	public void setBytes(byte[] bytes){System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);}
	
	public String toString(){return new String(bytes);}
}