import javafx.beans.property.SimpleStringProperty;

public class KMemoryBlock {
    private SimpleStringProperty KMLineNo;
    private SimpleStringProperty KMCol0;
    private SimpleStringProperty KMCol1;
    private SimpleStringProperty KMCol2;
    private SimpleStringProperty KMCol3;
    private SimpleStringProperty KMCol4;
    private SimpleStringProperty KMCol5;
    private SimpleStringProperty KMCol6;
    private SimpleStringProperty KMCol7;
    private SimpleStringProperty KMCol8;
    private SimpleStringProperty KMCol9;
    private SimpleStringProperty KMColA;
    private SimpleStringProperty KMColB;
    private SimpleStringProperty KMColC;
    private SimpleStringProperty KMColD;
    private SimpleStringProperty KMColE;
    private SimpleStringProperty KMColF;


    public KMemoryBlock(String KMLineNo, String KMCol0, String KMCol1, String KMCol2, String KMCol3, String KMCol4, String KMCol5
            , String KMCol6, String KMCol7, String KMCol8, String KMCol9, String KMColA, String KMColB, String KMColC
            , String KMColD, String KMColE, String KMColF){

        this.KMLineNo = new SimpleStringProperty(KMLineNo);
        this.KMCol0 = new SimpleStringProperty(KMCol0);
        this.KMCol1 = new SimpleStringProperty(KMCol1);
        this.KMCol2 = new SimpleStringProperty(KMCol2);
        this.KMCol3 = new SimpleStringProperty(KMCol3);
        this.KMCol4 = new SimpleStringProperty(KMCol4);
        this.KMCol5 = new SimpleStringProperty(KMCol5);
        this.KMCol6 = new SimpleStringProperty(KMCol6);
        this.KMCol7 = new SimpleStringProperty(KMCol7);
        this.KMCol8 = new SimpleStringProperty(KMCol8);
        this.KMCol9 = new SimpleStringProperty(KMCol9);
        this.KMColA = new SimpleStringProperty(KMColA);
        this.KMColB = new SimpleStringProperty(KMColB);
        this.KMColC = new SimpleStringProperty(KMColC);
        this.KMColD = new SimpleStringProperty(KMColD);
        this.KMColE = new SimpleStringProperty(KMColE);
        this.KMColF = new SimpleStringProperty(KMColF);
    }

    public KMemoryBlock(String VMLineNo) {
        this(VMLineNo, "0000", "0000", "0000", "0000",
                "0000", "0000", "0000", "0000",
                "0000", "0000", "0000", "0000",
                "0000", "0000", "0000", "0000");
    }

    public String get(int i) {
        if (i < 0 || i > 15)
            return null;

        switch(i) {
            case 0:
                return getKMCol0();
            case 1:
                return getKMCol1();
            case 2:
                return getKMCol2();
            case 3:
                return getKMCol3();
            case 4:
                return getKMCol4();
            case 5:
                return getKMCol5();
            case 6:
                return getKMCol6();
            case 7:
                return getKMCol7();
            case 8:
                return getKMCol8();
            case 9:
                return getKMCol9();
            case 0xA:
                return getKMColA();
            case 0xB:
                return getKMColB();
            case 0xC:
                return getKMColC();
            case 0xD:
                return getKMColD();
            case 0xE:
                return getKMColE();
            case 0xF:
                return getKMColF();
            default:
                return null;
        }
    }

    public boolean set(int i, String value) {
        if (i < 0 || i > 15 || value.length() > 4)
            return false;

        switch(i) {
            case 0:
                setKMCol0(value);
                break;
            case 1:
                setKMCol1(value);
                break;
            case 2:
                setKMCol2(value);
                break;
            case 3:
                setKMCol3(value);
                break;
            case 4:
                setKMCol4(value);
                break;
            case 5:
                setKMCol5(value);
                break;
            case 6:
                setKMCol6(value);
                break;
            case 7:
                setKMCol7(value);
                break;
            case 8:
                setKMCol8(value);
                break;
            case 9:
                setKMCol9(value);
                break;
            case 0xA:
                setKMColA(value);
                break;
            case 0xB:
                setKMColB(value);
                break;
            case 0xC:
                setKMColC(value);
                break;
            case 0xD:
                setKMColD(value);
                break;
            case 0xE:
                setKMColE(value);
                break;
            case 0xF:
                setKMColF(value);
                break;
        }

        return true;
    }

