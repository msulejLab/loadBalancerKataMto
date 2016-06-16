package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private static final double MAXIMUM_LOAD = 100.0d;

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
		this.setCurrentLoadPercentage(this.getCurrentLoadPercentage() + vmLoad(vm));
        vms.add(vm);
	}

    private double vmLoad(Vm vm) {
        return ((double) vm.getSize() / this.getCapacity()) * MAXIMUM_LOAD;
    }

    public int countVms() {
		return vms.size();
	}

    public boolean canFit(Vm vm) {
        double loadPercentageWithVm = getCurrentLoadPercentage() + vmLoad(vm);

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
