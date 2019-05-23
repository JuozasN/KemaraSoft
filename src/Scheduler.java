public class Scheduler {
    private OS os;

    public Scheduler(OS os) {
        this.os = os;
    }

    public Process runNextReadyProcess(Process runningProcess) {
        if (runningProcess.isBlocked()) {
            OS.removeFromProcessList(runningProcess);
            OS.addToProcessList(runningProcess, Process.ProcessState.BLOCKED);
        }

        if (!OS.processList.isEmpty()) {
            Process processToRun = OS.processList.get(0); // get first process in process queue
            os.runProcess(processToRun);    // assign processor to process; change process state to 'running'
            OS.removeFromProcessList(processToRun);    // remove the process from ready processes list
            return processToRun;
        }

        return null;
    }
}
