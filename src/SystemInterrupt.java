public class SystemInterrupt extends Exception{
    int intCode;

    SystemInterrupt(int intCode, String message){
        super(message);
        this.intCode = intCode;
    }

    public int getIntCode(){return intCode;}
}