import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public Block(Block block){
		words = new Word[Utils.BLOCK_WORD_COUNT];
		for(int i = 0; i < Utils.BLOCK_WORD_COUNT; ++i)
			words[i] = new Word(block.getWord(i));
	}

	public static Block getBlockFromString(String text) throws ProgramInterrupt{
		if (text == null) {
			return new Block();
		}

		List<String> words = Arrays.asList(text.split("(?<=\\G....)"));
		if (words.size() > Utils.BLOCK_WORD_COUNT) {
			throw new ProgramInterrupt((byte)4, "OVERFLOW!@!");
		}

		Block block = new Block();
		for (int i = 0; i < words.size(); ++i) {
			block.setWord(i, words.get(i));
		}

		return block;
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

	public void setWords(String text) {
		if (text == null) {
			return;
		}

		List<String> words = Arrays.asList(text.split("(?<=\\G....)"));
		if (words.size() > Utils.BLOCK_WORD_COUNT) {
			return;
		}

		for (int i = 0; i < words.size(); ++i) {
			setWord(i, words.get(i));
		}
	}

	public void setWords(Word[] words){
	    System.arraycopy(words, 0, this.words, 0, words.length);
	}

	public void setWords(int[] words){
		if (words == null || words.length > Utils.BLOCK_WORD_COUNT) {
			return;
		}

		for(int i = 0; i < words.length; ++i) {
			setWord(i, words[i]);
//			this.words[i].setValue(words[i]);
		}
	}

	public void setWord(int index, int word){
		words[index].setValue(word);
	}

	public void setWord(int index, Word word) {
		setWord(index, word.getValue());
	}

	public void setWord(int index, byte[] word) {
		words[index].setValue(word);
	}

	public void setWord(int index, String word) {
		setWord(index, word.getBytes());
	}
	
	public String toString() {
		String s = "";
		for(Word w: words){
			s+=w;
		}
		return s;
	}
}