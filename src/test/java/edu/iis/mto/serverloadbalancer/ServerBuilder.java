package edu.iis.mto.serverloadbalancer;

public class ServerBuilder implements Builder<Server> {

	private int capacity;
	private double initLoad;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server(capacity);

		if (initLoad > 0) {
			addInitLoad(server);
		}

		return server;
	}

	private void addInitLoad(Server server) {
		int initVmSize = (int) (initLoad / capacity * 100);
		Vm initVm = new Vm(initVmSize);
		server.addVm(initVm);
	}

	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withCurrentLoadOf(double initLoad) {
		this.initLoad = initLoad;
		return this;
	}
}
