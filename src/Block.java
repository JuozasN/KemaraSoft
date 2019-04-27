public class Block {
	private Word[] words;
	
	public Block(){
		words = new Word[Utils.BLOCK_WORD_COUNT];
		for(int i = 0; i < Utils.BLOCK_WORD_COUNT; ++i)
			words[i] = new Word();
	}

	public Block(int[] block){
		words = new Word[Utils.BLOCK_WORD_COUNT];
		for(int i = 0; i < Utils.BLOCK_WORD_COUNT; ++i)
			words[i] = new Word(block[i]);
	}

	public Word[] getWords(){
	    return words;
	}

	public Word getWord(int index){
	    return words[index];
	}

	public String getWordString(int index) {
		return words[index].toString();
	}

	public void setWords(Word[] words){
	    System.arraycopy(words, 0, this.words, 0, words.length);
	}

	public void setWords(int[] words){
		if (words == null || words.length > 16) {
			return;
		}

		for(int i = 0; i < words.length; ++i) {
			this.words[i].setValue(words[i]);
		}
	}

	public void setWord(int index, int word){
		words[index].setValue(word);
	}

	public void setWord(int index, Word word) {
		words[index].setValue(word.getValue());
	}

	public void setWord(int index, byte[] word) {
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