import java.util.ArrayList;

public class Process {
    public final class ProcessState {
        public static final byte RUNNING = 0;
        public static final byte BLOCKED = 1;
        public static final byte READY = 2;
        public static final byte SUSPENDED = 3;
        public static final byte BLOCKED_SUSPENDED = 3;
        public static final byte READY_SUSPENDED = 3;
    }

    private ArrayList<Process> processList = new ArrayList<>();
    private int ID;
    private Short[] savedRegisters = new Short[7];
    private ArrayList<Resource> createdResources = new ArrayList<>();
    private ArrayList<Resource> ownedResources = new ArrayList<>();
    private byte state;
    private byte priority;
    private Process parent;
    private ArrayList<Process> children;
    private String title;

    public Process(Process parent, byte state, ArrayList<Resource> elementList, String title) {
        this.parent = parent;
        this.state = state;
        // TODO: baigti inicializuoti (kurti procesą)
    }

    public static void delete() {

    }

    public static void suspend() {

    }

    public static void activate() {

    }
}
