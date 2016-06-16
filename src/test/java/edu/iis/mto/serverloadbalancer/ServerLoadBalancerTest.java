package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static edu.iis.mto.serverloadbalancer.VmCountMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingAServer_noVms_serverStaysEmpty() {
		Server theServer = a(server().withCapacity(1));

		balance(aListOfServersWith(theServer), anEmptyListOfVms());

		assertThat(theServer, hasLoadPercentageOf(0.0d));
	}

	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(100.0d));
		assertThat("the server should contain vm", theServer.contains(theVm));
	}

	@Test 
	public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillTheServerWithTenPercent(){
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));

		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(10.0d));
		assertThat("the server should contain vm", theServer.contains(theVm));
		
	}

    @Test
    public void balancingAServerWithEnoughRoom_getsFilledWithAllVms() {
        Server theServer = a(server().withCapacity(10));
        Vm vm1 = a(vm().ofSize(1));
        Vm vm2 = a(vm().ofSize(1));

        balance(aListOfServersWith(theServer), aListOfVmsWith(vm1, vm2));

        assertThat(theServer, hasVmCountOf(2));
        assertThat("server should contain first vm", theServer.contains(vm1));
        assertThat("server should contain second vm", theServer.contains(vm2));
    }

    @Test
    public void aVm_shouldBeBalanced_onLessLoadedServerFirst() {
        Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(60.0));
        Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(40.0));

        Vm theVm = a(vm().ofSize(1));

        balance(aListOfServersWith(moreLoadedServer, lessLoadedServer), aListOfVmsWith(theVm));

        assertThat("more loaded server shouldn't contain vm", !moreLoadedServer.contains(theVm));
        assertThat("less loaded server should contain vm", lessLoadedServer.contains(theVm));
    }

    @Test
    public void balanceAServerWithNotEnoughRoom_shouldNotBeFilledWithAVm() {
        Server theServer = a(server().withCapacity(10).withCurrentLoadOf(80));
        Vm theVm = a(vm().ofSize(3));

        balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

        assertThat("server shouldn't contain vm", !theServer.contains(theVm));
    }

    @Test
    public void balance_serversAndVms() {
        Server firstServer = a(server().withCapacity(4));
        Server secondServer = a(server().withCapacity(6));

        Vm vm1 = a(vm().ofSize(1));
        Vm vm2 = a(vm().ofSize(4));
        Vm vm3 = a(vm().ofSize(2));

        balance(aListOfServersWith(firstServer, secondServer), aListOfVmsWith(vm1, vm2, vm3));

        assertThat("first server should contain first vm", firstServer.contains(vm1));
        assertThat("second server should contain second vm", secondServer.contains(vm2));
        assertThat("first server should contain third vm", firstServer.contains(vm3));

        assertThat(firstServer, hasLoadPercentageOf(3.0 / 4.0 * 100.0));
        assertThat(secondServer, hasLoadPercentageOf(4.0 / 6.0 * 100));

    }

    private void balance(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	private Vm[] aListOfVmsWith(Vm ... vms) {
		return vms;
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
