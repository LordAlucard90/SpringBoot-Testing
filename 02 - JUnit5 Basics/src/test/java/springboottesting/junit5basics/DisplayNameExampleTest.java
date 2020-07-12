package springboottesting.junit5basics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DisplayNameExampleTest {
    @Test
    @DisplayName(value = "Nice test name")
    void uglyTestName() {
    }
}