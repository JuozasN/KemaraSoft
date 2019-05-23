public class DynamicResource extends Resource {
    private Block parameter;

    public void release(Process process, String parameter) throws ProgramInterrupt {
        process.removeFromOwnedResources(this);
        this.parameter = Block.getBlockFromString(parameter);
        Distributor.distributeResource(this);
    }
}
