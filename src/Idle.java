public class Idle extends Process{

    public Idle(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)0, "Idle");
    }

    public void run(){

    }
}
