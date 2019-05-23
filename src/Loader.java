public class Loader extends Process{

    public Loader(Process parent){
        this.create(parent, (byte) 3, null, "Loader");
    }

    public void run(){

    }
}
