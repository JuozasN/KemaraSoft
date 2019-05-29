public class MainProc extends Process{

    public MainProc(Process parent) {
        this.create(parent, (byte)3, "MainProc");
    }

    public void run(){
        switch(step){
            case 0:
                //request for "MainProc uzduotis" resource
                stepIncrement();
                return;
            case 1:
                //check resource parameter
                new JobHelper(this);
                //jei vykdymo laikas = 0 is kur zinome kuri JobHelper'i trinti?
                stepReset();
                return;
        }
    }
}
