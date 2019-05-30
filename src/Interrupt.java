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
                        Resource h = new DynamicResource("HALT");
                        h.create(os, this, Title.INTERRUPT_MESSAGE);
                        h.release();
                        return;
                    case "PUT":

                        return;
                    case "GET":

                        return;
                    default:
                        System.err.println("Unhandled interrupt.");
                        System.exit(0);
                }
                return;
            default:
                System.err.println("Impossible step at StartStop.run()");
                System.exit(0);
        }
    }
}
