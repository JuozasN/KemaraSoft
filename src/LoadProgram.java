import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LoadProgram extends Process {

    Block[] program;

    public LoadProgram(OS os, Process parent){
        super(os);
        this.create(parent, (byte)3, "LoadProgram");
    }

    public void run() throws ProgramInterrupt{
        switch(step){
            case 0:
                Distributor.request(this, Title.FILE_NAME);
                stepIncrement();
                return;
            case 1:
                program = splitToBlocks(loadProgram());
                Utils.getResource(os.resourceList, Title.KERNEL_MEMORY).request(this);
                stepIncrement();
                return;
            case 2:
                StaticResource km = (StaticResource)Utils.getResource(ownedResources, Title.KERNEL_MEMORY);
                Block[] blocks = km.getElements(Utils.VM_MEM_BLOCK_COUNT);
                String memPoint = Integer.toString(blocks[0].getWord(0).getValue());
                transfer(blocks);
                Utils.releaseDynamicResource(os, this, Title.KERNEL_PROGRAM, memPoint);
                stepReset();
                return;
            default:
                System.err.println("Impossible step at LoadProgram.run()");
                System.exit(0);
        }
    }

    
    public ArrayList<String> loadProgram(){
        BufferedReader br;
        ArrayList<String> strArray = new ArrayList<>();
        DynamicResource resourceObj = (DynamicResource)Utils.getResource(ownedResources, Title.FILE_NAME);
        String resource = resourceObj.getParameter();
        resourceObj.delete();
        String tempFilePath = "src/" + resource;

//        String tempFilePath = "src/" + Utils.getResource(ownedResources, Title.FILE_NAME);
        try {
            br = new BufferedReader(new FileReader(tempFilePath));
            for (String line; (line = br.readLine()) != null; ) {
                strArray.addAll(Arrays.asList(line.split(" ")));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error reading from file");
            System.exit(0);
        }
        return strArray;
    }

    public Block[] splitToBlocks(ArrayList<String> strArray) throws ProgramInterrupt{
        Block[] blocks = new Block[Utils.VM_MEM_BLOCK_COUNT];
        for(int i = 0; i < Utils.VM_MEM_BLOCK_COUNT; i++){
            blocks[i] = new Block();
        }
        int i = 0;
        int wordsCount = 0;
        for(String str: strArray){
            int tempWordsCount = (int) Math.ceil(((double)str.length())/4);
            Word[] words = new Word[Utils.BLOCK_WORD_COUNT];
            Word[] temp = Block.getBlockFromString(str).getWords();
            if((wordsCount + tempWordsCount) <= Utils.BLOCK_WORD_COUNT){
                System.arraycopy(temp, 0, words, wordsCount, tempWordsCount);
                wordsCount += tempWordsCount;
            } else{
                int remainingLen = Utils.BLOCK_WORD_COUNT-wordsCount;
                System.arraycopy(temp, 0, words, wordsCount, remainingLen);
                blocks[i].setWords(words);
                Arrays.fill(words, null);
                wordsCount = tempWordsCount-remainingLen;
                System.arraycopy(temp, remainingLen, words, 0, wordsCount);
                i++;
            }
        }
        return blocks;
    }

    public void transfer(Block[] dest){
        for(int i = 0; i < Utils.VM_MEM_BLOCK_COUNT; ++i){
            dest[i].setWords(program[i].getWords());
        }
    }
}
