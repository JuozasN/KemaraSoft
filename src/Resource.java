import java.util.ArrayList;
import java.util.Random;

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
        this.resourceList = new ArrayList<>();
        this.elementList = new ArrayList<>();
        this.waitingProcesses = new ArrayList<>();
        Random r = new Random();
        this.id = r.nextInt(999) + 1;
    }

    public void delete() {

    }

    public void request() {

    }

    public void release() {

    }
}
