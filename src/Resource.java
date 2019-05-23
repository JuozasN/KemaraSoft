import java.util.ArrayList;

public class Resource {
    private static long previousID = 0;
    private long id;
    private String title;
    private Process creator;
    private ArrayList<Block> elementList;
    private ArrayList<Process> waitingProcesses;
    //private ArrayList<Resource> resourceList;

    public Resource() {
        this.id = previousID++;
    }

    public void create(Process creator, String title) {
        OS.mainResourceList.add(this);
        creator.addToCreatedResources(this);
        this.creator = creator;
        this.title = title;
        this.elementList = new ArrayList<>();
        this.waitingProcesses = new ArrayList<>();
    }

    public void delete() {
        this.creator.removeFromCreatedResources(this);
        this.elementList.clear();
        for(Process p: this.waitingProcesses){
            p.changeState((byte)2); //2 = READY
        }
        OS.mainResourceList.remove(this);
        //"naikinamas pats aprasas"...
    }

    public void request(Process process) {
        process.changeState((byte)1); //1 = BLOCKED
        this.waitingProcesses.add(process);
        Distributor.distributeResource(this);
    }

    public void release(Process process) {
        process.removeFromOwnedResources(this);
        //KAZKA PADARYTI SU ELEMENT LIST...
        Distributor.distributeResource(this);
    }

    public Process getTopWaitingProcess() {
        return this.waitingProcesses.get(waitingProcesses.size() - 1); //grazina paskutini procesa is laukianciu sio resurso sarase
                                                                //turetu buti su top priority/anksciausiai ikeltas.
    }

    public void removeFromWaitingList(Process process) {
        this.waitingProcesses.remove(process);
    }
}
