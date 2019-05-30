import java.util.ArrayList;

public class JCL extends Process{

    public JCL(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "JCL");
    }

    public void run(){
        switch(step){
            case 0:
                Utils.getResource(os.resourceList, Title.KERNEL_PROGRAM).request(this);
                stepIncrement();
                return;
            case 1:
                StaticResource sr = (StaticResource)Utils.getResource(ownedResources, Title.KERNEL_PROGRAM);
                ArrayList<Word> program = new ArrayList<>();
                if(getWord(sr, 0).toString() == Utils.TASK_HEADER){
                    Word word = getWord(sr, 1);
                    if(word.toString() == Utils.TASK_BODY) {
                        Utils.releaseDynamicResource(os, this, Title.MEM_LINE, "Nera programos antrastes");
                        stepReset();
                        return;
                    }else{
                        program.add(word);
                        int i = 2;
                        while(true){
                            word = getWord(sr, i);
                            if(word.toString() == Utils.TASK_BODY){break;}
                            if(word.toString() == ""){
                                Utils.releaseDynamicResource(os, this, Title.MEM_LINE, "Nera vartotojo programos");
                                stepReset();
                                return;
                            }
                            program.add(word);
                            i++;
                        }
                        while(true){
                            word = getWord(sr, i);
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

    public Word getWord(StaticResource sr, int index){
        return sr.getElementList()[index/Utils.BLOCK_WORD_COUNT].getWord(index%Utils.BLOCK_WORD_COUNT);
    }
}
