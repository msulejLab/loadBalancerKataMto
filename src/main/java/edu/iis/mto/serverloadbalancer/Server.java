package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final double MAX_LOAD = 100.0;

    private double currentLoadPercentage;
    private int capacity;

    private List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        this.setCapacity(capacity);
    }

    public boolean contains(Vm vm) {
        return vms.contains(vm);
    }

    public void addVm(Vm vm) {
        setCurrentLoadPercentage(getCurrentLoadPercentage() + vmLoad(vm));
        vms.add(vm);
    }

    private double vmLoad(Vm vm) {
        return ((double) vm.getSize() / getCapacity()) * MAX_LOAD;
    }

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        double loadPercentageWithVm = getCurrentLoadPercentage() + (vm.getSize() / getCapacity()) * MAX_LOAD;
        return loadPercentageWithVm <= MAX_LOAD;
    }

    public double getCurrentLoadPercentage() {
        return currentLoadPercentage;
    }

    public void setCurrentLoadPercentage(double currentLoadPercentage) {
        this.currentLoadPercentage = currentLoadPercentage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
