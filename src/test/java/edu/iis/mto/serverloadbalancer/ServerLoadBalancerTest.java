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

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillTheServerWithTheVm() {
        Server theServer = a(ServerBuilder.server().withCapacity(1));
        Vm theVm = a(vm().ofSize(1));

        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, CurrentLoadPercentageMatcher.hasCurrentLoadPercentageOf(100.0d));
        assertThat("server should contain vm", theServer.contains(theVm));
    }

    private Vm[] aListOfVmsWith(Vm ... vms) {
        return vms;
    }

    private Vm a(VmBuilder builder) {
        return builder.build();
    }

    private VmBuilder vm() {
        return new VmBuilder();
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
