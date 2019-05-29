public class ChannelInterrupt extends Process{

    public ChannelInterrupt(Process parent) {
        this.create(parent, (byte)3, "ChannelInterrupt");
    }

    public void run(){

    }
}
