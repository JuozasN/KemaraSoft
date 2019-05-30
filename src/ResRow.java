import javafx.beans.property.SimpleStringProperty;

public class ResRow {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty Title;
    private SimpleStringProperty Elements;

    public ResRow(String LineNo, String Title, String State){
        this.LineNo = new SimpleStringProperty(LineNo);
        this.Title = new SimpleStringProperty(Title);
        this.Elements = new SimpleStringProperty(State);
    }

    public ResRow(int LineNo, Resource resource) {
//        this(String.valueOf(LineNo), resource.getTitle(), String.valueOf(resource.getElementsAsString()));
    }

    public ResRow(String LineNo) {
        this(LineNo, "nil", "nil");
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
