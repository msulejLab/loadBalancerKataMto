package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

public class ServerLoadBalancer {

    public void balance(Server[] servers, Vm[] vms) {
        for (Vm vm : vms) {
            addToCapableLessLoadedServer(servers, vm);
        }
    }

    private void addToCapableLessLoadedServer(Server[] servers, Vm vm) {
        List<Server> capableServers = findServersWithEnoughCapacity(servers, vm);

        Server lessLoadedServer = extractLessLoadedServer(capableServers);

        if (lessLoadedServer != null) {
            lessLoadedServer.addVm(vm);
        }
    }

    private List<Server> findServersWithEnoughCapacity(Server[] servers, Vm vm) {
        List<Server> capableServers = new ArrayList<Server>();

        for (Server server : servers) {
            if (server.canFit(vm)) {
                capableServers.add(server);
            }
        }
        return capableServers;
    }

    private Server extractLessLoadedServer(List<Server> servers) {
        Server lessLoadedServer = null;

        for (Server server : servers) {
            if (lessLoadedServer == null) {
                lessLoadedServer = server;
            }

            if (server.getCurrentLoadPercentage() < lessLoadedServer.getCurrentLoadPercentage()) {
                lessLoadedServer = server;
            }
        }
        return lessLoadedServer;
    }
}
