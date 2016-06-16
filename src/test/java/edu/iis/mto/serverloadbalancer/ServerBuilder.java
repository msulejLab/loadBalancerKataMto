package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {
    private int capacity;
    private double initLoad;

    static ServerBuilder server() {
        return new ServerBuilder();
    }

    public ServerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ServerBuilder withCurrentLoadOf(double initLoad) {
        this.initLoad = initLoad;
        return this;
    }

    public Server build() {
        Server server = new Server(capacity);

        if (initLoad > 0) {
            addInitVm(server);
        }

        return server;
    }

    private void addInitVm(Server server) {
        int initVmSize = (int) ((initLoad / (double) capacity) * 100.0);
        Vm initVm = new Vm(initVmSize);
        server.addVm(initVm);
    }
}
