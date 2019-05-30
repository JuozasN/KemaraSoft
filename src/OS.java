import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OS implements Initializable {
    @FXML private TableView<PQRow> processQueueView;
    @FXML private TableColumn<PQRow, String> PQLineNoCol;
    @FXML private TableColumn<PQRow, String> PQIdCol;
    @FXML private TableColumn<PQRow, String> PQTitleCol;
    @FXML private TableColumn<PQRow, String> PQStateCol;
    @FXML private TableColumn<PQRow, String> PQPriorityCol;

    @FXML private TableView<ResRow> resourcesView;
    @FXML private TableColumn<ResRow, String> ResLineNoCol;
    @FXML private TableColumn<ResRow, String> ResIdCol;
    @FXML private TableColumn<ResRow, String> ResTitleCol;
    @FXML private TableColumn<ResRow, String> ResElementsCol;

    @FXML private TextArea processLog;

    @FXML private Button runButton;
    @FXML private Button stepButton;
    @FXML private Button resetButton;
    @FXML private Button loadButton;
    @FXML private Button inputConfirm;

    @FXML private Label previousLine;
    @FXML private Label currentLine;
    @FXML public TextField filename;
    @FXML public TextField inputField;
    @FXML public TextField outputField;

    public static final byte IO_BLOCK_INDEX = 0x40;
    public static final byte PROCESS_STATE_BLOCK_INDEX = 0x41;
    private static final byte PROCESS_STATE_PTR_INDEX = 0;
    private static final byte PROCESS_STATE_SP_INDEX = 1;
    private static final byte PROCESS_STATE_PC_INDEX = 2;

    private final RM realMachine = null;
    private VM process;
    private Block IOBlock = new Block();
    private Block processStateBlock = new Block();
    private String inputText;
    private Scheduler scheduler;
    private Distributor distributor;

    public static final ArrayList<Process> processList = new ArrayList<>();
    public static final ArrayList<Resource> resourceList = new ArrayList<>();
    public static final SystemResources systemResources = new SystemResources();

    @FXML private void runButtonAction(javafx.event.ActionEvent event) {
        scheduler = new Scheduler(this);
        distributor = new Distributor(this);
        StartStop ss = new StartStop(this);
        ss.create(null, (byte)4, "StartStop");
    }

    @FXML private void stepButtonAction(javafx.event.ActionEvent event) {
        try {
            scheduler.runNextReadyProcess().run();
        }catch(ProgramInterrupt pi){}
    }

    @FXML private void resetButtonAction(javafx.event.ActionEvent event){
        previousLine.setText("");
        currentLine.setText("VM and RM have been wiped and reset!");

        process.reset();
        realMachine.reset();
    }

    @FXML private void loadButtonAction(javafx.event.ActionEvent event){

    }

    @FXML private void inputConfirmAction(javafx.event.ActionEvent event){
        if (realMachine.getSI() == 1){
            inputText = inputField.getText();
            this.getInterrupt();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePQTable();
        initializeResTable();
    }

    /**
     * UI INITIALIZATION METHODS
     */

    private void initializePQTable() {
        PQLineNoCol.setCellValueFactory(new PropertyValueFactory<>("LineNo"));
        PQIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PQTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        PQStateCol.setCellValueFactory(new PropertyValueFactory<>("State"));
        PQPriorityCol.setCellValueFactory(new PropertyValueFactory<>("Priority"));

        ObservableList<PQRow> tableValues = FXCollections.observableArrayList();

        processQueueView.setItems(tableValues);
    }

    private void initializeResTable() {
        ResLineNoCol.setCellValueFactory(new PropertyValueFactory<>("LineNo"));
        ResIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ResTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        ResElementsCol.setCellValueFactory(new PropertyValueFactory<>("Elements"));

        ObservableList<ResRow> tableValues = FXCollections.observableArrayList();

        resourcesView.setItems(tableValues);
    }

    /**
     * GETTERS AND SETTERS
     */

    public RM getRealMachine() {
        return realMachine;
    }

    public ObservableList<PQRow> getProcessQueueValues(){
        return processQueueView.getItems();
    }

    public ObservableList<ResRow> getResourcesValues(){
        return resourcesView.getItems();
    }

    public String getProcessLog() {
        return this.processLog.getText();
    }

    /**
     * UI METHODS
     */

    public boolean checkFilenameField(){
        if(filename.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Filename cannot be blank! Please enter a valid filename.", ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                alert.close();
            }
            return false;
        } else {
            return true;
        }
    }

    public String getCommandString(VM vm){
        String currCommand = vm.getMemValue(vm.getPc()).getStringValue();
        switch(currCommand) {
            case "PUSH":
            case "PSHC":
            case "POPM":
            case "JZ":
            case "JP":
            case "JN":
            case "JMP":
            case "TOP":
                return(currCommand + ' ' + vm.getMemValue((short) (vm.getPc() + 1)).getStringValue());
            default:
                return(currCommand);
        }
    }

    /**
     * ADD ITEMS TO PROCESS QUEUE AND RESOURCES TABLES
     */

    // adds a process row at the end of the process queue list
    public void addPQRow(Process process) {
        ObservableList<PQRow> processViewValues = getProcessQueueValues();
        processViewValues.add(new PQRow(processViewValues.size(), process));
    }

    // adds a resource row at the end of the resources list
    public void addResRow(Resource resource) {
        ObservableList<ResRow> resViewValues = getResourcesValues();
        resViewValues.add(new ResRow(resViewValues.size(), resource));
    }

    // adds a process row at the index position of the process queue list
    public void addPQRow(int index, Process process) {
        ObservableList<PQRow> processViewValues = getProcessQueueValues();
        processViewValues.add(index, new PQRow(processViewValues.size(), process));
    }

    // adds a resource row at the index position of the resources list
    public void addResRow(int index, Resource resource) {
        ObservableList<ResRow> resViewValues = getResourcesValues();
        resViewValues.add(index, new ResRow(resViewValues.size(), resource));
    }

    /**
     * REMOVES ITEMS FROM PROCESS QUEUE AND RESOURCES TABLES
     */

    // removes a process row from the end of the process queue list
    public void removePQRow() {
        ObservableList<PQRow> processViewValues = getProcessQueueValues();
        processViewValues.remove(processViewValues.size()-1);
    }

    // removes a resource row from the end of the resources list
    public void removeResRow() {
        ObservableList<ResRow> resViewValues = getResourcesValues();
        resViewValues.remove(resViewValues.size()-1);
    }

    // removes a process row from the index position of the process queue list
    public void removePQRow(int index) {
        ObservableList<PQRow> processViewValues = getProcessQueueValues();
        processViewValues.remove(index);
    }

    // removes a resource row from the index position of the resources list
    public void removeResRow(int index) {
        ObservableList<ResRow> resViewValues = getResourcesValues();
        resViewValues.remove(index);
    }

    // removes a process row from the index position of the process queue list
    public void removePQRow(Process process) {
        ObservableList<PQRow> processViewValues = getProcessQueueValues();
        for(int i = 0; i < processViewValues.size(); ++i) {
            if (processViewValues.get(i).getId().equals(String.valueOf(process.getId()))) {
                processViewValues.remove(i);
                break;
            }
        }
    }

    // removes a resource row from the index position of the resources list
    public void removeResRow(Resource resource) {
        ObservableList<ResRow> resViewValues = getResourcesValues();
        for (int i = 0; i < resViewValues.size(); ++i) {
            if (resViewValues.get(i).getId().equals(String.valueOf(resource.getId()))) {
                resViewValues.remove(i);
                break;
            }
        }
    }

    /**
     * APPEND TO PROCESS LOG
     */

    public void appendProcessLog(String text) {
        processLog.appendText(text + "\n");
    }

    /**
     * RESET PROCESS LOG
     */

    public void resetProcessLog() {
        processLog.setText("");
    }

    /**
     * RANDOM RM MEMORY ASSIGNMENT MECHANISM
     */

    public void assignRMMemoryBlocksForVM() {
        byte pagingBlockIndex = assignRMMemoryBlock();
        Byte[] memBlocks = assignRMMemoryBlocks(Utils.VM_MEM_BLOCK_COUNT);
        realMachine.setPagingTable(pagingBlockIndex, memBlocks);
    }

    public byte assignRMMemoryBlock() {
        byte index = realMachine.getRandUnassignedBlockIndex();
        realMachine.assignBlock(index);
        return index;
    }

    public Byte[] assignRMMemoryBlocks(byte blocks) {
        Byte[] indexes = realMachine.getRandUnassignedBlocks(blocks);
        realMachine.assignBlocks(indexes);
        return indexes;
    }

    /**
     * INPUT/OUTPUT AND PROCESS STATE MEMORY ASSIGNMENT
     */

    // loads process state from registers to kernel memory
    // real machine registers are set to zero after load
    private void loadPSBlockToKernel() {
        movePSRegToKernel(RM.RMRegIndexes.PTR, PROCESS_STATE_PTR_INDEX);
        movePSRegToKernel(RM.RMRegIndexes.SP, PROCESS_STATE_SP_INDEX);
        movePSRegToKernel(RM.RMRegIndexes.PC, PROCESS_STATE_PC_INDEX);
    }

    // loads a register from real machine registers to PS block in kernel memory
    // the real machine register is set to zero after load
    private void movePSRegToKernel(Byte rmRegIndex, Byte psRegIndex) {
        Short regValue = realMachine.getReg(rmRegIndex);
        this.processStateBlock.setWord(psRegIndex, regValue);
        realMachine.setValue((int) regValue, PROCESS_STATE_BLOCK_INDEX, psRegIndex);
        realMachine.setReg(rmRegIndex, (short) 0);
    }

    // loads process state from kernel memory to registers
    // kernel memory PS block is set to zero after load
    private Block loadPSBlockToRM() {
        movePSRegToRM(RM.RMRegIndexes.PTR, PROCESS_STATE_PTR_INDEX);
        movePSRegToRM(RM.RMRegIndexes.SP, PROCESS_STATE_SP_INDEX);
        movePSRegToRM(RM.RMRegIndexes.PC, PROCESS_STATE_PC_INDEX);
        return null;
    }

    // loads a register from PS block in kernel memory to real machine register
    // the kernel memory word is set to zero after load
    private void movePSRegToRM(Byte rmRegIndex, Byte psRegIndex) {
        Short regValue = (short) this.processStateBlock.getWord(psRegIndex).getValue();
        this.processStateBlock.setWord(psRegIndex, 0);
        realMachine.setValue(0, PROCESS_STATE_BLOCK_INDEX, psRegIndex);
        realMachine.setReg(rmRegIndex, regValue);
    }

    // loads a specified block of memory to kernel IO memory block
    private void loadIOBlock(Block block) {
        this.IOBlock.setWords(block.getWords());
        for (byte i = 0; i < Utils.BLOCK_WORD_COUNT; ++i) {
            realMachine.setValue(block.getWord(i).getValue(), IO_BLOCK_INDEX, i);
        }
    }

    private void loadIOBlock(int[] block) {
        if (block == null || block.length > 16) {
            return;
        }

        loadIOBlock(new Block(block));
    }

    // Returns IO memory block and removes its data from kernel memory
    private Block getIOBlock() {
        Block ioBlock = new Block(this.IOBlock);
        loadIOBlock(new Block());
        return ioBlock;
    }

    /**
     * INTERRUPTS
     */

    private void getInterrupt(){
        try {
            loadIOBlock(Block.getBlockFromString(inputText));
//        IOBlock.setWords(inputText);
        }catch(ProgramInterrupt e){
            realMachine.setPI(e.getIntCode());
            programInterrupt(e.getIntCode());
        }
        inputConfirm.setDisable(true);
        inputField.setDisable(true);
    }

    private void putInterrupt(){
        //kopijuojam
    }

    public void haltInterrupt() {
        realMachine.setSI((byte) 3);
        realMachine.setMode(false);
        executeInterrupt();
    }

    public void popAlert(String alertText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, alertText, ButtonType.OK);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            alert.close();
        }
    }

    public void test(){
        if(realMachine.getPI() != 0)
            programInterrupt(realMachine.getPI());
        if(realMachine.getSI() != 0)
            systemInterrupt(realMachine.getSI());
        if(realMachine.getTI() == 0);
        // HANDLE TIMER INTERRUPT
    }

    public void programInterrupt(byte intCode){
        String alertText = "";
        switch(intCode){
            case 1: alertText = "INVALID ADDRESS"; break;  // INVALID ADDRESS
            case 2: alertText = "INVALID OP CODE"; break;  // INVALID OP CODE
            case 3: alertText = "INVALID ASSIGN"; break;  // INVALID ASSIGN
            case 4: alertText = "OVERFLOW"; break;  // OVERFLOW
            default:
                System.err.println("Internal error in programInterrupt()");
                System.exit(0);
        }
        popAlert("Program Interrupt. Reason: " + alertText); System.exit(0);
    }

    public void systemInterrupt(byte intCode){
        switch(intCode){
            case 1: inputConfirm.setDisable(false); inputField.setDisable(false);break;
            case 2: putInterrupt(); break;
            case 3: break; // HALT
            default:
                System.err.println("Internal error in systemInterrupt()");
                System.exit(0);
        }
    }

    private void executeInterrupt() {
        // save process state and reset RM registers
        loadPSBlockToKernel();

        // currentProcess -> null???
        //process.clear();

        int si = realMachine.getSI();
        switch(si) {
            case 1:
                // put
                break;
            case 2:
                // get
                break;
            case 3:
                // haltInterrupt
                // realMachine.resetPTR();
                // process.reset();
                //return;
                break;
        }

//        process.load();
    }

    /** MANAGE PROCESS LISTS **/

    public void removeFromProcessList(Process process) {
        appendProcessLog("OS. Removing " + process.getTitle() + " from Process List.");
        processList.remove(process);
//        switch (process.getState()) {
//            case Process.ProcessState.BLOCKED:
//                blockedProcessList.remove(process);
//                break;
//            case Process.ProcessState.READY:
//                readyProcessList.remove(process);
//                break;
//            case Process.ProcessState.SUSPENDED:
//                suspendedProcessList.remove(process);
//                break;
//            case Process.ProcessState.BLOCKED_SUSPENDED:
//                blockedSuspendedProcessList.remove(process);
//                break;
//            case Process.ProcessState.READY_SUSPENDED:
//                readySuspendedProcessList.remove(process);
//                break;
//        }
    }

    public void addToProcessList(Process process) {
        appendProcessLog("OS. Adding " + process.getTitle() + " to Process List.");
        addPQRow(Utils.addByPriority(processList, process), process);
    }

    public void removeFromResourceList(Resource resource) {
        appendProcessLog("OS. Removing " + resource.getTitle() + " from Resource List.");
        resourceList.remove(resource);

    }

    public void addToResourceList(Resource resource) {
        appendProcessLog("OS. Adding " + resource.getTitle() + " to Resource List.");
        resourceList.add(resource);
        addResRow(resource);
    }
}
