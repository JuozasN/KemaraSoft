import java.util.ArrayList;

public class Resource {
    private static long previousID = 0;
    private long id;
    private Title title;
    private Process creator;
    private ArrayList<Process> waitingProcesses;
    private OS os;

    public Resource() {
        this.id = previousID++;
    }

    public void create(OS os, Process creator, Title title) {
        this.os = os;
        creator.addToCreatedResources(this);
        this.creator = creator;
        this.title = title;
        this.waitingProcesses = new ArrayList<>();
        os.addToResourceList(this);
        os.appendProcessLog("Resource. Resource created: " + this.getTitle() + " by Process: " + this.creator.getTitle() + ".");
    }

    public void delete() {
        this.creator.removeFromCreatedResources(this);
        for(Process p: this.waitingProcesses){
            p.changeState(State.READY);
        }
        os.removeFromResourceList(this);
        os.appendProcessLog("Resource. Resource deleted: " + this.getTitle() + ".");
    }

    public void request(Process process) {
        os.appendProcessLog("Resource. Resource: " + this.getTitle() + " is requested by Process: " + process.getTitle() + ".");
        process.block();
        Utils.addByPriority(waitingProcesses, process);
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

    public long getId() {
        return id;
    }

    public Title getTitle(){
        return this.title;
    }

    public Process getTopWaitingProcess() {
        return this.waitingProcesses.get(0);
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
