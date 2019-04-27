public class ProgramInterrupt extends Exception{
    byte intCode;

    ProgramInterrupt(byte intCode, String message){
        super(message);
        this.intCode = intCode;
    }

    public byte getIntCode(){return this.intCode;}
}
