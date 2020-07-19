package springboottesting.advancedjunit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

public class RepeatTestExampleTest {
    @RepeatedTest(
            value = 2,
            name = "{displayName} repetition {currentRepetition} of {totalRepetitions}"
    )
    @DisplayName("Repeated test")
    void repeatedTest() {
        System.out.println("Repeated test.");
    }

    @RepeatedTest(2)
    void repeatedTestWithInfo(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println(testInfo.getDisplayName() + "-> " + repetitionInfo.getCurrentRepetition());
    }
}
