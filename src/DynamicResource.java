public class DynamicResource extends Resource {
    private String parameter;

    public DynamicResource(String parameter){
        super();
        this.parameter = parameter;
    }

    public void release(){
        Distributor.distributeResource(this);
    }

    public String getParameter() {
        return this.parameter;
    }
}
