import java.util.ArrayList;

public class JCL extends Process{

    public JCL(Process parent) {
        this.create(parent, (byte)3, "JCL");
        new DynamicResource().create(this, "Eilute atmintyje");
    }

    public void run(){
        //ownedResources.get("Užduotis supervizorinėje atmintyje").request(this);
        ArrayList<Word> program = new ArrayList<>();
        if(ownedResources.get(0).getWord(0).toString() == "$HDR"){
            Word word = ownedResources.get(0).getWord(1);
            if(word.toString() == "$BDY") {
                try {
                    ((DynamicResource)getCreatedResource("Eilute atmintyje")).release(this, "Nera programos antrastes");

                }catch(ProgramInterrupt pi){System.err.println("Impossible error while running JCL");System.exit(0);}
            }else{
                program.add(word);

            }
        }else{
            try {
                ((DynamicResource)getCreatedResource("Eilute atmintyje")).release(this, "Nera programos antrastes");
            }catch(ProgramInterrupt pi){System.err.println("Impossible error while running JCL");System.exit(0);}
        }
    }
}
