package edu.iis.mto.serverloadbalancer;

public class Server {
    public static final int MAXIMUM_LOAD = 100;

    public double currentLoadPercentage;
    public int capacity;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return true;
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = ((double) vm.size / capacity) * MAXIMUM_LOAD;
    }
}
