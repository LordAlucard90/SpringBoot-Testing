package springboottesting.advancedjunit5;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("example")
class TagExampleTest {
    @Test
    void tagTest() {
        System.out.println("this is a teg test");
    }
}