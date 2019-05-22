import java.util.ArrayList;

public class Process {
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
    private ArrayList<Resource> ownedResources = new ArrayList<>();
    private ArrayList<Resource> elementList;
    private byte state;
    private byte priority;
    private Process parent;
    private ArrayList<Process> children;
    private String title;

    public Process(){
        this.ID = previousID++;
    }

    public void create(Process parent, byte state, byte priority, ArrayList<Resource> elementList, String title) {
        this.parent = parent;
        this.state = state;
        this.priority = priority;
        this.elementList = elementList;
        this.title = title;
        //TODO: Add to Process List
        parent.addToChildren(this);
        children = new ArrayList<>();
        createdResources = new ArrayList<>();
    }

    public void delete() {
        for(Resource r: createdResources){
            r.delete();
        }
        for(Process c: children){
            c.delete();
        }
        parent.removeFromChildren(this);
        //TODO: Remove from Process List
        for(Resource r: ownedResources){
            r.delete();
        }
    }

    public void suspend() {
        if(state < 3){
            state+=3;
        }
        else {
            //Do something
        }
    }

    public void activate() {
        if(state >= 3){
            state-=3;
        }
        else {
            //Do something
        }
    }

    public void addToChildren(Process children){
        this.children.add(children);
    }

    public void removeFromChildren(Process children){
        this.children.remove(children);
    }
}
