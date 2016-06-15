package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;

public class ServerBuilder implements Builder<Server> {

    private int capacity;
    private int initLoad;

    public ServerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        Server theServer = new Server(capacity);

        if (initLoad > 0) {
            int initVmSize = (int) (initLoad / (double) capacity * 100.0);
            Vm initVm = vm().ofSize(initVmSize).build();
            theServer.addVm(initVm);
        }

        return theServer;
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public ServerBuilder withCurrentLoadOf(int initLoad) {
        this.initLoad = initLoad;
        return this;
    }
}
