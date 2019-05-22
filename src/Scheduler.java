import java.util.ArrayList;

public class Scheduler {
    private OS os;

    public Scheduler(OS os) {
        this.os = os;
    }

    public Process runNextReadyProcess(Process runningProcess) {
        if (runningProcess.isBlocked()) {
            OS.removeFromList(runningProcess);
            OS.addToList(runningProcess, Process.ProcessState.BLOCKED);
        }

        if (!OS.readyProcessList.isEmpty()) {
            Process processToRun = OS.readyProcessList.get(0); // get first process in process queue
            os.runProcess(processToRun);    // assign processor to process; change process state to 'running'
            OS.removeFromList(processToRun);    // remove the process from ready processes list
            return processToRun;
        }

        return null;
    }
}
