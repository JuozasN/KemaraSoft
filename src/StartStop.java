public class StartStop extends Process{

    public StartStop(OS os) {
        super(os);
    }

    public void run() {
        switch (step) {
            case 0:
                createResources();
                createChildren();

                stepIncrement();
                Distributor.request(this, Title.POS_END);
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
        new StaticResource().create(os, this, Title.USER_MEMORY, Utils.UM_BLOCK_COUNT);
        new StaticResource().create(os, this, Title.KERNEL_MEMORY, Utils.KM_BLOCK_COUNT);
        new StaticResource().create(os, this, Title.OUTPUT, 0x1);
    }

    public void createChildren(){
        new LoadProgram(os, this);
        new JCL(os, this);
        new Loader(os, this);
        new MainProc(os, this);
        new Interrupt(os, this);
        new ChannelInterrupt(os, this);
        new PutLine(os, this);
        new Idle(os, this);
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
