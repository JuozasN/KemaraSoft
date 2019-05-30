public class PutLine extends Process{

    public PutLine(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "PutLine");
    }

    public void run(){
        switch (step) {
            case 0:
                Distributor.request(this, Title.MEM_LINE);
                stepIncrement();
                return;
            case 1:
                Distributor.request(this, Title.OUTPUT);
                stepIncrement();
                return;
            case 2:
                os.setOutputField(((DynamicResource)Utils.getResource(this.ownedResources, Title.MEM_LINE)).getParameter());
                Utils.getResource(this.ownedResources, Title.MEM_LINE).release();
                stepReset();
                return;
            default:
                System.err.println("Impossible step at PutLine.run()");
                System.exit(0);
        }
    }
}
