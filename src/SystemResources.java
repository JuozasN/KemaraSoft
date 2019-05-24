public class SystemResources {
    private final Resource fileName = new Resource();
    private final Resource kernelMemory = new Resource();
    private final Resource taskInKernelMemory = new Resource();

    public void initResources(Process creatorProcess) {
        fileName.create(creatorProcess, "File Name");
        kernelMemory.create(creatorProcess, "Kernel Memory");
        taskInKernelMemory.create(creatorProcess, "Task In Kernel Memory");
    }

    public Resource getFileNameRes() {
        return fileName;
    }

    public Resource getKernelMemoryRes() {
        return kernelMemory;
    }

    public Resource getTaskInKernelMemoryRes() {
        return taskInKernelMemory;
    }
}
