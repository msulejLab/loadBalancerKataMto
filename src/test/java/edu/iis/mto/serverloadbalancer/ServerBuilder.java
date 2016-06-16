package edu.iis.mto.serverloadbalancer;

public class ServerBuilder {
    static ServerBuilder server() {
        return new ServerBuilder();
    }

    public ServerBuilder withCapacity(int capacity) {
        return this;
    }

    public Server build() {
        return new Server();
    }
}
