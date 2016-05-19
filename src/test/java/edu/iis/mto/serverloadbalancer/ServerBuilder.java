package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {

    private int capacity;
    private double currentLoad;

    public ServerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Builder<Server> withCurrentLoadOf(double initLoad) {
        this.currentLoad = initLoad;
        return this;
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public Server build() {
        Server server = new Server(capacity);

        if (currentLoad > 0) {
            int initVmSize = (int) (currentLoad / (double) capacity * 100);
            Vm initVm = VmBuilder.vm().ofSize(initVmSize).build();
            server.addVm(initVm);
        }

        return server;
    }
}
