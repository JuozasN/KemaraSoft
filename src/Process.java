import java.util.ArrayList;

public abstract class Process {
    public final class ProcessState {
        public static final byte RUNNING = 0;
        public static final byte BLOCKED = 1;
        public static final byte READY = 2;
        public static final byte SUSPENDED = 3;
        public static final byte BLOCKED_SUSPENDED = 4;
        public static final byte READY_SUSPENDED = 5;
    }

    private ArrayList<Process> processList = new ArrayList<>();
    private static long previousID = 0;
    private long ID;
    private Short[] savedRegisters = new Short[7];
    private ArrayList<Resource> createdResources;
    private ArrayList<Block> ownedResources = new ArrayList<>();
//    private ArrayList<Block> elementList;
    private byte state;
    private byte priority;
    private Process parent;
    private ArrayList<Process> children;
    private String title;

    public Process(){
        this.ID = previousID++;
    }

    public void create(Process parent, byte state, byte priority, String title) {
        this.parent = parent;
        this.state = state;
        this.priority = priority;
//        this.elementList = elementList;
        this.title = title;
        OS.addToProcessList(this, priority);
        parent.addToChildren(this);
        children = new ArrayList<>();
        createdResources = new ArrayList<>();
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
        OS.removeFromProcessList(this);
        //kvieciamas planuotojas..
    }

    public void suspend() {
        if(state < ProcessState.SUSPENDED){
            state += ProcessState.SUSPENDED;
        }

        //kvieciamas planuotojas..
    }

    public void activate() {
        if(state >= ProcessState.BLOCKED_SUSPENDED){
            state -= ProcessState.SUSPENDED;
        } else if (state == ProcessState.BLOCKED) {
            state = ProcessState.READY;
        }

        //kvieciamas planuotojas..
    }

    /**
     * Abstract method that enables Process algorithm implementation on object creation
     */
    public abstract void run();

    public boolean isBlocked() {
        return state == ProcessState.BLOCKED;
    }
    public void addToChildren(Process children){
        this.children.add(children);
    }

    public byte getState() {
        return state;
    }

    public ArrayList<Block> getOwnedResources() {
        return this.ownedResources;
    }

//    public ArrayList<Block> getElementList() {
//        return this.elementList;
//    }

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

    public void removeFromOwnedResources(Block resourceElement) {
        this.ownedResources.remove(resourceElement);
    }

    public void removeOwnedResources() {
        this.ownedResources.clear();
    }
//
//    public void removeFromOwnedResources(Resource resource) {
//        this.ownedResources.remove(resource);
//    }

//    public void addToOwnedResources(Resource resource) { this.ownedResources.add(resource); }

    public void addToOwnedResources(Resource resource) {
        this.ownedResources.addAll(resource.getElementList());
    }

    public void changeState(byte state) {
        this.state = state;
    }
}
