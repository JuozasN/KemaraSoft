import javafx.beans.property.SimpleStringProperty;

public class VMRegister {
    public static String INITIAL_REG_VAL_STR = "0000";
    private SimpleStringProperty registerName;
    private SimpleStringProperty registerValue;

    public VMRegister(String registerName, String registerValue){
        this.registerName = new SimpleStringProperty(registerName);
        this.registerValue = new SimpleStringProperty(registerValue);
    }

    public VMRegister(String registerName) {
        this(registerName, INITIAL_REG_VAL_STR);
    }

    public void setInitial() {
        setRegisterValue(INITIAL_REG_VAL_STR);
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
