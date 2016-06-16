package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			Server lessLoadedServer = extractLessLoadedServer(servers);

			if (lessLoadedServer != null) {
				lessLoadedServer.addVm(vm);
			}
		}
	}

	private Server extractLessLoadedServer(Server[] servers) {
		Server lessLoadedServer = null;

		for (Server server : servers) {
            if (lessLoadedServer == null || server.currentLoadPercentage < lessLoadedServer.currentLoadPercentage) {
                lessLoadedServer = server;
            }
        }
		return lessLoadedServer;
	}

}
