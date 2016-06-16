package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final double MAX_LOAD = 100.0d;

    public double capacity;
    public double currentLoadPercentage;

    private List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm vm) {
        return vms.contains(vm);
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = ((double) vm.size / capacity) * MAX_LOAD;
        vms.add(vm);
    }

    public int vmsCount() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        double currentLoadPercentageWithVm = currentLoadPercentage + ((double) vm.size / capacity) * 100.0;

        return currentLoadPercentageWithVm <= MAX_LOAD;
    }
}
