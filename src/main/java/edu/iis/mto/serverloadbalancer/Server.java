package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final double MAX_LOAD = 100.0d;

    private double capacity;
    private double currentLoadPercentage;

    private List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        this.setCapacity(capacity);
    }

    public boolean contains(Vm vm) {
        return vms.contains(vm);
    }

    public void addVm(Vm vm) {
        setCurrentLoadPercentage(getCurrentLoadPercentage() + loadOfVm(vm));
        vms.add(vm);
    }

    public int vmsCount() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        double currentLoadPercentageWithVm = getCurrentLoadPercentage() + ((double) vm.getSize() / getCapacity()) * 100.0;

        return currentLoadPercentageWithVm <= MAX_LOAD;
    }

    private double loadOfVm(Vm vm) {
        return ((double) vm.getSize() / getCapacity()) * MAX_LOAD;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getCurrentLoadPercentage() {
        return currentLoadPercentage;
    }

    public void setCurrentLoadPercentage(double currentLoadPercentage) {
        this.currentLoadPercentage = currentLoadPercentage;
    }
}
