package springboottesting.introductiontojunit5;

import org.junit.jupiter.api.*;

class GreetingTest {
    private Greeting greeting;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All..");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Before Each..");
        greeting = new Greeting();
    }

    @Test
    void helloWorld() {
        System.out.println(greeting.helloWorld());
    }

    @Test
    void testHelloWorld() {
        greeting = new Greeting();
        System.out.println(greeting.helloWorld("World"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each..");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All..");
    }
}
