import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.jshell.execution.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    @FXML private TableView<RMStatus> RMRegView;
    @FXML private TableColumn<RMStatus, String> RMRegisterNameColumn;
    @FXML private TableColumn<RMStatus, String> RMRegisterValueColumn;

    @FXML private TableView<VMStatus> VMRegView;
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
    private int counter = 0;

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
        initializeRMRegTable();
        initializeVMRegTable();
        initializeRMMemTable();
        initializeVMMemTable();
    }

    private void initializeRMRegTable() {
        RMRegisterNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        RMRegisterValueColumn.setCellValueFactory(new PropertyValueFactory<>("registerValue"));

        ObservableList<RMStatus> tableValues = FXCollections.observableArrayList();
        tableValues.add(new RMStatus("PTR", "PTRVal"));
        tableValues.add(new RMStatus("SP", "SPVal"));
        tableValues.add(new RMStatus("PC", "PCVal"));
        tableValues.add(new RMStatus("PI", "PIVal"));
        tableValues.add(new RMStatus("SI", "SIVal"));
        tableValues.add(new RMStatus("TI", "TIVal"));
        tableValues.add(new RMStatus("MODE", "ModeVal"));

        RMRegView.setItems(tableValues);
    }

    private void initializeVMRegTable() {
        VMRegisterNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        VMRegisterValueColumn.setCellValueFactory(new PropertyValueFactory<>("registerValue"));

        ObservableList<VMStatus> tableValues = FXCollections.observableArrayList();
        tableValues.add(new VMStatus("SP", "SPVal"));
        tableValues.add(new VMStatus("PC", "PCVal"));

        VMRegView.setItems(tableValues);
    }

    private void initializeRMMemTable() {
        RMLineNo.setCellValueFactory(new PropertyValueFactory<>("LineNo"));
        RMCol0.setCellValueFactory(new PropertyValueFactory<>("RMCol0"));
        RMCol1.setCellValueFactory(new PropertyValueFactory<>("RMCol1"));
        RMCol2.setCellValueFactory(new PropertyValueFactory<>("RMCol2"));
        RMCol3.setCellValueFactory(new PropertyValueFactory<>("RMCol3"));
        RMCol4.setCellValueFactory(new PropertyValueFactory<>("RMCol4"));
        RMCol5.setCellValueFactory(new PropertyValueFactory<>("RMCol5"));
        RMCol6.setCellValueFactory(new PropertyValueFactory<>("RMCol6"));
        RMCol7.setCellValueFactory(new PropertyValueFactory<>("RMCol7"));
        RMCol8.setCellValueFactory(new PropertyValueFactory<>("RMCol8"));
        RMCol9.setCellValueFactory(new PropertyValueFactory<>("RMCol9"));
        RMColA.setCellValueFactory(new PropertyValueFactory<>("RMColA"));
        RMColB.setCellValueFactory(new PropertyValueFactory<>("RMColB"));
        RMColC.setCellValueFactory(new PropertyValueFactory<>("RMColC"));
        RMColD.setCellValueFactory(new PropertyValueFactory<>("RMColD"));
        RMColE.setCellValueFactory(new PropertyValueFactory<>("RMColE"));
        RMColF.setCellValueFactory(new PropertyValueFactory<>("RMColF"));

        ObservableList<RMMemory> tableValues = FXCollections.observableArrayList();
        for (int i = 0; i <= 0xFF; ++i){
            String str = Integer.toHexString(i);
            tableValues.add(new RMMemory(str.toUpperCase(), "0000", "0000", "0000", "0000", "0000",
                    "0000", "0000", "0000", "0000", "0000", "0000", "0000",
                    "0000", "0000", "0000", "0000"));
        }

        RMMemView.setItems(tableValues);
    }

    private void initializeVMMemTable() {
        VMLineNo.setCellValueFactory(new PropertyValueFactory<>("LineNo"));
        VMCol0.setCellValueFactory(new PropertyValueFactory<>("VMCol0"));
        VMCol1.setCellValueFactory(new PropertyValueFactory<>("VMCol1"));
        VMCol2.setCellValueFactory(new PropertyValueFactory<>("VMCol2"));
        VMCol3.setCellValueFactory(new PropertyValueFactory<>("VMCol3"));
        VMCol4.setCellValueFactory(new PropertyValueFactory<>("VMCol4"));
        VMCol5.setCellValueFactory(new PropertyValueFactory<>("VMCol5"));
        VMCol6.setCellValueFactory(new PropertyValueFactory<>("VMCol6"));
        VMCol7.setCellValueFactory(new PropertyValueFactory<>("VMCol7"));
        VMCol8.setCellValueFactory(new PropertyValueFactory<>("VMCol8"));
        VMCol9.setCellValueFactory(new PropertyValueFactory<>("VMCol9"));
        VMColA.setCellValueFactory(new PropertyValueFactory<>("VMColA"));
        VMColB.setCellValueFactory(new PropertyValueFactory<>("VMColB"));
        VMColC.setCellValueFactory(new PropertyValueFactory<>("VMColC"));
        VMColD.setCellValueFactory(new PropertyValueFactory<>("VMColD"));
        VMColE.setCellValueFactory(new PropertyValueFactory<>("VMColE"));
        VMColF.setCellValueFactory(new PropertyValueFactory<>("VMColF"));

        ObservableList<VMMemory> tableValues = FXCollections.observableArrayList();
        for (int i = 0; i <= 0xF; ++i){
            String str = Integer.toHexString(i);
            tableValues.add(new VMMemory(str.toUpperCase(), "0000", "0000", "0000", "0000", "0000",
                    "0000", "0000", "0000", "0000", "0000", "0000", "0000",
                    "0000", "0000", "0000", "0000"));
        }

        VMMemView.setItems(tableValues);
    }


    public ObservableList<RMStatus> getRMRegValues(){
        return RMRegView.getItems();
    }

    public ObservableList<VMStatus> getVMRegValues(){
        return VMRegView.getItems();
    }

    public ObservableList<RMMemory> getRMMemValues(){
        return RMMemView.getItems();
    }

    public ObservableList<VMMemory> getVMMemValues(){
        return VMMemView.getItems();
    }

    // registerIndex:
    // [0] -> PTR
    // [1] -> SP
    // [2] -> PC
    // [3] -> PI
    // [4] -> SI
    // [5] -> TI
    // [6] -> MODE
    public boolean setRMRegValue(int registerIndex, String value) {
        if (registerIndex < 0 || registerIndex > 6)
            return false;

        getRMRegValues().get(registerIndex).setRegisterValue(value);
        return true;
    }

    // registerIndex:
    // [0] -> SP
    // [1] -> PC
    public boolean setVMRegValue(int registerIndex, String value) {
        if (registerIndex < 0 || registerIndex > 1)
            return false;

        getVMRegValues().get(registerIndex).setRegisterValue(value);
        return true;
    }

    // block - memory block number (hex)
    // word - block word number (hex)
    public boolean setRMMemValue(int block, int word, String value) {
        if (block < 0x0 || block > Utils.UM_BLOCK_COUNT-1 || word < 0x0 || word > Utils.BLOCK_WORD_COUNT-1)
            return false;

        RMMemory rmMemory = getRMMemValues().get(block);
        switch(word) {
            case 0:
                rmMemory.setRMCol0(value);
                break;
            case 1:
                rmMemory.setRMCol1(value);
                break;
            case 2:
                rmMemory.setRMCol2(value);
                break;
            case 3:
                rmMemory.setRMCol3(value);
                break;
            case 4:
                rmMemory.setRMCol4(value);
                break;
            case 5:
                rmMemory.setRMCol5(value);
                break;
            case 6:
                rmMemory.setRMCol6(value);
                break;
            case 7:
                rmMemory.setRMCol7(value);
                break;
            case 8:
                rmMemory.setRMCol8(value);
                break;
            case 9:
                rmMemory.setRMCol9(value);
                break;
            case 0xA:
                rmMemory.setRMColA(value);
                break;
            case 0xB:
                rmMemory.setRMColB(value);
                break;
            case 0xC:
                rmMemory.setRMColC(value);
                break;
            case 0xD:
                rmMemory.setRMColD(value);
                break;
            case 0xE:
                rmMemory.setRMColE(value);
                break;
            case 0xF:
                rmMemory.setRMColF(value);
                break;
        }
        return true;
    }

    public boolean setVMMemValue(int block, int word, String value) {
        if (block < 0x0 || block > Utils.VM_MEM_BLOCK_COUNT-1 || word < 0x0 || word > Utils.BLOCK_WORD_COUNT-1)
            return false;

        VMMemory vmMemory = getVMMemValues().get(block);
        switch(word) {
            case 0:
                vmMemory.setVMCol0(value);
                break;
            case 1:
                vmMemory.setVMCol1(value);
                break;
            case 2:
                vmMemory.setVMCol2(value);
                break;
            case 3:
                vmMemory.setVMCol3(value);
                break;
            case 4:
                vmMemory.setVMCol4(value);
                break;
            case 5:
                vmMemory.setVMCol5(value);
                break;
            case 6:
                vmMemory.setVMCol6(value);
                break;
            case 7:
                vmMemory.setVMCol7(value);
                break;
            case 8:
                vmMemory.setVMCol8(value);
                break;
            case 9:
                vmMemory.setVMCol9(value);
                break;
            case 0xA:
                vmMemory.setVMColA(value);
                break;
            case 0xB:
                vmMemory.setVMColB(value);
                break;
            case 0xC:
                vmMemory.setVMColC(value);
                break;
            case 0xD:
                vmMemory.setVMColD(value);
                break;
            case 0xE:
                vmMemory.setVMColE(value);
                break;
            case 0xF:
                vmMemory.setVMColF(value);
                break;
        }
        return true;
    }
}
