package springboottesting.advancedjunit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

public class ParametrizedExampleTest {
    @DisplayName("Value Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @ValueSource(strings = {"First", "Second", "Third"})
    void valueSourceTest(String val) {
        System.out.println("Using val: " + val);
    }

    @DisplayName("Enum Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @EnumSource(EnumTestParameter.class)
    void enumSourceTest(EnumTestParameter enumVal) {
        System.out.println("Using enum value: " + enumVal.name());
    }

    @DisplayName("Csv Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @CsvSource({
            "First, 1",
            "Second, 2",
            "Third, 3",
    })
    void csvSourceTest(String stringVal, int intVal) {
        System.out.println("Using csv values: (" + stringVal + ", " + intVal + ")");
    }

    @DisplayName("Csv File Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @CsvFileSource(
            resources = "/input.csv",
            numLinesToSkip = 1,
            delimiter = ';'
    )
    void csvFileSourceTest(String stringVal, int intVal) {
        System.out.println("Using csv file values: (" + stringVal + ", " + intVal + ")");
    }

    @DisplayName("Method Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @MethodSource(value = "getArgs")
    void methodSourceTest(String stringVal, int intVal) {
        System.out.println("Using method values: (" + stringVal + ", " + intVal + ")");
    }

    static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of("First", 1),
                Arguments.of("Second", 2),
                Arguments.of("Third", 3)
        );
    }

    @DisplayName("Arguments Source Parametrized Test")
    @ParameterizedTest(name = "{displayName}: [{index}] {arguments}")
    @ArgumentsSource(value = CustomArgumentsProvider.class)
    void argumentsSourceTest(String stringVal, int intVal) {
        System.out.println("Using class values: (" + stringVal + ", " + intVal + ")");
    }
}
