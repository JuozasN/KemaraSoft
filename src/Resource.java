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
        OS.addToResourceList(this);
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
        OS.removeFromResourceList(this);
        //"naikinamas pats aprasas"...
    }

    public void request(Process process) {
        process.changeState(Process.ProcessState.BLOCKED);
        this.waitingProcesses.add(process);
        Distributor.distributeResource(this);
    }

//    public void release(Process process) {
//        process.removeFromOwnedResources(this);
//        //KAZKA PADARYTI SU ELEMENT LIST...
//        Distributor.distributeResource(this);
//    }

    public void release(Block element) {
        // perduodame elementą resursui ir kviečiame paskirstytoją,
        // kad jis perduotų elementą jo laukiančiam procesui
        this.elementList.add(element);
        Distributor.distributeResource(this);
    }

    public String getTitle(){
        return this.title;
    }

    public ArrayList<Block> getElementList() {
        return this.elementList;
    }

    public void removeElementList() {
        this.elementList.clear();
    }

    public Process getTopWaitingProcess() {
        return this.waitingProcesses.get(waitingProcesses.size() - 1); //grazina paskutini procesa is laukianciu sio resurso sarase
                                                                //turetu buti su top priority/anksciausiai ikeltas.
    }

    public void removeFromWaitingList(Process process) {
        this.waitingProcesses.remove(process);
    }

    public boolean isProcessWaiting(Process process) {
        return this.waitingProcesses.contains(process);
    }

    public String getElementsAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(elementList.get(0));
        for(int i = 1; i < elementList.size(); ++i) {
            sb.append(elementList.get(i));
        }
        return(sb.toString());
    }
}
