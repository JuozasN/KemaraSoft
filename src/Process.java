import java.util.ArrayList;

public abstract class Process {
//    protected ArrayList<Process> processList = new ArrayList<>();
    protected static long previousID = 0;
    protected long id;
    protected Short[] savedRegisters = new Short[7];
    protected ArrayList<Resource> createdResources;
    protected ArrayList<Resource> ownedResources = new ArrayList<>();
//    protected ArrayList<Resource> elementList;
    protected State state;
    protected byte priority;
    protected Process parent;
    protected ArrayList<Process> children;
    protected String title;
    protected int step;
    protected OS os;

    public Process(OS os){
        this.id = previousID++;
        this.os = os;
    }

    public void create(Process parent, byte priority, String title) {
        this.parent = parent;
        this.priority = priority;
//        this.elementList = elementList;
        this.title = title;
        if (parent != null)
            parent.addToChildren(this);
        children = new ArrayList<>();
        createdResources = new ArrayList<>();
        state = State.READY;
        step = 0;
        os.addToProcessList(this);
        if (parent == null){
            os.appendProcessLog("Process. Process created: " + this.getTitle() + " by OS.");
        }else
            os.appendProcessLog("Process. Process created: " + this.getTitle() + " by Parent: " + this.parent.getTitle() + ".");
        //kvieciamas planuotojas..
    }

    public void delete() {
        for(Resource r: createdResources){
            r.delete();
        }
        for(Process c: children){
            c.delete();
        }
        parent.removeFromChildren(this);
//        for(Resource r: ownedResources){
//            r.delete();
//        }
        ownedResources.clear();
        os.removeFromProcessList(this);
        os.appendProcessLog("Process. Process deleted: " + this.getTitle() + ".");
        //kvieciamas planuotojas..
    }

    public void suspend() {
        if (state == State.RUNNING)
            state = State.SUSPENDED;
        else if (state == State.BLOCKED)
            state = State.BLOCKED_SUSPENDED;
        else if (state == State.READY)
            state = State.READY_SUSPENDED;
        os.appendProcessLog("Process. Process suspended: " + this.getTitle() + ".");

        //kvieciamas planuotojas..
    }

    public void activate() {
        if (state == State.BLOCKED_SUSPENDED)
            state = State.BLOCKED;
        else if (state == State.READY_SUSPENDED)
            state = State.READY;
        else if (state == State.BLOCKED)
            state = State.READY;
        os.appendProcessLog("Process. Process activated: " + this.getTitle() + ".");

        //kvieciamas planuotojas..
    }

    public void block(){
        state = State.BLOCKED;
        os.appendProcessLog("Process. Process blocked: " + this.getTitle() + ".");
    }
    /**
     * Abstract method that enables Process algorithm implementation on object creation
     */
    public abstract void run() throws ProgramInterrupt;

    public boolean isBlocked() {
        return state == State.BLOCKED;
    }
    public void addToChildren(Process children){
        this.children.add(children);
    }

    public long getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public ArrayList<Resource> getOwnedResources() {
        return ownedResources;
    }

//    public ArrayList<Block> getElementList() {
//        return this.elementList;
//    }


    public String getTitle() {
        return title;
    }

    public byte getPriority(){
        return priority;
    }

    public void removeFromChildren(Process children){
        this.children.remove(children);
    }

    public void addToCreatedResources(Resource resource) {
        this.createdResources.add(resource);
    }

    public void removeFromCreatedResources(Resource resource) {
        this.createdResources.remove(resource);
    }

    public void removeFromOwnedResources(Resource resource) {
        this.ownedResources.remove(resource);
    }

    public void removeOwnedResources() {
        this.ownedResources.clear();
    }

    public void addToOwnedResources(Resource resource) {
        this.ownedResources.add(resource);
    }

    public Resource getCreatedResource(Title title){
        for(Resource r: createdResources){
            if(r.getTitle() == title)
                return r;
        }
        //System.err.format("Resource %s was not found", title);
        return null;
    }
//
//    public void removeFromOwnedResources(Resource resource) {
//        this.ownedResources.remove(resource);
//    }

//    public void addToOwnedResources(Resource resource) { this.ownedResources.add(resource); }

//    public void addToOwnedResources(Resource resource) {
//        this.ownedResources.addAll(resource.getElementList());
//    }

    public void stepIncrement(){
        step++;
    }

    public void stepReset(){
        step = 0;
    }

    public void changeState(State state) {
        this.state = state;
    }
}
