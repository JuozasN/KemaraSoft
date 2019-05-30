import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadProgram extends Process {

    public LoadProgram(OS os, Process parent){
        super(os);
        this.create(parent, (byte)3, "LoadProgram");
    }

    public void run() {
        switch(step){
            case 0:
                Distributor.request(this, Title.FILE_NAME);
                stepIncrement();
                return;
            case 1:

            case 2:
                Utils.getResource(os.resourceList, Title.KERNEL_MEMORY);
                //Copy file content to kernel memory
                String memPoint = ""; //Send pointer to program???
                Resource r = new DynamicResource(memPoint);
                r.create(os,this, Title.KERNEL_PROGRAM);
                r.release();
                stepReset();
                return;
            default:
                System.err.println("Impossible step at LoadProgram.run()");
                System.exit(0);
        }
//        ArrayList<Block> ownedResources = this.getOwnedResources();
//        if (ownedResources.isEmpty()) {
//            // procesas dar nebuvo gaves jokių resursų, todėl request'inam pirmo is turimų;
//            // procesas užsiblokuos ir nebus runninamas,
//            // todel nėra rizikos, kad request'insim keletą kartų
//            OS.systemResources.getFileNameRes().request(this);
//        } else if (ownedResources.size() == 1) {
//            // procesas gavo pirmąjį elementą - failo vardą
//            // ?? nuskaitom failo pavadinimą iš įvedimo supervizorinėje atmintyje?
//            // Block input = OS.readInput();
//            Block input = ownedResources.get(0);
//            if (Utils.validateFileName(input)) { // validuojame įvedimą
//                // failo pavadinimas korektiškas
//                // failą nuskaitom ir suskaidom blokais...
//                // request'inam supervizorinės atminties resurso
//                OS.systemResources.getKernelMemoryRes().request(this);
//            } else {
//                // failo pavadinimas nekorektiškas - throw'inam exception'ą?
//            }
//        } else if (ownedResources.size() == 2) {
//            // gavom antrąjį resursą - kernel memory
//            // kopijuojame nuskaitytus blokus į atmintį...
//            // atlaisviname procesų elementų sąrašą
//            this.removeOwnedResources();
//            // atlaisviname resursą "užduotis supervizorinėje atmintyje"
//            try {
//                OS.systemResources.getTaskInKernelMemoryRes().release(Block.getBlockFromString("Load Task"));
//            } catch (ProgramInterrupt e) {
//                e.printStackTrace();
//            }
//        }
    }
    
}
