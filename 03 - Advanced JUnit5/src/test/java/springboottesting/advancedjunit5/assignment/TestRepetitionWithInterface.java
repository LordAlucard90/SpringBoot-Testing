package springboottesting.advancedjunit5.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public interface TestRepetitionWithInterface {
    @BeforeEach
    default void setUp(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println("Running test: " + testInfo.getDisplayName());
        System.out.println("Iteration: " + repetitionInfo.getCurrentRepetition() + " of " + repetitionInfo.getTotalRepetitions());
    }
}
