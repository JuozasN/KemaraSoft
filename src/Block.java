public class Block {
	private Word[] words;
	
	public Block(){
		words = new Word[0x10];
		for(int i = 0; i < 0x10; ++i)
			words[i] = new Word();
	}
	public Word[] getWords(){return words;}
	public Word getBlock(int index){return words[index];}
	public void setWords(Word[] words){System.arraycopy(words, 0, this.words, 0, words.length);}
	public void setBlock(byte[] block, int index){
		words[index].setBytes(block);}
	
	public String toString(){
		String str = "|";
		for(int i = 0; i < 0x10; ++i){
			str += words[i]+"|";
		}
		return str;
	}
}