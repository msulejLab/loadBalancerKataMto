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
        return true;
    }

    public void addVm(Vm vm) {
        currentLoadPercentage = ((double) vm.size / capacity) * MAXIMUM_LOAD;
        vms.add(vm);
    }

    public int countVms() {
        return vms.size();
    }
}
