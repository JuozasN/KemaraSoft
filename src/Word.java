public class Word {
	private byte[] bytes;
	
	public Word(){
		bytes = new byte[0x4];
	}
	
	public byte[] getBytes(){return bytes;}
	public void setBytes(byte[] bytes){System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);}
	
	public String toString(){return new String(bytes);}
}