package wtf.pants.bindings.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFunctional {

    public String foldingFunction(String a, String b) {
        return a + "/" + b;
    }

    @Test
    public void testFoldLeft() {
        final String startingValue = "";
        final String[] basicArray = {"home", "test", "expected"};

        final String actualJoinedPath = Functional.foldLeft(basicArray, startingValue, this::foldingFunction);
        final String expectedJoinedPath = "/home/test/expected";

        assertEquals(expectedJoinedPath, actualJoinedPath);
    }

}
