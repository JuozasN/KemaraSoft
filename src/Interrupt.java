public class Interrupt extends Process{

    public Interrupt(Process parent){
        this.create(parent, (byte) 3, null, "Interrupt");
    }

    public void run(){

    }
}
