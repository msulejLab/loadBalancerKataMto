package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.*;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.*;
import static edu.iis.mto.serverloadbalancer.VmBuilder.*;
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
        Server theServer = a(server().withCapacity(10));

        balance(aListOfServersWith(theServer), anEmptyListOfVms());

        assertThat(theServer, hasCurrentLoadPercentageOf(0.0d));
    }

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillTheServerWithTheVm() {
        Server theServer = a(server().withCapacity(1));
        Vm theVm = a(vm().ofSize(1));

        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadPercentageOf(100.0d));
        assertThat("server should contain vm", theServer.contains(theVm));
    }

    @Test
    public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent() {
        Server theServer = a(server().withCapacity(10));
        Vm theVm = a(vm().ofSize(1));

        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadPercentageOf(10.0d));
        assertThat("server should contain vm", theServer.contains(theVm));
    }

    @Test
    public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
        Server theServer = a(server().withCapacity(10));
        Vm firstVm = a(vm().ofSize(1));
        Vm secondVm = a(vm().ofSize(1));

        balance(aListOfServersWith(theServer), aListOfVmsWith(firstVm, secondVm));

        assertThat(theServer, VmsCountMatcher.hasVmsCountOf(2));
        assertThat("server should contain first vm", theServer.contains(firstVm));
        assertThat("server should contain second vm", theServer.contains(secondVm));
    }

    @Test
    public void aVm_shouldBeBalanced_onLessLoadedServerFirst() {
        Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadPercentage(45.0));
        Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadPercentage(50.0));
        Vm theVm = a(vm().ofSize(1));

        balance(aListOfServersWith(lessLoadedServer, moreLoadedServer), aListOfVmsWith(theVm));

        assertThat("less loaded server should contain vm", lessLoadedServer.contains(theVm));
        assertThat("more loaded server shouldn't contain vm", !moreLoadedServer.contains(theVm));
    }

    @Test
    public void balanceAServerWithNotEnoughRoom_shouldNotBeFilledWithAVm() {
        Server theServer = a(server().withCapacity(10).withCurrentLoadPercentage(80.0));
        Vm theVm = a(vm().ofSize(3));

        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat("server should not contain vm", !theServer.contains(theVm));
    }

    @Test
    public void balance_serversAndVms() {
        Server firstServer = a(server().withCapacity(4));
        Server secondServer = a(server().withCapacity(6));

        Vm firstVm = a(vm().ofSize(2));
        Vm secondVm = a(vm().ofSize(4));
        Vm thirdVm = a(vm().ofSize(1));

        balance(aListOfServersWith(firstServer, secondServer), aListOfVmsWith(firstVm, secondVm, thirdVm));

        assertThat("first server should contain first vm", firstServer.contains(firstVm));
        assertThat("second server should contain second vm", secondServer.contains(secondVm));
        assertThat("first server should contain third vm", firstServer.contains(thirdVm));

        assertThat(firstServer, hasCurrentLoadPercentageOf(3.0 / 4.0 * 100.0));
        assertThat(secondServer, hasCurrentLoadPercentageOf(4.0 / 6.0 * 100.0));
    }

    private Vm[] aListOfVmsWith(Vm ... vms) {
        return vms;
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

    private <T> T a(Builder<T> builder) {
        return builder.build();
    }
}
