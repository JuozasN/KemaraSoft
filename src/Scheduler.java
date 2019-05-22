import java.util.ArrayList;

public class Scheduler {
    private OS os;
    private ArrayList<Process> blockedProcessList;
    private ArrayList<Process> readyProcessList;

    public Scheduler(OS os, ArrayList<Process> blockedProcessList, ArrayList<Process> readyProcessList) {
        this.os = os;
        this.blockedProcessList = blockedProcessList;
        this.readyProcessList = readyProcessList;
    }

    public Process runNextReadyProcess(Process runningProcess) {
        if (runningProcess.isBlocked()) {
            OS.removeFromList(runningProcess);
            OS.addToList(runningProcess, Process.ProcessState.BLOCKED);
        }

        if (!readyProcessList.isEmpty()) {
            Process processToRun = readyProcessList.get(0);
            os.runProcess(processToRun);
            OS.removeFromList(processToRun);
            return processToRun;
        }

        return null;
    }
}
