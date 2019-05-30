public class Idle extends Process{

    public Idle(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)0, "Idle");
    }

    public void run(){
            if (os.CH1 == "Enter"){
                Utils.releaseDynamicResource(os, this, Title.CHANNEL_INTERRUPT, "Enter");
            }
            if (os.CH1 == "Esc"){
                Utils.releaseDynamicResource(os, this, Title.CHANNEL_INTERRUPT, "Esc");
            }
        }
    }
}
