public class Interrupt extends Process{

    public Interrupt(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "Interrupt");
    }

    public void run(){
        switch (step) {
            case 0:
                Distributor.request(this, Title.INTERRUPT);
                stepIncrement();
                return;
            case 1:
                DynamicResource r = (DynamicResource)Utils.getResource(this.ownedResources, Title.INTERRUPT);
                switch (r.getParameter()) {
                    case "HALT":
                        Utils.releaseDynamicResource(os, this, Title.INTERRUPT_MESSAGE, "HALT");
                        return;
                    case "PUT":
                        Utils.releaseDynamicResource(os, this, Title.INTERRUPT_MESSAGE, "PUT");
                        return;
                    case "GET":
                        Utils.releaseDynamicResource(os, this, Title.INTERRUPT_MESSAGE, "GET");
                        return;
                    default:
                        System.err.println("Unhandled interrupt.");
                        System.exit(0);
                }
                stepReset();
                return;
            default:
                System.err.println("Impossible step at StartStop.run()");
                System.exit(0);
        }
    }
}
