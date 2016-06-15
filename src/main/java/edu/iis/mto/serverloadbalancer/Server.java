package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int MAXIMUM_LOAD = 100;

    public double currentLoadPercentage;
    public int capacity;

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

    private double loadOfVm(Vm vm) {
        return ((double) vm.size / capacity) * MAXIMUM_LOAD;
    }

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        double loadPercentageWithVm = currentLoadPercentage + loadOfVm(vm);

        return loadPercentageWithVm <= MAXIMUM_LOAD;
    }
}
