package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int MAXIMUM_LOAD = 100;

    public int capacity;
    public double currentLoadPercentage;

    private List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return vms.contains(theVm);
    }

    public void addVm(Vm vm) {
        currentLoadPercentage += loadOfVm(vm);
        vms.add(vm);
    }

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        return currentLoadPercentage +loadOfVm(vm) <= MAXIMUM_LOAD;
    }

    private double loadOfVm(Vm vm) {
        return ((double) vm.size / (double) capacity) * MAXIMUM_LOAD;
    }
}
