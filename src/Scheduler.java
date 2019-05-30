public class Scheduler {
    private OS os;

    public Scheduler(OS os) {
        this.os = os;
    }

    public Process runNextReadyProcess(Process runningProcess) {
        os.appendProcessLog("Scheduler. Attempting to get next ready process.");
        if (runningProcess.isBlocked()) {
            os.removeFromProcessList(runningProcess);
            os.addToProcessList(runningProcess);
        }

        if (!OS.processList.isEmpty()) {
            Process processToRun = OS.processList.get(0); // get first process in process queue
            os.runProcess(processToRun);    // assign processor to process; change process state to 'running'
            os.removeFromProcessList(processToRun);    // remove the process from ready processes list
            return processToRun;
        }
        os.appendProcessLog("Scheduler. There are no ready processes.");
        return null;
    }
}
