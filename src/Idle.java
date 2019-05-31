public class Idle extends Process{

    public Idle(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)0, "Idle");
    }

    public void run(){
        if (os.enterPushed){
            Utils.releaseDynamicResource(os, this, Title.CHANNEL_INTERRUPT, "Enter");
            os.enterPushed = false;
        }
        if (os.escPushed){
            Utils.releaseDynamicResource(os, this, Title.CHANNEL_INTERRUPT, "Esc");
            os.escPushed = false;
        }
    }
}
