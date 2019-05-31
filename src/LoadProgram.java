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
                Utils.getResource(os.resourceList, Title.KERNEL_MEMORY);
                //Copy file content to kernel memory
                String memPoint = ""; //Send pointer to program???
                Resource r = new DynamicResource(memPoint);
                r.create(os,this, Title.KERNEL_PROGRAM);
                r.release();
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
        String tempFilePath = "src/" + Utils.getResource(ownedResources, Title.FILE_NAME);
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
        for(String str: strArray){
            Word[] words = new Word[Utils.BLOCK_WORD_COUNT];
            Word[] temp = Block.getBlockFromString(str).getWords();
            if((temp.length + words.length) <= Utils.BLOCK_WORD_COUNT){
                System.arraycopy(temp, 0, words, words.length, temp.length);
            } else{
                int remainingLen = Utils.BLOCK_WORD_COUNT-words.length;
                System.arraycopy(temp, 0, words, words.length, remainingLen);
                blocks[i].setWords(words);
                Arrays.fill(words, null);
                System.arraycopy(temp, remainingLen, words, words.length, temp.length-remainingLen);
                i++;
            }
        }
        return blocks;
    }
}
