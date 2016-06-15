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

        addInitialLoadServer(theServer);

        return theServer;
    }

    private void addInitialLoadServer(Server theServer) {
        if (initLoad > 0) {
            int initVmSize = (int) (initLoad / (double) capacity * Server.MAXIMUM_LOAD);
            Vm initVm = vm().ofSize(initVmSize).build();
            theServer.addVm(initVm);
        }
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public ServerBuilder withCurrentLoadOf(int initLoad) {
        this.initLoad = initLoad;
        return this;
    }
}
