public class PutLine extends Process{

    public PutLine(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "PutLine");
    }

    public void run(){

    }
}
