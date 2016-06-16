package edu.iis.mto.serverloadbalancer;

public class Server {
    public static final double MAX_LOAD = 100.0;
    public double currentLoadPercentage;
    public int capacity;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm vm) {
        return true;
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = ((double) vm.size / capacity) * MAX_LOAD;
    }
}
