public class Idle extends Process{

    public Idle(Process parent) {
        this.create(parent, (byte)0, "Idle");
    }

    public void run(){

    }
}
