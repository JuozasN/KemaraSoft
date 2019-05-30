public class ChannelInterrupt extends Process{

    public ChannelInterrupt(OS os, Process parent) {
        super(os);
        this.create(parent, (byte)3, "ChannelInterrupt");
    }

    public void run(){

    }
}
