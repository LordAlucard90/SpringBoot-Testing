package springboottesting.junit5basics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssertionsExampleTest {
    private AssertionsExample assertionsExample;

    @BeforeEach
    void setUp() {
        assertionsExample = new AssertionsExample();
    }

    @Test
    void someText() {
        assertEquals("Some text.", assertionsExample.someText());
        assertTrue("Some text.".equals(assertionsExample.someText()));
    }

    @Test
    void someTextFails() {
        String actual = "Some text.";
        String actualWrong = "Not Some text";
        assertEquals(
                actual,
//                actualWrong,
                assertionsExample.someText(),
                "This has to fail"
        );

        // the first one must succeed to reach this one
        assertEquals(
//                actual,
                actualWrong,
                assertionsExample.someText(),
                () -> "Expensive way to say: " + "This has to fail. "
        );
    }

}