package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest {
    private final Logger LOGGER = Logger.getLogger(ServerLoadBalancerParametrizedTest.class.getSimpleName());

    @Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
                {1, 1, 100}, {1, 0, 0}, {10, 1, 10}
        });
	}

    private int serverCapacity;
    private int vmSize;
    private int expectedLoadPercentage;

    public ServerLoadBalancerParametrizedTest(int serverCapacity, int vmSize, int expected) {
        this.serverCapacity = serverCapacity;
        this.vmSize = vmSize;
        this.expectedLoadPercentage = expected;
    }

    @Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
        LOGGER.info(String.format("Running test for server with capacity %d and vm of size %d " +
                "for expected load percentage of %d", serverCapacity, vmSize, expectedLoadPercentage));

		Server theServer = a(server().withCapacity(serverCapacity));
		Vm theVm = a(vm().ofSize(vmSize));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(expectedLoadPercentage));
		//assertThat("the server should contain vm", theServer.contains(theVm));
	}
}
