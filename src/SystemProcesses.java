public class SystemProcesses {
    public static Process startStop = new Process() {
        @Override
        public void run() {
            // StartStop algorithm
        }
    };

    public static Process LoadProgram = new Process() {
        @Override
        public void run() {
            Resource fileNameRes = this.getOwnedResource(0);
            fileNameRes.request(this);

        }
    };

    public static Process JCL = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process Loader = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process MainProc = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process JobHelper = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process VirtualMachine = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process Interrupt = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process ChannelInterrupt = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process PutLine = new Process() {
        @Override
        public void run() {

        }
    };

    public static Process Idle = new Process() {
        @Override
        public void run() {

        }
    };
}
