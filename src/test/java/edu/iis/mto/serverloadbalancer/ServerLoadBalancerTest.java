package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
    public void balancingServers_noVm_ServerStaysEmpty() {
        Server theServer = a(ServerBuilder.server().withCapacity(10));

        balance(aListOfServersWith(theServer), anEmptyListOfVms());

        assertThat(theServer, CurrentLoadPercentageMatcher.hasCurrentLoadPercentageOf(0.0d));
    }

    private void balance(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] anEmptyListOfVms() {
        return new Vm[0];
    }

    private Server[] aListOfServersWith(Server ... servers) {
        return servers;
    }

    private Server a(ServerBuilder builder) {
        return builder.build();
    }
}
