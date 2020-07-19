package springboottesting.advancedjunit5;

import org.junit.jupiter.api.Test;

public class BeforeAllInterfaceImpTest implements BeforeAllInterfaceTest {
    @Test
    void aTest1() {
        System.out.println("this is aTest1");
    }

    @Test
    void aTest2() {
        System.out.println("this is aTest2");
    }
}
