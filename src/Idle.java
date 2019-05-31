public class Idle extends Process{

    public Idle(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)0, "Idle");
    }

    public void run(){
        os.inputConfirm.setDisable(false);
        os.inputField.setDisable(false);
        if (os.isEnterPressed()){
            Utils.releaseDynamicResource(os, this, Title.CHANNEL_INTERRUPT, "Enter");
            os.setEnterPressed(false);
        }
        if (os.isEscPressed()){
            Utils.releaseDynamicResource(os, this, Title.CHANNEL_INTERRUPT, "Esc");
            os.setEscPressed(false);
        }
    }
}
