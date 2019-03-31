import javafx.beans.property.SimpleStringProperty;

public class RMStatus {
    private SimpleStringProperty registerName;
    private SimpleStringProperty registerValue;

    public RMStatus(String registerName, String registerValue){
        this.registerName = new SimpleStringProperty(registerName);
        this.registerValue = new SimpleStringProperty(registerValue);
    }

    public String getRegisterName() {
        return registerName.get();
    }

    public SimpleStringProperty registerNameProperty() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName.set(registerName);
    }

    public String getRegisterValue() {
        return registerValue.get();
    }

    public SimpleStringProperty registerValueProperty() {
        return registerValue;
    }

    public void setRegisterValue(String registerValue) {
        this.registerValue.set(registerValue);
    }
}
