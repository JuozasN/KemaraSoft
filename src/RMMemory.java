import javafx.beans.property.SimpleStringProperty;

public class RMMemory {
    private SimpleStringProperty LineNo;
    private SimpleStringProperty RMCol0;
    private SimpleStringProperty RMCol1;
    private SimpleStringProperty RMCol2;
    private SimpleStringProperty RMCol3;
    private SimpleStringProperty RMCol4;
    private SimpleStringProperty RMCol5;
    private SimpleStringProperty RMCol6;
    private SimpleStringProperty RMCol7;
    private SimpleStringProperty RMCol8;
    private SimpleStringProperty RMCol9;
    private SimpleStringProperty RMColA;
    private SimpleStringProperty RMColB;
    private SimpleStringProperty RMColC;
    private SimpleStringProperty RMColD;
    private SimpleStringProperty RMColE;
    private SimpleStringProperty RMColF;


    public RMMemory(String LineNo, String RMCol0, String RMCol1, String RMCol2, String RMCol3, String RMCol4, String RMCol5
            , String RMCol6, String RMCol7, String RMCol8, String RMCol9, String RMColA, String RMColB, String RMColC
            , String RMColD, String RMColE, String RMColF){

        this.LineNo = new SimpleStringProperty(LineNo);
        this.RMCol0 = new SimpleStringProperty(RMCol0);
        this.RMCol1 = new SimpleStringProperty(RMCol1);
        this.RMCol2 = new SimpleStringProperty(RMCol2);
        this.RMCol3 = new SimpleStringProperty(RMCol3);
        this.RMCol4 = new SimpleStringProperty(RMCol4);
        this.RMCol5 = new SimpleStringProperty(RMCol5);
        this.RMCol6 = new SimpleStringProperty(RMCol6);
        this.RMCol7 = new SimpleStringProperty(RMCol7);
        this.RMCol8 = new SimpleStringProperty(RMCol8);
        this.RMCol9 = new SimpleStringProperty(RMCol9);
        this.RMColA = new SimpleStringProperty(RMColA);
        this.RMColB = new SimpleStringProperty(RMColB);
        this.RMColC = new SimpleStringProperty(RMColC);
        this.RMColD = new SimpleStringProperty(RMColD);
        this.RMColE = new SimpleStringProperty(RMColE);
        this.RMColF = new SimpleStringProperty(RMColF);

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

    public String getRMCol0() {
        return RMCol0.get();
    }

    public SimpleStringProperty RMCol0Property() {
        return RMCol0;
    }

    public void setRMCol0(String RMCol0) {
        this.RMCol0.set(RMCol0);
    }

    public String getRMCol1() {
        return RMCol1.get();
    }

    public SimpleStringProperty RMCol1Property() {
        return RMCol1;
    }

    public void setRMCol1(String RMCol1) {
        this.RMCol1.set(RMCol1);
    }

    public String getRMCol2() {
        return RMCol2.get();
    }

    public SimpleStringProperty RMCol2Property() {
        return RMCol2;
    }

    public void setRMCol2(String RMCol2) {
        this.RMCol2.set(RMCol2);
    }

    public String getRMCol3() {
        return RMCol3.get();
    }

    public SimpleStringProperty RMCol3Property() {
        return RMCol3;
    }

    public void setRMCol3(String RMCol3) {
        this.RMCol3.set(RMCol3);
    }

    public String getRMCol4() {
        return RMCol4.get();
    }

    public SimpleStringProperty RMCol4Property() {
        return RMCol4;
    }

    public void setRMCol4(String RMCol4) {
        this.RMCol4.set(RMCol4);
    }

    public String getRMCol5() {
        return RMCol5.get();
    }

    public SimpleStringProperty RMCol5Property() {
        return RMCol5;
    }

    public void setRMCol5(String RMCol5) {
        this.RMCol5.set(RMCol5);
    }

    public String getRMCol6() {
        return RMCol6.get();
    }

    public SimpleStringProperty RMCol6Property() {
        return RMCol6;
    }

    public void setRMCol6(String RMCol6) {
        this.RMCol6.set(RMCol6);
    }

    public String getRMCol7() {
        return RMCol7.get();
    }

    public SimpleStringProperty RMCol7Property() {
        return RMCol7;
    }

    public void setRMCol7(String RMCol7) {
        this.RMCol7.set(RMCol7);
    }

    public String getRMCol8() {
        return RMCol8.get();
    }

    public SimpleStringProperty RMCol8Property() {
        return RMCol8;
    }

    public void setRMCol8(String RMCol8) {
        this.RMCol8.set(RMCol8);
    }

    public String getRMCol9() {
        return RMCol9.get();
    }

    public SimpleStringProperty RMCol9Property() {
        return RMCol9;
    }

    public void setRMCol9(String RMCol9) {
        this.RMCol9.set(RMCol9);
    }

    public String getRMColA() {
        return RMColA.get();
    }

    public SimpleStringProperty RMColAProperty() {
        return RMColA;
    }

    public void setRMColA(String RMColA) {
        this.RMColA.set(RMColA);
    }

    public String getRMColB() {
        return RMColB.get();
    }

    public SimpleStringProperty RMColBProperty() {
        return RMColB;
    }

    public void setRMColB(String RMColB) {
        this.RMColB.set(RMColB);
    }

    public String getRMColC() {
        return RMColC.get();
    }

    public SimpleStringProperty RMColCProperty() {
        return RMColC;
    }

    public void setRMColC(String RMColC) {
        this.RMColC.set(RMColC);
    }

    public String getRMColD() {
        return RMColD.get();
    }

    public SimpleStringProperty RMColDProperty() {
        return RMColD;
    }

    public void setRMColD(String RMColD) {
        this.RMColD.set(RMColD);
    }

    public String getRMColE() {
        return RMColE.get();
    }

    public SimpleStringProperty RMColEProperty() {
        return RMColE;
    }

    public void setRMColE(String RMColE) {
        this.RMColE.set(RMColE);
    }

    public String getRMColF() {
        return RMColF.get();
    }

    public SimpleStringProperty RMColFProperty() {
        return RMColF;
    }

    public void setRMColF(String RMColF) {
        this.RMColF.set(RMColF);
    }
}