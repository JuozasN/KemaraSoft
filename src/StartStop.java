public class StartStop extends Process{

    public StartStop() {
        this.create(null, (byte)4, "StartStop");
    }

    public void run() {
        switch (step) {
            case 0:
                createResources();
                createChildren();

                step = 1;
                //ownedResources.get("POS pabaiga").request(this);
                return;


            case 1:
                deleteChildren();
                deleteResources();
        }
    }
    public void createResources(){

    }

    public void createChildren(){
        children.add(new LoadProgram(this));
        children.add(new JCL(this));
        children.add(new Loader(this));
        children.add(new MainProc(this));
        children.add(new Interrupt(this));
        children.add(new ChannelInterrupt(this));
        children.add(new PutLine(this));
        children.add(new Idle(this));
    }

    public void deleteResources() {
        for(Resource r: createdResources) {
            r.delete();
        }
    }

    public void deleteChildren(){
        for(Process p: children){
            p.delete();
        }
    }
}
