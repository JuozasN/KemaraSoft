import javafx.beans.property.SimpleStringProperty;

public class ResRow {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty Id;
    private SimpleStringProperty Title;
    private SimpleStringProperty Elements;

    public ResRow(String LineNo, String Id, String Title, String State){
        this.LineNo = new SimpleStringProperty(LineNo);
        this.Id = new SimpleStringProperty(Id);
        this.Title = new SimpleStringProperty(Title);
        this.Elements = new SimpleStringProperty(State);
    }

    public ResRow(int LineNo, Resource resource) {
//        this(String.valueOf(LineNo), resource.getTitle(), String.valueOf(resource.getElementsAsString()));
        this(String.valueOf(LineNo), String.valueOf(resource.getId()), resource.getTitle().toString(), "");
    }

    public ResRow(String LineNo) {
        this(LineNo, "nil", "nil", "nil");
    }

    public void setInitial() {
        setIdCol("nil");
        setTitleCol("nil");
        setElements("nil");
    }

    public String getLineNo() {
        return LineNo.get();
    }

    public String getId() {
        return Id.get();
    }

    public SimpleStringProperty idProperty() {
        return Id;
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

    public void setIdCol(String id) {
        this.Id.set(id);
    }

    public void setTitleCol(String titleCol) {
        this.Title.set(titleCol);
    }

    public void setElements(String stateCol) {
        this.Elements.set(stateCol);
    }
}
