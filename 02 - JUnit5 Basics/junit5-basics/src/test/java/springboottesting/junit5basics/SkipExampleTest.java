package springboottesting.junit5basics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SkipExampleTest {
    SkipExample skipExample;

    @BeforeEach
    void setUp() {
        skipExample = new SkipExample();
    }

    @Disabled(value = "Disabled until it is implemented")
    @Test
    void implemented() {
        skipExample.implemented();
    }
}