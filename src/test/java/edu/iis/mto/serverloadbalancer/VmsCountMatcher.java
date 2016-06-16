package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class VmsCountMatcher extends TypeSafeMatcher<Server> {
    private int expectedVmsCount;

    public VmsCountMatcher(int expectedVmsCount) {
        this.expectedVmsCount = expectedVmsCount;
    }

    protected boolean matchesSafely(Server server) {
        return server.vmsCount() == expectedVmsCount;
    }

    public void describeTo(Description description) {
        description.appendText("vms count of ").appendValue(expectedVmsCount);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description mismatchDescription) {
        mismatchDescription.appendText("vms count of ").appendValue(item.vmsCount());
    }
}
