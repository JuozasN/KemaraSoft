public class Scheduler {
    private OS os;

    public Scheduler(OS os) {
        this.os = os;
    }

    public Process runNextReadyProcess() {
        os.appendProcessLog("Scheduler. Attempting to get next ready process.");
        for(Process p: os.processList){

            if(p.getState() == Process.ProcessState.READY){
                os.appendProcessLog("Scheduler. Running " + p.getTitle() + " process.");
                return p;
            }
        }
        os.appendProcessLog("Scheduler. There are no ready processes.");
        return null;
    }
}
