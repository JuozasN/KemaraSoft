public class JobHelper extends Process{

    public JobHelper(OS os, Process parent){
        super(os);
        this.create(parent, (byte)2, "JobHelper");
    }

    public void run(){

    }
}
