import javafx.beans.property.SimpleStringProperty;

public class PQRow {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty Id;
    private SimpleStringProperty Title;
    private SimpleStringProperty State;
    private SimpleStringProperty Priority;

    public PQRow(String LineNo, String Id, String Title, String State, String Priority){
        this.LineNo = new SimpleStringProperty(LineNo);
        this.Id = new SimpleStringProperty(Id);
        this.Title = new SimpleStringProperty(Title);
        this.State = new SimpleStringProperty(State);
        this.Priority = new SimpleStringProperty(Priority);
    }

    public PQRow(int LineNo, Process process) {
        this(String.valueOf(LineNo), String.valueOf(process.getId()), process.getTitle(), String.valueOf(process.getState()),
                String.valueOf(process.getPriority()));
    }

    public PQRow(String LineNo) {
        this(LineNo, "nil", "nil", "nil", "nil");
    }

    public void setInitial() {
        setIdCol("nil");
        setTitleCol("nil");
        setStateCol("nil");
        setPriorityCol("nil");
    }

    public String getLineNo() {
        return LineNo.get();
    }

    public SimpleStringProperty lineNoProperty() {
        return LineNo;
    }

    public String getTitle() {
        return Title.get();
    }

    public SimpleStringProperty titleProperty() {
        return Title;
    }

    public String getId() {
        return Id.get();
    }

    public SimpleStringProperty idProperty() {
        return Id;
    }

    public String getState() {
        return State.get();
    }

    public SimpleStringProperty stateProperty() {
        return State;
    }

    public String getPriority() {
        return Priority.get();
    }

    public SimpleStringProperty priorityProperty() {
        return Priority;
    }

    public void setTitleCol(String titleCol) {
        this.Title.set(titleCol);
    }

    public void setIdCol(String id) {
        this.Id.set(id);
    }

    public void setStateCol(String stateCol) {
        this.State.set(stateCol);
    }

    public void setPriorityCol(String priorityCol) {
        this.Priority.set(priorityCol);
    }
}
