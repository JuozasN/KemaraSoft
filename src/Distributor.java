public class Distributor {

    //Kvieciama is Resurso primityvu "request" ir "release"
    public static void distributeResource(Resource resource){
        Process processGettingResource = resource.getTopWaitingProcess();
        //
        resource.removeFromWaitingList(processGettingResource);
    }
}
