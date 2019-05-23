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

    @FXML private TableView<KMemoryBlock> KMemView;
    @FXML private TableColumn<KMemoryBlock, String> KMLineNo;
    @FXML private TableColumn<KMemoryBlock, String> KMCol0;
    @FXML private TableColumn<KMemoryBlock, String> KMCol1;
    @FXML private TableColumn<KMemoryBlock, String> KMCol2;
    @FXML private TableColumn<KMemoryBlock, String> KMCol3;
    @FXML private TableColumn<KMemoryBlock, String> KMCol4;
    @FXML private TableColumn<KMemoryBlock, String> KMCol5;
    @FXML private TableColumn<KMemoryBlock, String> KMCol6;
    @FXML private TableColumn<KMemoryBlock, String> KMCol7;
    @FXML private TableColumn<KMemoryBlock, String> KMCol8;
    @FXML private TableColumn<KMemoryBlock, String> KMCol9;
    @FXML private TableColumn<KMemoryBlock, String> KMColA;
    @FXML private TableColumn<KMemoryBlock, String> KMColB;
    @FXML private TableColumn<KMemoryBlock, String> KMColC;
    @FXML private TableColumn<KMemoryBlock, String> KMColD;
    @FXML private TableColumn<KMemoryBlock, String> KMColE;
    @FXML private TableColumn<KMemoryBlock, String> KMColF;

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

    public static final ArrayList<Process> blockedProcessList = new ArrayList<>();
    public static final ArrayList<Process> readyProcessList = new ArrayList<>();
    public static final ArrayList<Process> suspendedProcessList = new ArrayList<>();
    public static final ArrayList<Process> blockedSuspendedProcessList = new ArrayList<>();
    public static final ArrayList<Process> readySuspendedProcessList = new ArrayList<>();

    public static final ArrayList<Resource> mainResourceList = new ArrayList<>();

    private final RM realMachine = new RM(this);
    private VM process;
    private Block IOBlock = new Block();
    private Block processStateBlock = new Block();
    private String inputText;

    @FXML private void runButtonAction(javafx.event.ActionEvent event) {
        previousLine.setText("We Starting!");
        try {
            process.loadProgram();
        } catch(ProgramInterrupt PI) {
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
        previousLine.setText(currentLine.getText());
        try {
            process.exec();
            realMachine.decrementTI();
        } catch(SystemInterrupt SI){
            realMachine.setSI(SI.getIntCode());
            realMachine.toggleMode();
        } catch(ProgramInterrupt PI){
            realMachine.setPI(PI.getIntCode());
            realMachine.toggleMode();
        } finally {
            test();
            realMachine.toggleMode();
        }
        currentLine.setText(getCommandString(process));
    }

    @FXML private void resetButtonAction(javafx.event.ActionEvent event){
        previousLine.setText("");
        currentLine.setText("VM and RM have been wiped and reset!");

        process.reset();
        realMachine.reset();
//        resetRMRegister();
//        resetVMRegister();
//        resetRMMemory();
//        resetVMMemory();
    }

    @FXML private void loadButtonAction(javafx.event.ActionEvent event){
        if(checkFilenameField()) {
            try {
                process= new VM(this);
                process.loadProgram();
            } catch (ProgramInterrupt PI) {
                // Overflow
            }
            previousLine.setText(currentLine.getText());
            currentLine.setText(getCommandString(process));
        }

    }

    @FXML private void inputConfirmAction(javafx.event.ActionEvent event){
        if (realMachine.getSI() == 1){
            inputText = inputField.getText();
            this.getInterrupt();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeRMRegTable();
        initializeVMRegTable();
        initializeRMMemTable();
        initializeVMMemTable();
        initializeKMemTable();
    }

    /**
     * UI INITIALIZATION METHODS
     */

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
        for (int i = 0; i < Utils.UM_BLOCK_COUNT + 2; ++i){
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
        for (int i = 0; i < Utils.VM_MEM_BLOCK_COUNT; ++i){
            String str = Integer.toHexString(i);
            tableValues.add(new VMMemoryBlock(str.toUpperCase()));
        }

        VMMemView.setItems(tableValues);
    }

    private void initializeKMemTable() {
        KMLineNo.setCellValueFactory(new PropertyValueFactory<>("KMLineNo"));
        KMCol0.setCellValueFactory(new PropertyValueFactory<>("KMCol0"));
        KMCol1.setCellValueFactory(new PropertyValueFactory<>("KMCol1"));
        KMCol2.setCellValueFactory(new PropertyValueFactory<>("KMCol2"));
        KMCol3.setCellValueFactory(new PropertyValueFactory<>("KMCol3"));
        KMCol4.setCellValueFactory(new PropertyValueFactory<>("KMCol4"));
        KMCol5.setCellValueFactory(new PropertyValueFactory<>("KMCol5"));
        KMCol6.setCellValueFactory(new PropertyValueFactory<>("KMCol6"));
        KMCol7.setCellValueFactory(new PropertyValueFactory<>("KMCol7"));
        KMCol8.setCellValueFactory(new PropertyValueFactory<>("KMCol8"));
        KMCol9.setCellValueFactory(new PropertyValueFactory<>("KMCol9"));
        KMColA.setCellValueFactory(new PropertyValueFactory<>("KMColA"));
        KMColB.setCellValueFactory(new PropertyValueFactory<>("KMColB"));
        KMColC.setCellValueFactory(new PropertyValueFactory<>("KMColC"));
        KMColD.setCellValueFactory(new PropertyValueFactory<>("KMColD"));
        KMColE.setCellValueFactory(new PropertyValueFactory<>("KMColE"));
        KMColF.setCellValueFactory(new PropertyValueFactory<>("KMColF"));

        ObservableList<KMemoryBlock> tableValues = FXCollections.observableArrayList();
        for (int i = 0; i < Utils.KM_BLOCK_COUNT; ++i){
            String str = Integer.toHexString(i);
            tableValues.add(new KMemoryBlock(str.toUpperCase()));
        }

        KMemView.setItems(tableValues);
    }

    /**
     * GETTERS AND SETTERS
     */
    public RM getRealMachine() {
        return realMachine;
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

    public ObservableList<KMemoryBlock> getKMemValues(){
        return KMemView.getItems();
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
        String currCommand = vm.getValue(vm.getPc()).getStringValue();
        switch(currCommand) {
            case "PUSH":
            case "PSHC":
            case "POPM":
            case "JZ":
            case "JP":
            case "JN":
            case "JMP":
            case "TOP":
                return(currCommand + ' ' + vm.getValue((short) (vm.getPc() + 1)).getStringValue());
            default:
                return(currCommand);
        }
    }

    /**
     * SET REAL MACHINE AND VIRTUAL MACHINE REGISTERS
     */

    // registerIndex:
    // [0] -> PTR
    // [1] -> SP
    // [2] -> PC
    // [3] -> PI
    // [4] -> SI
    // [5] -> TI
    // [6] -> MODE
    public boolean setRMRegValue(byte registerIndex, String value) {
        if (registerIndex < 0 || registerIndex > 6)
            return false;

        if (value.equals("0000") || value.equals("0")) {
            realMachine.setReg(registerIndex, (short) 0);
        } else {
            realMachine.setReg(registerIndex, Utils.byteArrayToShort(value.getBytes()));
        }

        getRMRegValues().get(registerIndex).setRegisterValue(value);
        return true;
    }

    public boolean setRMRegValue(byte registerIndex, Short value) {
        return setRMRegValue(registerIndex, Utils.shortToHexString(value));
    }

    private boolean setRMRegValuePaging(byte vmRegInd, String value) {
        byte rmRegIndex = Paging.getRMRegIndex(vmRegInd);
        if (rmRegIndex == -1)
            return false;

        return setRMRegValue(rmRegIndex, value);
    }

    // registerIndex:
    // [0] -> SP
    // [1] -> PC
    public boolean setVMRegValue(byte registerIndex, String value) {
        if (registerIndex < 0 || registerIndex > 1)
            return false;

        getVMRegValues().get(registerIndex).setRegisterValue(value);
        return true;
//        return setRMRegValuePaging(registerIndex, value);
    }

    public boolean setVMRegValue(byte registerIndex, Short value) {
        return setVMRegValue(registerIndex, Utils.shortToHexString(value));
//        return setRMRegValuePaging(registerIndex, value);
    }

    /**
     * SET REAL MACHINE, VIRTUAL MACHINE AND KERNEL MEMORY
     */

    // block - memory block number (hex)
    // word - block word number (hex)
    public boolean setRMMemValue(byte block, byte word, String value) {
        if (block < 0x0 || block > Utils.UM_BLOCK_COUNT-1 + 2 || word < 0x0 || word > Utils.BLOCK_WORD_COUNT-1)
            return false;

        RMMemoryBlock rmMemoryBlock = getRMMemValues().get(block);
        return rmMemoryBlock.set(word, value);
    }

    public boolean setRMMemValue(byte block, byte word, Short value) {
        if (block < 0x0 || block > Utils.UM_BLOCK_COUNT-1 + 2 || word < 0x0 || word > Utils.BLOCK_WORD_COUNT-1)
            return false;

        RMMemoryBlock rmMemoryBlock = getRMMemValues().get(block);
        return rmMemoryBlock.set(word, Utils.shortToHexString(value));
    }

    // uses paging mechanism
    public boolean setRMMemValuePaging(byte vmBlock, byte vmWord, String value) {
        if (vmBlock < 0x0 || vmBlock > Utils.UM_BLOCK_COUNT-1 || vmWord < 0x0 || vmWord > Utils.BLOCK_WORD_COUNT-1)
            return false;

        Short adr = Paging.getUMAdr(vmBlock, vmWord);
        if (adr == null) {
            System.err.println("ERROR converting VM memory address to RM memory address in method setRMMemValuePaging()");
            return false;
        }

        byte rmBlock = (byte) (adr / Utils.BLOCK_WORD_COUNT);
        byte rmWord = (byte) (adr % Utils.BLOCK_WORD_COUNT);
        return setRMMemValue(rmBlock, rmWord, value);
    }

    public boolean setVMMemValue(byte block, byte word, String value) {
        if (block < 0x0 || block > Utils.VM_MEM_BLOCK_COUNT-1 || word < 0x0 || word > Utils.BLOCK_WORD_COUNT-1)
            return false;

        VMMemoryBlock vmMemoryBlock = getVMMemValues().get(block);

        return vmMemoryBlock.set(word, value);
    }

    public boolean setKMemValue(byte block, byte word, String value) {
        if (block < 0x0 || block > Utils.KM_BLOCK_COUNT-1 || word < 0x0 || word > Utils.BLOCK_WORD_COUNT-1)
            return false;

        KMemoryBlock kMemoryBlock = getKMemValues().get(block);

        return kMemoryBlock.set(word, value);
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
        setRMMemValue(PROCESS_STATE_BLOCK_INDEX, psRegIndex, regValue);
        setRMRegValue(rmRegIndex, (short) 0);
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
        setRMMemValue(PROCESS_STATE_BLOCK_INDEX, psRegIndex, (short) 0);
        setRMRegValue(rmRegIndex, regValue);
    }

    // loads a specified block of memory to kernel IO memory block
    private void loadIOBlock(Block block) {
        this.IOBlock.setWords(block.getWords());
        for (byte i = 0; i < Utils.BLOCK_WORD_COUNT; ++i) {
            setRMMemValue(IO_BLOCK_INDEX, i, block.getWord(i).getStringValue());
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
        setRMRegValue(RM.RMRegIndexes.SI, Utils.shortToHexString((short) 3));
        setRMRegValue(RM.RMRegIndexes.MODE, Utils.shortToHexString((short) 1)); // 1 -> kernel mode
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
        process.clear();

        String reg = getRMRegValues().get(RM.RMRegIndexes.SI).getRegisterValue();
        int si = Integer.parseInt(reg);

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

        process.load();
    }

    /** MANAGE PROCESS LISTS **/

    public static void removeFromProcessList(Process process) {
        switch (process.getState()) {
            case Process.ProcessState.BLOCKED:
                blockedProcessList.remove(process);
                break;
            case Process.ProcessState.READY:
                readyProcessList.remove(process);
                break;
            case Process.ProcessState.SUSPENDED:
                suspendedProcessList.remove(process);
                break;
            case Process.ProcessState.BLOCKED_SUSPENDED:
                blockedSuspendedProcessList.remove(process);
                break;
            case Process.ProcessState.READY_SUSPENDED:
                readySuspendedProcessList.remove(process);
                break;
        }
    }

    public static void addToProcessList(Process process, byte state) {
        switch(state) {
            case Process.ProcessState.BLOCKED:
                blockedProcessList.add(process);
                break;
            case Process.ProcessState.READY:
                readyProcessList.add(process);
                break;
            case Process.ProcessState.SUSPENDED:
                suspendedProcessList.add(process);
                break;
            case Process.ProcessState.BLOCKED_SUSPENDED:
                blockedSuspendedProcessList.add(process);
                break;
            case Process.ProcessState.READY_SUSPENDED:
                readySuspendedProcessList.add(process);
                break;
        }
    }

    public void runProcess(Process process) {
        // run process...
    }
}
