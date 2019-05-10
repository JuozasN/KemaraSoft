public class SystemInterrupt extends Exception{
    byte intCode;

    SystemInterrupt(byte intCode, String message){
        super(message);
        this.intCode = intCode;
    }

    public byte getIntCode(){return this.intCode;}
}