public class JobHelper extends Process{

    public JobHelper(Process parent){
        this.create(parent, (byte)2, "JobHelper");
    }

    public void run(){

    }
}
