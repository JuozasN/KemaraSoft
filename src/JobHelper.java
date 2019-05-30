public class JobHelper extends Process{

    public JobHelper(OS os, Process parent){
        super(os);
        this.create(parent, (byte)2, "JobHelper");
    }

    public void run(){
        switch (step) {
            case 0:
                Utils.releaseDynamicResource(os, this, Title.DATA_LOAD, "LOAD");
                Distributor.request(this, Title.LOADER_END);
                stepIncrement();
                return;
            case 1:
                Utils.getResource(os.resourceList, Title.KERNEL_MEMORY).request(this);
                stepIncrement();
                return;
            case 2:
                // kuriama puslapių lentelė...
                Utils.getResource(os.resourceList, Title.USER_MEMORY).request(this);
                stepIncrement();
                return;
            case 3:
                new VM(os, os.getRealMachine());
                // blokavimas laukiant proceso "interrupt" pranešimo
                stepIncrement();
                return;
            case 4:
                // Resource interrupt = < request interrupt >
                // VM virtualMachine = (VM) interrupt.getParent()
                // virtualMachine.pause()
                // if (interrupt.getParameter() == 'HALT') {
                //      virtualMachine.halt();
                //      release(Title.USER_MEMORY);
                //      Utils.releaseDynamicResource(os, this, Title.MAIN_PROGRAM, "0");
                //      return;
                // } else if (interrupt.getParameter() == 'GET' {
                //      Utils.getResource(os.resourceList, Title.USER_INPUT).request(this);
                // } else {
                //      showOutput(interrupt.getValue());
                // }
                //      virtualMachine.start();
                return;

        }
    }
}
