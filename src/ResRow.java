import javafx.beans.property.SimpleStringProperty;

public class ResRow {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty Title;
    private SimpleStringProperty Elements;

    public ResRow(String LineNoCol, String TitleCol, String StateCol){
        this.LineNo = new SimpleStringProperty(LineNoCol);
        this.Title = new SimpleStringProperty(TitleCol);
        this.Elements = new SimpleStringProperty(StateCol);
    }

    public ResRow(String VMLineNo) {
        this(VMLineNo, "nil", "nil");
    }

    public void setInitial() {
        setTitleCol("nil");
        setElements("nil");
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

    public String getElements() {
        return Elements.get();
    }

    public SimpleStringProperty elementsProperty() {
        return Elements;
    }

    public void setTitleCol(String titleCol) {
        this.Title.set(titleCol);
    }

    public void setElements(String stateCol) {
        this.Elements.set(stateCol);
    }
}
