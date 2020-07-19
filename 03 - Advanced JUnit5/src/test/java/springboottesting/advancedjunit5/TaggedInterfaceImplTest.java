package springboottesting.advancedjunit5;

import org.junit.jupiter.api.Test;

class TaggedInterfaceImplTest implements TaggedInterfaceTest {
    @Test
    void tagInterfaceTest() {
        System.out.println("this is a teg interface test");
    }
}