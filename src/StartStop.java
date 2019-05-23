import java.util.ArrayList;

public class StartStop extends Process{

    public StartStop(){
        //What to do with elementList? Currently null in every constructor
        this.create(null, (byte)4, null, "StartStop");
    }

    public void run(){
        createResources();
        createChildren();
        changeState(ProcessState.BLOCKED); // Blokavimas laukiant "POS pabaiga" resurso
        //TODO: Kažkaip padaryti kad gavus resursą vygdytų sekančią eilutę
        deleteChildren();
        deleteResources();
    }

    public void createChildren(){
        addToChildren(new LoadProgram(this));
        addToChildren(new JCL(this));
        addToChildren(new Loader(this));
        addToChildren(new MainProc(this));
        addToChildren(new Interrupt(this));
        addToChildren(new ChannelInterrupt(this));
        addToChildren(new PutLine(this));
        addToChildren(new Idle(this));
    }

    public void createResources(){
        // Something
    }

    public void deleteChildren(){
        for(Process p: children){
            removeFromChildren(p);
            p.delete();
        }
    }

    public void deleteResources(){
        for(Resource r: createdResources){
            removeFromCreatedResources(r);
            r.delete();
        }
    }
}
