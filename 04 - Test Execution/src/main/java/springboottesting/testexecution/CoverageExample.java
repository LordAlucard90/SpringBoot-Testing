package springboottesting.testexecution;


public class CoverageExample {
    public static String greet() {
        return "Hello"; // not covered
    }

    public static int answer() {
        return 42; // covered
    }
}
