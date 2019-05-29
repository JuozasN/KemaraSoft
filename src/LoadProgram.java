public class LoadProgram extends Process{
    public LoadProgram(Process parent) {
        this.create(parent, (byte)3, "LoadProgram");
    }

    public void run() {

    }
}
