package springboottesting.advancedjunit5.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

public class TestRepetitionWithInterfaceImpl implements TestRepetitionWithInterface {
    @RepeatedTest(
            value = 3,
            name = "{displayName}"
    )
    @DisplayName("Repeated Test Exercise")
    void exerciseTest() {
        System.out.println("Exercise Test.");
    }
}
