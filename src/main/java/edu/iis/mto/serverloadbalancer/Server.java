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
		this.currentLoadPercentage = (double) vm.size / (double) this.capacity * MAXIMUM_LOAD;
        vms.add(vm);
	}

	public int countVms() {
		return vms.size();
	}

    public boolean canFit(Vm vm) {
        double loadPercentageWithVm = currentLoadPercentage + ((double) vm.size / capacity) * 100;

        return loadPercentageWithVm <= MAXIMUM_LOAD;
    }
}
