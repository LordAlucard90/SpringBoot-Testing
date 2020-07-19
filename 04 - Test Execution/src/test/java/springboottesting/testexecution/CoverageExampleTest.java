package springboottesting.testexecution;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoverageExampleTest {

    @Test
    void answer() {
        assertEquals(42, CoverageExample.answer());
    }
}