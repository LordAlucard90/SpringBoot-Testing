package springboottesting.junit5basics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class AssumptionsExampleTest {
    @Test
    void skippedTest() {
        assumeTrue(false);
        assertTrue(false);
    }

    @Test
    void executedTest() {
        assumeTrue(true);
        assertTrue(true);
    }
}