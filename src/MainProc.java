public class MainProc extends Process{

    public MainProc(Process parent){
        this.create(parent, (byte) 3, null, "MainProc");
    }

    public void run(){

    }
}
