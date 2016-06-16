package edu.iis.mto.serverloadbalancer;

public class Server {
    public double capacity;
    public double currentLoadPercentage;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm vm) {
        return true;
    }
}
