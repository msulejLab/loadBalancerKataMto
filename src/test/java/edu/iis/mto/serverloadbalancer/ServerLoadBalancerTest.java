package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOfVms;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
    public void balancingServer_noVm_ServerStaysEmpty() {
        Server theServer = a(server().withCapacity(1));

        balancing(aListOfServersWith(theServer), anEmptyListOfVms());

        assertThat(theServer, hasCurrentLoadOfVms(0.0d));
    }

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
        Server theServer = a(server().withCapacity(1));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadOfVms(100.0d));
        assertThat("server should contain vm", theServer.contains(vm));

    }

    private void balancing(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] anEmptyListOfVms() {
        return new Vm[0];
    }

    private Server[] aListOfServersWith(Server server) {
        return new Server[] {server};
    }

    private Server a(ServerBuilder builder) {
        return builder.build();
    }
}
