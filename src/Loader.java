public class Loader extends Process{

    public Loader(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "Loader");
    }

    public void run(){

    }
}