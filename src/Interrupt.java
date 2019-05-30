public class Interrupt extends Process{

    public Interrupt(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "Interrupt");
    }

    public void run(){

    }
}
