import java.lang.reflect.Array;
import java.util.ArrayList;

public class JCL extends Process{

    public JCL(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "JCL");
    }

    public void run(){
        switch(step){
            case 0:
                //Utils.getResource(os.resourceList, Title.KERNEL_PROGRAM).request(this);
                Distributor.request(this, Title.KERNEL_PROGRAM);
                stepIncrement();
                return;
            case 1:
                DynamicResource dr = (DynamicResource)Utils.getResource(ownedResources, Title.KERNEL_PROGRAM);
                StaticResource km = (StaticResource)Utils.getResource(os.resourceList, Title.KERNEL_MEMORY);
                int memPointer = Integer.parseInt(dr.getParameter());
                Block[] KMprogram = new Block[Utils.VM_MEM_BLOCK_COUNT];
                for(int i = 0; i < Utils.VM_MEM_BLOCK_COUNT; i++, memPointer++){
                    KMprogram[i] = new Block(km.getElement(memPointer));
                }
                ArrayList<Word> program = new ArrayList<>();
                if(getWord(KMprogram, 0).toString() == Utils.TASK_HEADER){
                    Word word = getWord(KMprogram, 1);
                    if(word.toString() == Utils.TASK_BODY) {
                        Utils.releaseDynamicResource(os, this, Title.MEM_LINE, "Nera programos antrastes");
                        stepReset();
                        return;
                    }else{
                        //program.add(word);
                        int i = 2;
                        while(true){
                            word = getWord(KMprogram, i);
                            if(word.toString() == Utils.TASK_BODY){break;}
                            if(word.toString() == ""){
                                Utils.releaseDynamicResource(os, this, Title.MEM_LINE, "Nera vartotojo programos");
                                stepReset();
                                return;
                            }
                            //program.add(word);
                            i++;
                        }
                        while(true){
                            word = getWord(KMprogram, i);
                            if(word.toString() == Utils.TASK_END || word.toString() == ""){
                                if(word.toString() == Utils.TASK_END){
                                    Utils.releaseDynamicResource(os, this, Title.MAIN_PROGRAM, "Vykdymo laikas = 1");
                                    stepReset();
                                    return;
                                }
                                Utils.releaseDynamicResource(os, this, Title.MEM_LINE, "Nera programos pabaigos zymes");
                                stepReset();
                                return;
                            }else{
                                program.add(word);
                                i++;
                            }
                        }
                    }
                }else{
                    Utils.releaseDynamicResource(os, this, Title.MEM_LINE, "Nera programos antrastes");
                    stepReset();
                    return;
                }
            default:
                System.err.println("Impossible step at JCL.run()");
                System.exit(0);
        }
    }

    public Word getWord(Block[] blocks, int index){
        return blocks[index/Utils.BLOCK_WORD_COUNT].getWord(index%Utils.BLOCK_WORD_COUNT);
    }
}
