import javafx.beans.property.SimpleStringProperty;

public class PQRow {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty Title;
    private SimpleStringProperty State;
    private SimpleStringProperty Priority;

    public PQRow(String LineNoCol, String TitleCol, String StateCol, String PriorityCol){
        this.LineNo = new SimpleStringProperty(LineNoCol);
        this.Title = new SimpleStringProperty(TitleCol);
        this.State = new SimpleStringProperty(StateCol);
        this.Priority = new SimpleStringProperty(PriorityCol);
    }

    public PQRow(String VMLineNo) {
        this(VMLineNo, "nil", "nil", "nil");
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
