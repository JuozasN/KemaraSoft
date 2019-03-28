package OSProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    @FXML private TableView<RMStatus> RMView;
    @FXML private TableColumn<RMStatus, String> RMRegisterNameColumn;
    @FXML private TableColumn<RMStatus, String> RMRegisterValueColumn;

    @FXML private TableView<VMStatus> VMView;
    @FXML private TableColumn<VMStatus, String> VMRegisterNameColumn;
    @FXML private TableColumn<VMStatus, String> VMRegisterValueColumn;

    @FXML private TableView<RMMemory> RMMemView;
    @FXML private TableColumn<RMMemory, String> RMLineNo;
    @FXML private TableColumn<RMMemory, String> RMCol0;
    @FXML private TableColumn<RMMemory, String> RMCol1;
    @FXML private TableColumn<RMMemory, String> RMCol2;
    @FXML private TableColumn<RMMemory, String> RMCol3;
    @FXML private TableColumn<RMMemory, String> RMCol4;
    @FXML private TableColumn<RMMemory, String> RMCol5;
    @FXML private TableColumn<RMMemory, String> RMCol6;
    @FXML private TableColumn<RMMemory, String> RMCol7;
    @FXML private TableColumn<RMMemory, String> RMCol8;
    @FXML private TableColumn<RMMemory, String> RMCol9;
    @FXML private TableColumn<RMMemory, String> RMColA;
    @FXML private TableColumn<RMMemory, String> RMColB;
    @FXML private TableColumn<RMMemory, String> RMColC;
    @FXML private TableColumn<RMMemory, String> RMColD;
    @FXML private TableColumn<RMMemory, String> RMColE;
    @FXML private TableColumn<RMMemory, String> RMColF;

    @FXML private TableView<VMMemory> VMMemView;
    @FXML private TableColumn<VMMemory, String> VMLineNo;
    @FXML private TableColumn<VMMemory, String> VMCol0;
    @FXML private TableColumn<VMMemory, String> VMCol1;
    @FXML private TableColumn<VMMemory, String> VMCol2;
    @FXML private TableColumn<VMMemory, String> VMCol3;
    @FXML private TableColumn<VMMemory, String> VMCol4;
    @FXML private TableColumn<VMMemory, String> VMCol5;
    @FXML private TableColumn<VMMemory, String> VMCol6;
    @FXML private TableColumn<VMMemory, String> VMCol7;
    @FXML private TableColumn<VMMemory, String> VMCol8;
    @FXML private TableColumn<VMMemory, String> VMCol9;
    @FXML private TableColumn<VMMemory, String> VMColA;
    @FXML private TableColumn<VMMemory, String> VMColB;
    @FXML private TableColumn<VMMemory, String> VMColC;
    @FXML private TableColumn<VMMemory, String> VMColD;
    @FXML private TableColumn<VMMemory, String> VMColE;
    @FXML private TableColumn<VMMemory, String> VMColF;

    @FXML private Button runButton;
    @FXML private Button stepButton;
    @FXML private Button resetButton;
    @FXML private Button loadButton;

    @FXML private Label previousLine;
    @FXML private Label currentLine;
    int counter = 0;

    @FXML private void runButtonAction(javafx.event.ActionEvent event){
        previousLine.setText("We Starting!");
    }

    @FXML private void stepButtonAction(javafx.event.ActionEvent event){
        previousLine.setText("We Started... " + counter);
        counter++;
        currentLine.setText("We Started... " + counter);
    }

    @FXML private void resetButtonAction(javafx.event.ActionEvent event){
        previousLine.setText("");
        currentLine.setText("");
    }

    @FXML private void loadButtonAction(javafx.event.ActionEvent event){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RMRegisterNameColumn.setCellValueFactory(new PropertyValueFactory<RMStatus, String>("registerName"));
        RMRegisterValueColumn.setCellValueFactory(new PropertyValueFactory<RMStatus, String>("registerValue"));

        RMView.setItems(getRMRegValues());

        VMRegisterNameColumn.setCellValueFactory(new PropertyValueFactory<VMStatus, String>("registerName"));
        VMRegisterValueColumn.setCellValueFactory(new PropertyValueFactory<VMStatus, String>("registerValue"));

        VMView.setItems(getVMRegValues());

        RMLineNo.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("LineNo"));
        RMCol0.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol0"));
        RMCol1.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol1"));
        RMCol2.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol2"));
        RMCol3.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol3"));
        RMCol4.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol4"));
        RMCol5.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol5"));
        RMCol6.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol6"));
        RMCol7.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol7"));
        RMCol8.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol8"));
        RMCol9.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMCol9"));
        RMColA.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMColA"));
        RMColB.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMColB"));
        RMColC.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMColC"));
        RMColD.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMColD"));
        RMColE.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMColE"));
        RMColF.setCellValueFactory(new PropertyValueFactory<RMMemory, String>("RMColF"));

        RMMemView.setItems(getRMMemValues());

        VMLineNo.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("LineNo"));
        VMCol0.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol0"));
        VMCol1.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol1"));
        VMCol2.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol2"));
        VMCol3.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol3"));
        VMCol4.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol4"));
        VMCol5.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol5"));
        VMCol6.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol6"));
        VMCol7.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol7"));
        VMCol8.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol8"));
        VMCol9.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMCol9"));
        VMColA.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMColA"));
        VMColB.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMColB"));
        VMColC.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMColC"));
        VMColD.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMColD"));
        VMColE.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMColE"));
        VMColF.setCellValueFactory(new PropertyValueFactory<VMMemory, String>("VMColF"));

        VMMemView.setItems(getVMMemValues());

    }

    public ObservableList<RMStatus> getRMRegValues(){
        ObservableList<RMStatus> regValues = FXCollections.observableArrayList();
        regValues.add(new RMStatus("PTR", "PTRVal"));
        regValues.add(new RMStatus("SP", "SPVal"));
        regValues.add(new RMStatus("PC", "PCVal"));
        regValues.add(new RMStatus("PI", "PIVal"));
        regValues.add(new RMStatus("SI", "SIVal"));
        regValues.add(new RMStatus("TI", "TIVal"));
        regValues.add(new RMStatus("MODE", "ModeVal"));

        return regValues;
    }

    public ObservableList<VMStatus> getVMRegValues(){
        ObservableList<VMStatus> regValues = FXCollections.observableArrayList();
        regValues.add(new VMStatus("SP", "SPVal"));
        regValues.add(new VMStatus("PC", "PCVal"));

        return regValues;
    }

    public ObservableList<RMMemory> getRMMemValues(){
        ObservableList<RMMemory> regValues = FXCollections.observableArrayList();
        for (int i = 0; i <= 0xFF; ++i){
            String str = new String(Integer.toHexString(i));
            regValues.add(new RMMemory(str.toUpperCase(), "0000", "0000", "0000", "0000", "0000",
            "0000", "0000", "0000", "0000", "0000", "0000", "0000",
                    "0000", "0000", "0000", "0000"));
        }

        return regValues;
    }

    public ObservableList<VMMemory> getVMMemValues(){
        ObservableList<VMMemory> regValues = FXCollections.observableArrayList();
        for (int i = 0; i <= 0xF; ++i){
            String str = new String(Integer.toHexString(i));
            regValues.add(new VMMemory(str.toUpperCase(), "0000", "0000", "0000", "0000", "0000",
                    "0000", "0000", "0000", "0000", "0000", "0000", "0000",
                    "0000", "0000", "0000", "0000"));
        }

        return regValues;
    }
}
