public class DynamicResource extends Resource {
    enum Title {
        POS_END, FILE_NAME, KERNEL_PROGRAM,
        MEM_LINE, MAIN_PROGRAM, DATA_LOAD,
        LOADER_END, INTERRUPT, CHANNEL_INTERRUPT
    }

    private Title title;
    private String parameter;

    public DynamicResource(OS os, Title title, String parameter){
        super(os);
        this.title = title;
        this.parameter = parameter;
    }

    public void release(){
        Distributor.distributeResource(this);
    }

    public Title getTitle() {
        return title;
    }
}
