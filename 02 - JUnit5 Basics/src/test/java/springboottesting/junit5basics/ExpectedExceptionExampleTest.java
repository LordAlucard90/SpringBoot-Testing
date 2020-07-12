package springboottesting.junit5basics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpectedExceptionExampleTest {
    ExpectedExceptionExample expectedExceptionExample;

    @BeforeEach
    void setUp() {
        expectedExceptionExample = new ExpectedExceptionExample();
    }

    @Test
    void someMethod() {
        assertThrows(
                RuntimeException.class,
                () -> expectedExceptionExample.someMethod()
        );
    }
}