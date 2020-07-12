package springboottesting.junit5basics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupedAssertionsExampleTest {
    @Test
    void groupedAssertions() {
        GroupedAssertionsExample groupedAssertionsExample = new GroupedAssertionsExample();

        assertAll(
                "Testing initialization with grouped assertions",
                () -> assertEquals("string", groupedAssertionsExample.string),
                () -> assertTrue(groupedAssertionsExample.aBoolean),
                () -> assertEquals(42, groupedAssertionsExample.anInt)
        );
    }

    @Test
    void groupedAssertionsFails() {
        GroupedAssertionsExample groupedAssertionsExample = new GroupedAssertionsExample();
        String string = "string";
        String wrongString = "";
        int anInt = 42;
        int wrongInt = 0;

        assertAll(
                "Testing initialization with grouped assertions",
                () -> {
                    assertEquals(
                            string,
//                            wrongString, // uncomment to make it mail
                            groupedAssertionsExample.string,
                            "This test must fail." // uncomment to make it mail
                    );
                },
                () -> {
                    assertEquals(
                            anInt,
//                            wrongInt, // uncomment to make it mail
                            groupedAssertionsExample.anInt,
                            "This test must fail."
                    );
                },
                () -> assertTrue(groupedAssertionsExample.aBoolean)
        );
    }
}