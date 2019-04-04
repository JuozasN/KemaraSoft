import javafx.beans.property.SimpleStringProperty;

public class VMRegister {
    private SimpleStringProperty registerName;
    private SimpleStringProperty registerValue;

    public VMRegister(String registerName, String registerValue){
        this.registerName = new SimpleStringProperty(registerName);
        this.registerValue = new SimpleStringProperty(registerValue);
    }

    public VMRegister(String registerName) {
        this(registerName, "0");
    }

    public void setInitial() {
        setRegisterValue("0");
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