    public void setInitial() {
        setKMCol0("0000");
        setKMCol1("0000");
        setKMCol2("0000");
        setKMCol3("0000");
        setKMCol4("0000");
        setKMCol5("0000");
        setKMCol6("0000");
        setKMCol7("0000");
        setKMCol8("0000");
        setKMCol9("0000");
        setKMColA("0000");
        setKMColB("0000");
        setKMColC("0000");
        setKMColD("0000");
        setKMColE("0000");
        setKMColF("0000");
    }

    public String getKMLineNo() {
        return KMLineNo.get();
    }

    public SimpleStringProperty KMLineNoProperty() {
        return KMLineNo;
    }

    public void setKMLineNo(String KMLineNo) {
        this.KMLineNo.set(KMLineNo);
    }

    public String getKMCol0() {
        return KMCol0.get();
    }

    public SimpleStringProperty KMCol0Property() {
        return KMCol0;
    }

    public void setKMCol0(String KMCol0) {
        this.KMCol0.set(KMCol0);
    }

    public String getKMCol1() {
        return KMCol1.get();
    }

    public SimpleStringProperty KMCol1Property() {
        return KMCol1;
    }

    public void setKMCol1(String KMCol1) {
        this.KMCol1.set(KMCol1);
    }

    public String getKMCol2() {
        return KMCol2.get();
    }

    public SimpleStringProperty KMCol2Property() {
        return KMCol2;
    }

    public void setKMCol2(String KMCol2) {
        this.KMCol2.set(KMCol2);
    }

    public String getKMCol3() {
        return KMCol3.get();
    }

    public SimpleStringProperty KMCol3Property() {
        return KMCol3;
    }

    public void setKMCol3(String KMCol3) {
        this.KMCol3.set(KMCol3);
    }

    public String getKMCol4() {
        return KMCol4.get();
    }

    public SimpleStringProperty KMCol4Property() {
        return KMCol4;
    }

    public void setKMCol4(String KMCol4) {
        this.KMCol4.set(KMCol4);
    }

    public String getKMCol5() {
        return KMCol5.get();
    }

    public SimpleStringProperty KMCol5Property() {
        return KMCol5;
    }

    public void setKMCol5(String KMCol5) {
        this.KMCol5.set(KMCol5);
    }

    public String getKMCol6() {
        return KMCol6.get();
    }

    public SimpleStringProperty KMCol6Property() {
        return KMCol6;
    }

    public void setKMCol6(String KMCol6) {
        this.KMCol6.set(KMCol6);
    }

    public String getKMCol7() {
        return KMCol7.get();
    }

    public SimpleStringProperty KMCol7Property() {
        return KMCol7;
    }

    public void setKMCol7(String KMCol7) {
        this.KMCol7.set(KMCol7);
    }

    public String getKMCol8() {
        return KMCol8.get();
    }

    public SimpleStringProperty KMCol8Property() {
        return KMCol8;
    }

    public void setKMCol8(String KMCol8) {
        this.KMCol8.set(KMCol8);
    }

    public String getKMCol9() {
        return KMCol9.get();
    }

    public SimpleStringProperty KMCol9Property() {
        return KMCol9;
    }

    public void setKMCol9(String KMCol9) {
        this.KMCol9.set(KMCol9);
    }

    public String getKMColA() {
        return KMColA.get();
    }

    public SimpleStringProperty KMColAProperty() {
        return KMColA;
    }

    public void setKMColA(String KMColA) {
        this.KMColA.set(KMColA);
    }

    public String getKMColB() {
        return KMColB.get();
    }

    public SimpleStringProperty KMColBProperty() {
        return KMColB;
    }

    public void setKMColB(String KMColB) {
        this.KMColB.set(KMColB);
    }

    public String getKMColC() {
        return KMColC.get();
    }

    public SimpleStringProperty KMColCProperty() {
        return KMColC;
    }

    public void setKMColC(String KMColC) {
        this.KMColC.set(KMColC);
    }

    public String getKMColD() {
        return KMColD.get();
    }

    public SimpleStringProperty KMColDProperty() {
        return KMColD;
    }

    public void setKMColD(String KMColD) {
        this.KMColD.set(KMColD);
    }

    public String getKMColE() {
        return KMColE.get();
    }

    public SimpleStringProperty KMColEProperty() {
        return KMColE;
    }

    public void setKMColE(String KMColE) {
        this.KMColE.set(KMColE);
    }

    public String getKMColF() {
        return KMColF.get();
    }

    public SimpleStringProperty KMColFProperty() {
        return KMColF;
    }

    public void setKMColF(String KMColF) {
        this.KMColF.set(KMColF);
    }
}
