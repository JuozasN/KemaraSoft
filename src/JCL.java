import java.util.ArrayList;

public class JCL extends Process{

    public JCL(Process parent) {
        this.create(parent, (byte)3, "JCL");
    }

    public void run(){
        switch(step){
            case 0:
                //ownedResources.get("Užduotis supervizorinėje atmintyje").request(this);
                stepIncrement();
                return;
            case 1:
                ArrayList<Word> program = new ArrayList<>();
                if(getWord(0).toString() == "$HDR"){
                    Word word = getWord(1);
                    if(word.toString() == "$BDY") {
                        releaseDynamicResource(DynamicResource.Title.MEM_LINE, "Nera programos antrastes");
                        stepReset();
                        return;
                    }else{
                        program.add(word);
                        int i = 2;
                        while(true){
                            word = getWord(i);
                            if(word.toString() == "$BDY"){break;}
                            if(word.toString() == ""){
                                releaseDynamicResource(DynamicResource.Title.MEM_LINE, "Nera vartotojo programos");
                                stepReset();
                                return;
                            }
                            program.add(word);
                            i++;
                        }
                        while(true){
                            word = getWord(i);
                            if(word.toString() == "$END" || word.toString() == ""){
                                if(word.toString() == "$END"){
                                    releaseDynamicResource(DynamicResource.Title.MAIN_PROGRAM, "Vykdymo laikas = 1");
                                    stepReset();
                                    return;
                                }
                                releaseDynamicResource(DynamicResource.Title.MEM_LINE, "Nera programos pabaigos zymes");
                                stepReset();
                                return;
                            }else{
                                program.add(word);
                                i++;
                            }
                        }
                    }
                }else{
                    releaseDynamicResource(DynamicResource.Title.MEM_LINE, "Nera programos antrastes");
                    stepReset();
                    return;
                }
            default:
                System.err.println("Impossible step at JCL.run()");
                System.exit(0);
        }
    }

    public Word getWord(int index){
        return ownedResources.get(index/Utils.BLOCK_WORD_COUNT).getWord(index%Utils.BLOCK_WORD_COUNT);
    }

    public void releaseDynamicResource(DynamicResource.Title title, String parameter){
        new DynamicResource(title, parameter).release();
    }
}
