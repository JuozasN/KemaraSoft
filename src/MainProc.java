public class MainProc extends Process{

    public MainProc(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "MainProc");
    }

    public void run(){
        switch(step){
            case 0:
                Distributor.request(this, Title.MAIN_PROGRAM);
                stepIncrement();
                return;
            case 1:
                if (Integer.parseInt(((DynamicResource)Utils.getResource(this.ownedResources, Title.MAIN_PROGRAM)).getParameter()) == 0){
                    Utils.getProcess(this.children, "JobHelper").delete();
                } else {
                    new JobHelper(os, this);
                }
                stepReset();
                return;
            default:
                System.err.println("Impossible step at MainProc.run()");
                System.exit(0);
        }
    }
}
