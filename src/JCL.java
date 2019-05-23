public class JCL extends Process{

    public JCL(Process parent){
        this.create(parent, ProcessState.READY, (byte) 3, null, "JCL");
    }

    public void run(){

    }
}
