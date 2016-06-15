package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int MAXIMUM_LOAD = 100;

    private double currentLoadPercentage;
    private int capacity;

    private List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        this.setCapacity(capacity);
    }

    public boolean contains(Vm theVm) {
        return vms.contains(theVm);
    }

    public void addVm(Vm vm) {
        setCurrentLoadPercentage(getCurrentLoadPercentage() + loadOfVm(vm));
        vms.add(vm);
    }

    private double loadOfVm(Vm vm) {
        return ((double) vm.getSize() / getCapacity()) * MAXIMUM_LOAD;
    }

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        double loadPercentageWithVm = getCurrentLoadPercentage() + loadOfVm(vm);

        return loadPercentageWithVm <= MAXIMUM_LOAD;
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
