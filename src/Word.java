public class Word {
	private Integer value;

	public Word(Integer value) {
		if (value != null)
			this.value = value;
		else
		    this.value = 0;
	}

    public Word() {
        this(0);
    }

	private void resetValue(){
		this.value = 0;
	}
	
	public int getValue(){
		return this.value;
	}

	public String getValueHexString() {
		return Utils.bytesToHexString(Utils.intToByteArray(value));
	}

	public String getStringValue() {
		return Utils.bytesToString(Utils.intToByteArray(value));
	}

	public void setValue(int value) {
	    this.value = value;
    }

//	public void setValue(byte[] value){
//		resetValue(); System.arraycopy(value, 0, this.value, 0, value.length);
//	}

	public void setValue(byte[] value) {
		this.value = Utils.byteArrayToInt(value);
	}
	
	public String toString(){
	    if (value == 0)
	        return "0000";
        else
            return Utils.bytesToString(Utils.intToByteArray(value));
//		String str = String.valueOf(value);
//		str = str.replaceAll("\0", "");
//		return str;
	}
}