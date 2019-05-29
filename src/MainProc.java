public class MainProc extends Process{

    public MainProc(Process parent) {
        this.create(parent, (byte)3, "MainProc");
    }

    public void run(){

    }
}
