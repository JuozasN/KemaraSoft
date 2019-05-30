public class SystemResources {
    private static final StaticResource fileName = new StaticResource();
    private static final StaticResource kernelMemory = new StaticResource();
    private static final StaticResource taskInKernelMemory = new StaticResource();

    public static void initResources(OS os, Process creatorProcess) {
        fileName.create(os, creatorProcess, Title.FILE_NAME);
        kernelMemory.create(os, creatorProcess, Title.KERNEL_MEMORY);
        taskInKernelMemory.create(os, creatorProcess, Title.KERNEL_PROGRAM);
    }

    public static StaticResource getFileNameRes() {
        return fileName;
    }

    public static StaticResource getKernelMemoryRes() {
        return kernelMemory;
    }

    public static StaticResource getTaskInKernelMemoryRes() {
        return taskInKernelMemory;
    }
}
