public class Block {
	private Word[] words;
	
	public Block(){
		words = new Word[Utils.BLOCK_WORD_COUNT];
		for(int i = 0; i < Utils.BLOCK_WORD_COUNT; ++i)
			words[i] = new Word();
	}
	public Word[] getWords(){
	    return words;
	}

	public Word getWord(int index){
	    return words[index];
	}

	public void setWords(Word[] words){
	    System.arraycopy(words, 0, this.words, 0, words.length);
	}

	public void setWord(int word, int index){
		words[index].setValue(word);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		for(int i = 0; i < Utils.BLOCK_WORD_COUNT; ++i){
			sb.append(String.format("%-4s|", words[i]));
		}
		return sb.toString();
	}
}