public class Loader extends Process{

    public Loader(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "Loader");
    }

    public void run() {
        switch (step) {
            case 0:
                Distributor.request(this, Title.DATA_LOAD);
                stepIncrement();
                return;
            case 1:
                Utils.getResource(os.resourceList, Title.KERNEL_MEMORY).request(this);
                stepIncrement();
                return;
            case 2:
                Utils.getResource(os.resourceList, Title.USER_MEMORY).request(this);
                stepIncrement();
                return;
            case 3:
                //os.XCHG();
                // atlaisvinamas supervizorinės atminties resursas
                Utils.releaseDynamicResource(os, this, Title.LOADER_END, "END");
                stepReset();
                return;
            default:
                System.err.println("Impossible step at StartStop.run()");
                System.exit(0);
        }
    }
}