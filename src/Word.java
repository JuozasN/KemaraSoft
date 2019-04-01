public class Word {
	private byte[] bytes;
	private void resetBytes(){
		byte[] empty = {0, 0, 0, 0};
		bytes = empty;
	}
	
	public Word(){
		bytes = new byte[Utils.WORD_BYTE_COUNT];
	}

	public Word(byte[] bytes) {
		this();
		if (bytes != null && bytes.length <= 4)
			this.bytes = bytes;
	}
	
	public byte[] getBytes(){
		return bytes;
	}

	public void setBytes(byte[] bytes){
		resetBytes(); System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
	}
	
	public String toString(){
		String str = new String(bytes);
		str = str.replaceAll("\0", "");
		return str;
	}
}