import java.util.HashMap;
import java.util.Map;

public class Distributor {

    public static OS os;
    public static Map<Title, Process> waitingProcesses;

    public Distributor(OS os){
        this.os = os;
        os.appendProcessLog("Distributor. Initializing distributor.");
        waitingProcesses = new HashMap<>();
    }

    //Kvieciama is Resurso primityvu "request" ir "release"
    public static void distributeResource(Resource resource){
        os.appendProcessLog("Distributor. Distributing resource: " + resource.getTitle());
        Process process = null;
        if(resource instanceof DynamicResource) {
            Title title = resource.getTitle();
            process = waitingProcesses.remove(title);
        }
        if(resource instanceof StaticResource){
            process = resource.getTopWaitingProcess();
            resource.removeFromWaitingList(process);
        }

        process.addToOwnedResources(resource);
        process.activate();

        // pašaliname perduotus procesui elementus iš resurso elementų sąrašo

        //resource.removeElementList();

        //kviečiame planuotoja
    }

    public static void request(Process process, Title title){
        os.appendProcessLog("Distributor. " + process.getTitle() + "requesting " + title + ".");
        process.block();
        waitingProcesses.put(title, process);
    }
}
