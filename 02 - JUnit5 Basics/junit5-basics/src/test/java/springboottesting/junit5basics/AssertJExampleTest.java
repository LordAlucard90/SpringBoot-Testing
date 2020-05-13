package springboottesting.junit5basics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AssertJExampleTest {
    private AssertionsExample assertionsExample;

    @BeforeEach
    void setUp() {
        assertionsExample = new AssertionsExample();
    }

    @Test
    void someText() {
        assertThat(assertionsExample.someText()).isEqualTo("Some text.");
    }
}