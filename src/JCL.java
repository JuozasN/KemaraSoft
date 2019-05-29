import java.util.ArrayList;

public class JCL extends Process{

    public JCL(Process parent) {
        this.create(parent, (byte)3, "JCL");
        new DynamicResource().create(this, "Eilute atmintyje");
        new DynamicResource().create(this, "MainProc uzduotis");
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
                        releaseDynamicResource("Eilute atmintyje", "Nera programos antrastes");
                        stepReset();
                        return;
                    }else{
                        program.add(word);
                        int i = 2;
                        while(true){
                            word = getWord(i);
                            if(word.toString() == "$BDY"){break;}
                            if(word.toString() == ""){
                                releaseDynamicResource("Eilute atmintyje", "Nera vartotojo programos");
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
                                    releaseDynamicResource("MainProc uzduotis", "Vykdymo laikas = 1");
                                    stepReset();
                                    return;
                                }
                                releaseDynamicResource("Eilute atmintyje", "Nera programos pabaigos zymes");
                                stepReset();
                                return;
                            }else{
                                program.add(word);
                                i++;
                            }
                        }
                    }
                }else{
                    releaseDynamicResource("Eilute atmintyje", "Nera programos antrastes");
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

    public void releaseDynamicResource(String resourceTitle, String resourceParameter){
        try {
            ((DynamicResource)getCreatedResource(resourceTitle)).release(this, resourceParameter);

        }catch(ProgramInterrupt pi){System.err.println("Impossible error while running JCL");System.exit(0);}
    }
}
