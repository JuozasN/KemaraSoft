import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static BufferedReader br;
    public static void main(String[] args) {
        if(args.length != 1){
            System.err.println("Invalid run argument");
            System.exit(0);
        }

        VM vm = new VM(new Block[VM.MEM_BLOCK_COUNT]);

        try{
            br = new BufferedReader(new FileReader(args[0]));

            byte adr = 0;
            for(String line; (line = br.readLine()) != null; ){
                String[] strArray = line.split(" ");
                for(String str: strArray){
                    if (str.length() > 4){
                        // Invalid command interrupt?
                        System.exit(0);
                    }
                    vm.setValue(str.getBytes(), adr);
                    adr++;
                }
            }
        } catch(FileNotFoundException e){
            System.err.println("File not found");
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error reading from file");
            System.exit(0);
        }

        vm.exec();

        for(int i = 0; i < VM.MEM_BLOCK_COUNT; ++i){
            System.out.println(vm.getMem()[i]);
        }
    }
}
