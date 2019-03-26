public class Block {
	public static final int WORD_COUNT = 0x10;
	private Word[] words;
	
	public Block(){
		words = new Word[WORD_COUNT];
		for(int i = 0; i < WORD_COUNT; ++i)
			words[i] = new Word();
	}
	public Word[] getWords(){return words;}
	public Word getBlock(int index){return words[index];}
	public void setWords(Word[] words){System.arraycopy(words, 0, this.words, 0, words.length);}
	public void setBlock(byte[] block, int index){
		words[index].setBytes(block);}
	
	public String toString(){
		String str = "|";
		for(int i = 0; i < WORD_COUNT; ++i){
			str += words[i]+"|";
		}
		return str;
	}
}