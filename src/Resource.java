import java.util.ArrayList;

public class Resource {
    private int id;
    private String title;
    private Process creator;
    private ArrayList<Boolean> elementList;
    private ArrayList<Process> waitingProcesses;
    private ArrayList<Resource> resourceList;

    public Resource(Process creator, String title) {
        this.creator = creator;
        this.title = title;
    }

    public void delete() {

    }

    public void request() {

    }

    public void release() {

    }
}
