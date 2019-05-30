public class Interrupt extends Process{

    public Interrupt(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "Interrupt");
    }

    public void run(){
        switch (step) {
            case 0:
                
                return;
            case 1:

                return;
            default:
                System.err.println("Impossible step at StartStop.run()");
                System.exit(0);
        }
    }
}
