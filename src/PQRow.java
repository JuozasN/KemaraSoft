import javafx.beans.property.SimpleStringProperty;

public class PQRow {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty Title;
    private SimpleStringProperty State;
    private SimpleStringProperty Priority;

    public PQRow(String LineNo, String Title, String State, String Priority){
        this.LineNo = new SimpleStringProperty(LineNo);
        this.Title = new SimpleStringProperty(Title);
        this.State = new SimpleStringProperty(State);
        this.Priority = new SimpleStringProperty(Priority);
    }

    public PQRow(int LineNo, Process process) {
        this(String.valueOf(LineNo), process.getTitle(), String.valueOf(process.getState()),
                String.valueOf(process.getPriority()));
    }

    public PQRow(String LineNo) {
        this(LineNo, "nil", "nil", "nil");
    }

    public void setInitial() {
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

    public void setStateCol(String stateCol) {
        this.State.set(stateCol);
    }

    public void setPriorityCol(String priorityCol) {
        this.Priority.set(priorityCol);
    }
}
