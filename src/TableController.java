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
    @FXML private TableView<RMRegister> RMRegView;
    @FXML private TableColumn<RMRegister, String> RMRegisterNameColumn;
    @FXML private TableColumn<RMRegister, String> RMRegisterValueColumn;

    @FXML private TableView<VMRegister> VMRegView;
    @FXML private TableColumn<VMRegister, String> VMRegisterNameColumn;
    @FXML private TableColumn<VMRegister, String> VMRegisterValueColumn;

    @FXML private TableView<RMMemoryBlock> RMMemView;
    @FXML private TableColumn<RMMemoryBlock, String> RMLineNo;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol0;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol1;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol2;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol3;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol4;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol5;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol6;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol7;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol8;
    @FXML private TableColumn<RMMemoryBlock, String> RMCol9;
    @FXML private TableColumn<RMMemoryBlock, String> RMColA;
    @FXML private TableColumn<RMMemoryBlock, String> RMColB;
    @FXML private TableColumn<RMMemoryBlock, String> RMColC;
    @FXML private TableColumn<RMMemoryBlock, String> RMColD;
    @FXML private TableColumn<RMMemoryBlock, String> RMColE;
    @FXML private TableColumn<RMMemoryBlock, String> RMColF;

    @FXML private TableView<VMMemoryBlock> VMMemView;
    @FXML private TableColumn<VMMemoryBlock, String> VMLineNo;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol0;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol1;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol2;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol3;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol4;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol5;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol6;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol7;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol8;
    @FXML private TableColumn<VMMemoryBlock, String> VMCol9;
    @FXML private TableColumn<VMMemoryBlock, String> VMColA;
    @FXML private TableColumn<VMMemoryBlock, String> VMColB;
    @FXML private TableColumn<VMMemoryBlock, String> VMColC;
    @FXML private TableColumn<VMMemoryBlock, String> VMColD;
    @FXML private TableColumn<VMMemoryBlock, String> VMColE;
    @FXML private TableColumn<VMMemoryBlock, String> VMColF;

    @FXML private Button runButton;
    @FXML private Button stepButton;
    @FXML private Button resetButton;
    @FXML private Button loadButton;

    @FXML private Label previousLine;
    @FXML private Label currentLine;
    VM process = new VM(this);
    private int counter = 0;

    @FXML private void runButtonAction(javafx.event.ActionEvent event) {
        previousLine.setText("We Starting!");
        try {
            process.loadProgram();
        }catch(ProgramInterrupt PI) {
            // Overflow
        }
        while(true){
            try{
                process.exec();
                // decrement timer
            }catch (SystemInterrupt SI){
                int intCode = SI.getIntCode();
                if (intCode == 3) break;
            }catch (ProgramInterrupt PI){
                int intCode = PI.getIntCode();
            }
        }
    }

    @FXML private void stepButtonAction(javafx.event.ActionEvent event){
        previousLine.setText("We Started... " + counter);
        try {
            process.exec();
        }catch(SystemInterrupt SI){

        }catch(ProgramInterrupt PI){

        }

        counter++;
        currentLine.setText("We Started... " + counter);
    }

    @FXML private void resetButtonAction(javafx.event.ActionEvent event){
        previousLine.setText("");
        currentLine.setText("");

        resetRMRegister();
        resetVMRegister();
        resetRMMemory();
        resetVMMemory();
    }

    @FXML private void loadButtonAction(javafx.event.ActionEvent event){
        try {
            process.loadProgram();
        }catch(ProgramInterrupt PI) {
            // Overflow
        }
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

        ObservableList<RMRegister> tableValues = FXCollections.observableArrayList();
        tableValues.add(new RMRegister("PTR"));
        tableValues.add(new RMRegister("SP"));
        tableValues.add(new RMRegister("PC"));
        tableValues.add(new RMRegister("PI"));
        tableValues.add(new RMRegister("SI"));
        tableValues.add(new RMRegister("TI"));
        tableValues.add(new RMRegister("MODE"));

        RMRegView.setItems(tableValues);
    }

    private void initializeVMRegTable() {
        VMRegisterNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        VMRegisterValueColumn.setCellValueFactory(new PropertyValueFactory<>("registerValue"));

        ObservableList<VMRegister> tableValues = FXCollections.observableArrayList();
        tableValues.add(new VMRegister("SP"));
        tableValues.add(new VMRegister("PC"));

        VMRegView.setItems(tableValues);
    }

    private void initializeRMMemTable() {
        RMLineNo.setCellValueFactory(new PropertyValueFactory<>("RMLineNo"));
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

        ObservableList<RMMemoryBlock> tableValues = FXCollections.observableArrayList();
        for (int i = 0; i <= 0xFF; ++i){
            String str = Integer.toHexString(i);
            tableValues.add(new RMMemoryBlock(str.toUpperCase()));
        }

        RMMemView.setItems(tableValues);
    }

    private void initializeVMMemTable() {
        VMLineNo.setCellValueFactory(new PropertyValueFactory<>("VMLineNo"));
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

        ObservableList<VMMemoryBlock> tableValues = FXCollections.observableArrayList();
        for (int i = 0; i <= 0xF; ++i){
            String str = Integer.toHexString(i);
            tableValues.add(new VMMemoryBlock(str.toUpperCase()));
        }

        VMMemView.setItems(tableValues);
    }

    private void resetRMRegister() {
        ObservableList<RMRegister> rmRegisters = getRMRegValues();
        for(RMRegister register : rmRegisters) {

        }
    }

    private void resetVMRegister() {
        ObservableList<VMRegister> vmRegisters = getVMRegValues();
        for(VMRegister register : vmRegisters) {

        }
    }

    private void resetRMMemory() {
        ObservableList<RMMemoryBlock> rmMemoryBlocks = getRMMemValues();
        for (RMMemoryBlock memoryBlock : rmMemoryBlocks) {
            memoryBlock.setInitial();
        }
    }

    private void resetVMMemory() {
        ObservableList<VMMemoryBlock> vMMemoryBlocks = getVMMemValues();
        for (VMMemoryBlock memoryBlock : vMMemoryBlocks) {
            memoryBlock.setInitial();
        }
    }

    public ObservableList<RMRegister> getRMRegValues(){
        return RMRegView.getItems();
    }

    public ObservableList<VMRegister> getVMRegValues(){
        return VMRegView.getItems();
    }

    public ObservableList<RMMemoryBlock> getRMMemValues(){
        return RMMemView.getItems();
    }

    public ObservableList<VMMemoryBlock> getVMMemValues(){
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

        RMMemoryBlock rmMemoryBlock = getRMMemValues().get(block);
        return rmMemoryBlock.set(word, value);
    }

    public boolean setVMMemValue(int block, int word, String value) {
        if (block < 0x0 || block > Utils.VM_MEM_BLOCK_COUNT-1 || word < 0x0 || word > Utils.BLOCK_WORD_COUNT-1)
            return false;

        VMMemoryBlock vmMemoryBlock = getVMMemValues().get(block);

        return vmMemoryBlock.set(word, value);
    }
}
