package springboottesting.advancedjunit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExtensionExample.class)
public class ExtensionExampleTest {
    @Test
    void aTest() {
        System.out.println("Extension Test.");
    }
}
