package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class VmCountMatcher extends TypeSafeMatcher<Server> {
    private int expectedSize;

    public VmCountMatcher(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    protected boolean matchesSafely(Server server) {
        return expectedSize == server.countVms();
    }

    public void describeTo(Description description) {
        description.appendText("vm count of ").appendValue(expectedSize);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description mismatchDescription) {
        mismatchDescription.appendText("vm count of ").appendValue(item.countVms());
    }

    public static VmCountMatcher hasVmCountOf(int expectedSize) {
        return new VmCountMatcher(expectedSize);
    }
}
