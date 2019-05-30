import java.util.HashMap;
import java.util.Map;

public class Distributor {

    public static Map<DynamicResource.Title, Process> waitingProcesses;

    public Distributor(){
        waitingProcesses = new HashMap<>();
    }

    //Kvieciama is Resurso primityvu "request" ir "release"
    public static void distributeResource(Resource resource){
        if(resource instanceof DynamicResource){
            DynamicResource.Title title = ((DynamicResource)resource).getTitle();
            Process process = waitingProcesses.get(title);
            process.addToOwnedResources(resource);
            process.activate();
        }
        // imame pirmą eilėje laukiantį resurso procesą
        Process topWaitingProcess = resource.getTopWaitingProcess();
        // perduodame procesui resurso elementus

        //topWaitingProcess.addToOwnedResources(resource);

        // atblokuojame procesą, gavusį resursą
        topWaitingProcess.activate();
        // pašaliname procesą iš laukiančių resurso procesų sąrašo
        resource.removeFromWaitingList(topWaitingProcess);

        // pašaliname perduotus procesui elementus iš resurso elementų sąrašo
        //resource.removeElementList();

        //kviečiame planuotoja
    }

    public static void request(Process process, DynamicResource.Title title){
        process.block();
        waitingProcesses.put(title, process);
    }
}
