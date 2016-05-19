package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int MAXIMUM_LOAD = 100;

    private int capacity;
    private double currentLoadPercentage;

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

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        return getCurrentLoadPercentage() +loadOfVm(vm) <= MAXIMUM_LOAD;
    }

    private double loadOfVm(Vm vm) {
        return ((double) vm.getSize() / (double) getCapacity()) * MAXIMUM_LOAD;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCurrentLoadPercentage() {
        return currentLoadPercentage;
    }

    public void setCurrentLoadPercentage(double currentLoadPercentage) {
        this.currentLoadPercentage = currentLoadPercentage;
    }
}
