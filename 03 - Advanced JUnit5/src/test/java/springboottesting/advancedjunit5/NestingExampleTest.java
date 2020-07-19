package springboottesting.advancedjunit5;

import org.junit.jupiter.api.*;

@DisplayName("NestingExampleTest")
class NestingExampleTest {
    @BeforeEach
    void setUp() {
        System.out.println("Before Each in NestingExampleTest.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each in NestingExampleTest.");

    }

    @Test
    void test() {
        System.out.println("NestingExampleTest test.");
    }

    @Nested
    @DisplayName("NestedExample")
    class NestedExample {
        @BeforeEach
        void setUp() {
            System.out.println("Before Each in NestedExampleTest.");
        }

        @AfterEach
        void tearDown() {
            System.out.println("After Each in NestedExampleTest.");

        }

        @Test
        void test() {
            System.out.println("NestedExampleTest test.");
        }

        @Nested
        @DisplayName("NestedNestedExample")
        class NestedNestedExample {
            @BeforeEach
            void setUp() {
                System.out.println("Before Each in NestedNestedExample.");
            }

            @AfterEach
            void tearDown() {
                System.out.println("After Each in NestedNestedExample.");

            }

            @Test
            void test() {
                System.out.println("NestedNestedExample test.");
            }
        }
    }
}