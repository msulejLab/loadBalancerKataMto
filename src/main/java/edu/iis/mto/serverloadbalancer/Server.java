package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private static final double MAXIMUM_LOAD = 100.0d;

    public double currentLoadPercentage;
	private int capacity;

    private List<Vm> vms = new ArrayList<Vm>();

	public Server(int capacity) {
		this.capacity = capacity;
	}

	public boolean contains(Vm vm) {
		return vms.contains(vm);
	}

	public void addVm(Vm vm) {
		this.currentLoadPercentage += vmLoad(vm);
        vms.add(vm);
	}

    private double vmLoad(Vm vm) {
        return ((double) vm.size / this.capacity) * MAXIMUM_LOAD;
    }

    public int countVms() {
		return vms.size();
	}

    public boolean canFit(Vm vm) {
        double loadPercentageWithVm = currentLoadPercentage + vmLoad(vm);

        return loadPercentageWithVm <= MAXIMUM_LOAD;
    }
}
