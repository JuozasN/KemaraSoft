public class Distributor {

    //Kvieciama is Resurso primityvu "request" ir "release"
    public static void distributeResource(Resource resource){
        // imame pirmą eilėje laukiantį resurso procesą
        Process topWaitingProcess = resource.getTopWaitingProcess();
        // perduodame procesui resurso elementus
        topWaitingProcess.addToOwnedResources(resource);
        // atblokuojame procesą, gavusį resursą
        topWaitingProcess.activate();
        // pašaliname procesą iš laukiančių resurso procesų sąrašo
        resource.removeFromWaitingList(topWaitingProcess);
        // pašaliname perduotus procesui elementus iš resurso elementų sąrašo
        resource.removeElementList();
        //kviečiame planuotoja
    }
}
