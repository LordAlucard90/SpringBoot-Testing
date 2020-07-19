package springboottesting.advancedjunit5;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface BeforeAllInterfaceTest {
    @BeforeAll
    default void beforeAll() {
        System.out.println("Default before all method.");
    }
}
