package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {

    public ServerBuilder withCapacity(int capacity) {
        return this;
    }

    public Server build() {
        return new Server();
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }
}
