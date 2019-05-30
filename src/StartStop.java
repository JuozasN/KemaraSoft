public class StartStop extends Process{

    public StartStop() {
        this.create(null, (byte)4, "StartStop");
    }

    public void run() {
        switch (step) {
            case 0:
                createResources();
                createChildren();

                stepIncrement();
                Distributor.request(this, DynamicResource.Title.POS_END);
                return;
            case 1:
                deleteChildren();
                deleteResources();
                stepReset();
                return;
            default:
                System.err.println("Impossible step at StartStop.run()");
                System.exit(0);
        }
    }
    public void createResources(){

    }

    public void createChildren(){
        new LoadProgram(this);
        new JCL(this);
        new Loader(this);
        new MainProc(this);
        new Interrupt(this);
        new ChannelInterrupt(this);
        new PutLine(this);
        new Idle(this);
    }

    public void deleteResources() {
        for(Resource r: createdResources) {
            r.delete();
        }
    }

    public void deleteChildren(){
        for(Process p: children){
            p.delete();
        }
    }
}
