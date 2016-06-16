package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class VmsCountMatcher extends TypeSafeMatcher<Server> {
    private int expectedSize;

    public VmsCountMatcher(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    protected boolean matchesSafely(Server server) {
        return expectedSize == server.countVms();
    }

    public void describeTo(Description description) {
        description.appendText("count of vms ").appendValue(expectedSize);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description mismatchDescription) {
        mismatchDescription.appendText("count of vms").appendValue(item.countVms());
    }

    public static VmsCountMatcher hasVmsCountOf(int expectedSize) {
        return new VmsCountMatcher(expectedSize);
    }
}
