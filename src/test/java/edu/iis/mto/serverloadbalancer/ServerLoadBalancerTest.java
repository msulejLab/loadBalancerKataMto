package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOfVms;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasAVmsCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Matcher;
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
        assertThat("server should contain vm", theServer.contains(theVm));
    }

    @Test
    public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent() {
        Server theServer = a(server().withCapacity(10));
        Vm theVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat(theServer, hasCurrentLoadOfVms(10.0d));
        assertThat("server should contain vm", theServer.contains(theVm));
    }

    @Test
    public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
        Server theServer = a(server().withCapacity(100));
        Vm theFirstVm = a(vm().ofSize(1));
        Vm theSecondVm = a(vm().ofSize(1));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theFirstVm, theSecondVm));

        assertThat(theServer, hasAVmsCountOf(2));
        assertThat("server should contain first vm", theServer.contains(theFirstVm));
        assertThat("server should contain second vm", theServer.contains(theSecondVm));
    }

    @Test
    public void aVm_shouldBeBalanced_onLessLoadedServerFirst() {
        Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(50.0));
        Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(45.0));

        Vm theVm = a(vm().ofSize(10));

        balancing(aListOfServersWith(moreLoadedServer, lessLoadedServer), aListOfVmsWith(theVm));

        assertThat("more loaded server shouldn't contain vm", !moreLoadedServer.contains(theVm));
        assertThat("less loaded server should contain vm", lessLoadedServer.contains(theVm));
    }

    @Test
    public void balanceAServerWithNotEnoughRoom_shouldNotBeFilledWithAVm() {
        Server theServer = a(server().withCapacity(10).withCurrentLoadOf(90.0));
        Vm theVm = a(vm().ofSize(2));

        balancing(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat("server shouldn't contain the vm", !theServer.contains(theVm));
    }

    @Test
    public void balance_serversAndVms() {
        Server server1 = a(server().withCapacity(4));
        Server server2 = a(server().withCapacity(6));

        Vm vm1 = a(vm().ofSize(1));
        Vm vm2 = a(vm().ofSize(4));
        Vm vm3 = a(vm().ofSize(2));

        balancing(aListOfServersWith(server1, server2), aListOfVmsWith(vm1, vm2, vm3));

        assertThat("server 1 should contain vm1", server1.contains(vm1));
        assertThat("server 2 should contain vm2", server2.contains(vm2));
        assertThat("server 1 should contain vm3", server1.contains(vm3));

        assertThat(server1, hasCurrentLoadOfVms(75.0));
        assertThat(server2, hasCurrentLoadOfVms(66.66));
    }

    private void balancing(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] anEmptyListOfVms() {
        return new Vm[0];
    }

    private Server[] aListOfServersWith(Server... servers) {
        return servers;
    }

    private Vm[] aListOfVmsWith(Vm... vms) {
        return vms;
    }

    private <T> T a(Builder<T> builder) {
        return builder.build();
    }
}
