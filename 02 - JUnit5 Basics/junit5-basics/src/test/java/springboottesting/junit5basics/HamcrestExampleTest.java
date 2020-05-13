package springboottesting.junit5basics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class HamcrestExampleTest {
    private AssertionsExample assertionsExample;

    @BeforeEach
    void setUp() {
        assertionsExample = new AssertionsExample();
    }

    @Test
    void someText() {
        assertThat(assertionsExample.someText(), is("Some text."));
    }
}