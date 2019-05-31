public class GetLine extends Process{

    public GetLine(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "GetLine");
    }

    public void run(){
//        switch (step) {
//            case 0:
//                Distributor.request(this, Title.MEM_LINE);
//                stepIncrement();
//                return;
//            case 1:
//                Distributor.request(this, Title.USER_INPUT);
//                stepIncrement();
//                return;
//            case 2:
//                os.setOutputField((Utils.releaseDynamicResource(os, this, Title.MEM_LINE););
//                Utils.getResource(this.ownedResources, Title.MEM_LINE).release();
//                stepReset();
//                return;
//            default:
//                System.err.println("Impossible step at GetLine.run()");
//                System.exit(0);
//        }
    }
}