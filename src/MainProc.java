public class MainProc extends Process{

    public MainProc(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "MainProc");
    }

    public void run(){
        switch(step){
            case 0:
                Distributor.request(this, Title.MAIN_PROGRAM);
                stepIncrement();
                return;
            case 1:
                if (Integer.parseInt((DynamicResource)Utils.getResource(this.ownedResources, Title.MAIN_PROGRAM))))
                //check resource parameter
                new JobHelper(os, this);
                //jei vykdymo laikas = 0 is kur zinome kuri JobHelper'i trinti?
                stepReset();
                return;
        }
    }
}
