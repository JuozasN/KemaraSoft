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

    protected ArrayList<Process> processList = new ArrayList<>();
    protected static long previousID = 0;
    protected long ID;
    protected Short[] savedRegisters = new Short[7];
    protected ArrayList<Resource> createdResources;
    protected ArrayList<Resource> ownedResources = new ArrayList<>();
    protected ArrayList<Resource> elementList;
    protected byte state;
    protected byte priority;
    protected Process parent;
    protected ArrayList<Process> children;
    protected String title;

    public Process(){
        this.ID = previousID++;
    }

    public void create(Process parent, byte priority, ArrayList<Resource> elementList, String title) {
        this.parent = parent;
        this.state = state;
        this.priority = priority;
        this.elementList = elementList;
        this.title = title;
        OS.addToProcessList(this, priority);
        parent.addToChildren(this);
        children = new ArrayList<>();
        createdResources = new ArrayList<>();
        state = ProcessState.READY;
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
        for(Resource r: ownedResources){
            r.delete();
        }
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

    public void changeState(byte state) {
        this.state = state;
    }
}
