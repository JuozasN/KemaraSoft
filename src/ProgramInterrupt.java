public class ProgramInterrupt extends Exception{
    int intCode;

    ProgramInterrupt(int intCode, String message){
        super(message);
        this.intCode = intCode;
    }

    public int getIntCode(){return intCode;}
}
