package edu.iis.mto.serverloadbalancer;

public class ServerBuilder {
    public ServerBuilder withCapacity(int capacity) {
        return this;
    }

    public Server build() {
        return new Server();
    }
}
