import javafx.beans.property.SimpleStringProperty;

public class VMMemory {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty VMCol0;
    private SimpleStringProperty VMCol1;
    private SimpleStringProperty VMCol2;
    private SimpleStringProperty VMCol3;
    private SimpleStringProperty VMCol4;
    private SimpleStringProperty VMCol5;
    private SimpleStringProperty VMCol6;
    private SimpleStringProperty VMCol7;
    private SimpleStringProperty VMCol8;
    private SimpleStringProperty VMCol9;
    private SimpleStringProperty VMColA;
    private SimpleStringProperty VMColB;
    private SimpleStringProperty VMColC;
    private SimpleStringProperty VMColD;
    private SimpleStringProperty VMColE;
    private SimpleStringProperty VMColF;


    public VMMemory(String LineNo, String VMCol0, String VMCol1, String VMCol2, String VMCol3, String VMCol4, String VMCol5
            , String VMCol6, String VMCol7, String VMCol8, String VMCol9, String VMColA, String VMColB, String VMColC
            , String VMColD, String VMColE, String VMColF){

        this.LineNo = new SimpleStringProperty(LineNo);
        this.VMCol0 = new SimpleStringProperty(VMCol0);
        this.VMCol1 = new SimpleStringProperty(VMCol1);
        this.VMCol2 = new SimpleStringProperty(VMCol2);
        this.VMCol3 = new SimpleStringProperty(VMCol3);
        this.VMCol4 = new SimpleStringProperty(VMCol4);
        this.VMCol5 = new SimpleStringProperty(VMCol5);
        this.VMCol6 = new SimpleStringProperty(VMCol6);
        this.VMCol7 = new SimpleStringProperty(VMCol7);
        this.VMCol8 = new SimpleStringProperty(VMCol8);
        this.VMCol9 = new SimpleStringProperty(VMCol9);
        this.VMColA = new SimpleStringProperty(VMColA);
        this.VMColB = new SimpleStringProperty(VMColB);
        this.VMColC = new SimpleStringProperty(VMColC);
        this.VMColD = new SimpleStringProperty(VMColD);
        this.VMColE = new SimpleStringProperty(VMColE);
        this.VMColF = new SimpleStringProperty(VMColF);

    }

    public String getLineNo() {
        return LineNo.get();
    }

    public SimpleStringProperty lineNoProperty() {
        return LineNo;
    }

    public void setLineNo(String lineNo) {
        this.LineNo.set(lineNo);
    }

    public String getVMCol0() {
        return VMCol0.get();
    }

    public SimpleStringProperty VMCol0Property() {
        return VMCol0;
    }

    public void setVMCol0(String VMCol0) {
        this.VMCol0.set(VMCol0);
    }

    public String getVMCol1() {
        return VMCol1.get();
    }

    public SimpleStringProperty VMCol1Property() {
        return VMCol1;
    }

    public void setVMCol1(String VMCol1) {
        this.VMCol1.set(VMCol1);
    }

    public String getVMCol2() {
        return VMCol2.get();
    }

    public SimpleStringProperty VMCol2Property() {
        return VMCol2;
    }

    public void setVMCol2(String VMCol2) {
        this.VMCol2.set(VMCol2);
    }

    public String getVMCol3() {
        return VMCol3.get();
    }

    public SimpleStringProperty VMCol3Property() {
        return VMCol3;
    }

    public void setVMCol3(String VMCol3) {
        this.VMCol3.set(VMCol3);
    }

    public String getVMCol4() {
        return VMCol4.get();
    }

    public SimpleStringProperty VMCol4Property() {
        return VMCol4;
    }

    public void setVMCol4(String VMCol4) {
        this.VMCol4.set(VMCol4);
    }

    public String getVMCol5() {
        return VMCol5.get();
    }

    public SimpleStringProperty VMCol5Property() {
        return VMCol5;
    }

    public void setVMCol5(String VMCol5) {
        this.VMCol5.set(VMCol5);
    }

    public String getVMCol6() {
        return VMCol6.get();
    }

    public SimpleStringProperty VMCol6Property() {
        return VMCol6;
    }

    public void setVMCol6(String VMCol6) {
        this.VMCol6.set(VMCol6);
    }

    public String getVMCol7() {
        return VMCol7.get();
    }

    public SimpleStringProperty VMCol7Property() {
        return VMCol7;
    }

    public void setVMCol7(String VMCol7) {
        this.VMCol7.set(VMCol7);
    }

    public String getVMCol8() {
        return VMCol8.get();
    }

    public SimpleStringProperty VMCol8Property() {
        return VMCol8;
    }

    public void setVMCol8(String VMCol8) {
        this.VMCol8.set(VMCol8);
    }

    public String getVMCol9() {
        return VMCol9.get();
    }

    public SimpleStringProperty VMCol9Property() {
        return VMCol9;
    }

    public void setVMCol9(String VMCol9) {
        this.VMCol9.set(VMCol9);
    }

    public String getVMColA() {
        return VMColA.get();
    }

    public SimpleStringProperty VMColAProperty() {
        return VMColA;
    }

    public void setVMColA(String VMColA) {
        this.VMColA.set(VMColA);
    }

    public String getVMColB() {
        return VMColB.get();
    }

    public SimpleStringProperty VMColBProperty() {
        return VMColB;
    }

    public void setVMColB(String VMColB) {
        this.VMColB.set(VMColB);
    }

    public String getVMColC() {
        return VMColC.get();
    }

    public SimpleStringProperty VMColCProperty() {
        return VMColC;
    }

    public void setVMColC(String VMColC) {
        this.VMColC.set(VMColC);
    }

    public String getVMColD() {
        return VMColD.get();
    }

    public SimpleStringProperty VMColDProperty() {
        return VMColD;
    }

    public void setVMColD(String VMColD) {
        this.VMColD.set(VMColD);
    }

    public String getVMColE() {
        return VMColE.get();
    }

    public SimpleStringProperty VMColEProperty() {
        return VMColE;
    }

    public void setVMColE(String VMColE) {
        this.VMColE.set(VMColE);
    }

    public String getVMColF() {
        return VMColF.get();
    }

    public SimpleStringProperty VMColFProperty() {
        return VMColF;
    }

    public void setVMColF(String VMColF) {
        this.VMColF.set(VMColF);
    }
}
