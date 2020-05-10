package springboottesting.junit5basics;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

class TimeoutsExampleTest {
    @Test
    void duration() {
        assertTimeout(
                Duration.ofMillis(100),
                () -> {
                    Thread.sleep(100);
                    System.out.println("text");
                }
        );
    }
}