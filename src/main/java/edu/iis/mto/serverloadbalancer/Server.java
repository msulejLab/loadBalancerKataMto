package edu.iis.mto.serverloadbalancer;

public class Server {
    public int capacity;
    public double currentLoadPercentage;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return true;
    }
}
