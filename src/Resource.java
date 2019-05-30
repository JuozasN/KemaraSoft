import java.util.ArrayList;

public class Resource {
    private static long previousID = 0;
    private long id;
    private String title;
    private Process creator;
    private ArrayList<Process> waitingProcesses;
    private OS os;
    //private ArrayList<Resource> resourceList;

    public Resource(OS os) {
        this.os = os;
        this.id = previousID++;
    }

    public void create(Process creator, String title) {
        OS.addToResourceList(this);
        creator.addToCreatedResources(this);
        this.creator = creator;
        this.title = title;
        this.waitingProcesses = new ArrayList<>();
        os.appendProcessLog("Resource. Resource created: " + this.getTitle() + " by Process: " + this.creator.getTitle() + ".");
    }

    public void delete() {
        this.creator.removeFromCreatedResources(this);
        for(Process p: this.waitingProcesses){
            p.changeState((byte)2); //2 = READY
            //Istrynus resursa, jo laukes procesas tampa pasiruoses???
        }
        OS.removeFromResourceList(this);
        os.appendProcessLog("Resource. Resource deleted: " + this.getTitle() + ".");
        //"naikinamas pats aprasas"...
    }

    public void request(Process process) {
        os.appendProcessLog("Resource. Resource: " + this.getTitle() + " is requested by Process: " + process.getTitle() + ".");
        process.block();
        this.waitingProcesses.add(process);
        Distributor.distributeResource(this);
    }

//    public void release(Process process) {
//        process.removeFromOwnedResources(this);
//        //KAZKA PADARYTI SU ELEMENT LIST...
//        Distributor.distributeResource(this);
//    }

    public void release() {
        os.appendProcessLog("Resource. Resource: " + this.getTitle() + " is being released.");
        // perduodame elementą resursui ir kviečiame paskirstytoją,
        // kad jis perduotų elementą jo laukiančiam procesui
        Distributor.distributeResource(this);
    }

    public String getTitle(){
        return this.title;
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

//    public String getElementsAsString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(elementList.get(0));
//        for(int i = 1; i < elementList.size(); ++i) {
//            sb.append(elementList.get(i));
//        }
//        return(sb.toString());
//    }
}
