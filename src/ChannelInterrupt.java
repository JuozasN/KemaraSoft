public class ChannelInterrupt extends Process{

    public ChannelInterrupt(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "ChannelInterrupt");
    }

    public void run(){
        switch (step) {
            case 0:
                Distributor.request(this, Title.CHANNEL_INTERRUPT);
                stepIncrement();
                return;
            case 1:
                String channelInterrupt = ((DynamicResource) getOwnedResources().get(0)).getParameter();
                if (channelInterrupt.equals("Enter")) {
                    // atpažįstama komanda IO eilutėje atmintyje
                    if (os.processCommand().equals(Command.LOAD)) {         // load a program
                        Utils.releaseDynamicResource(os, this, Title.FILE_NAME, os.getFileNameFromInput());
                    } else if (os.processCommand().equals(Command.QUIT)) { // quit current program
                        Utils.releaseDynamicResource(os, this, Title.INTERRUPT, "HALT");
                    } else if (os.processCommand().equals(Command.SHOW)) { // show another program currently running in OS
                        // change current VM
                    }
                    return;
                } else if (channelInterrupt.equals("Esc")) {
                    if (os.getRealMachine().getPTR() == 0) {
                        Utils.releaseDynamicResource(os, this, Title.POS_END, "END");
                        return;
                    } else {
                        Utils.releaseDynamicResource(os, this, Title.INTERRUPT, "HALT");
                        return;
                    }
                }
                return;
//            case 0:
//                Distributor.request(this, Title.CHANNEL_INTERRUPT);
//                stepIncrement();
//                return;
//            case 1:
//                String channelInterrupt = ((DynamicResource) getOwnedResources().get(0)).getParameter();
//                if (channelInterrupt.equals("Enter")) {
//                    if (os.getRealMachine().getPTR() == 0) {
//                        Utils.releaseDynamicResource(os, this, Title.FILE_NAME, "File");
//                        return;
//                    } else {
//                        Utils.releaseDynamicResource(os, this, Title.USER_INPUT, "INPUT");
//                        return;
//                    }
//                } else if (channelInterrupt.equals("Esc")) {
//                    if (os.getRealMachine().getPTR() == 0) {
//                        Utils.releaseDynamicResource(os, this, Title.POS_END, "END");
//                        return;
//                    } else {
//                        Utils.releaseDynamicResource(os, this, Title.INTERRUPT, "HALT");
//                        return;
//                    }
//                }
//                return;
        }
    }
}
