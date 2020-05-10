package springboottesting.junit5basics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

class ConditionalTestsExampleTest {
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void onWindows() {
        System.out.println("onWindows");
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void onMac() {
        System.out.println("onMac");
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void onLinux() {
        System.out.println("onLinux");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void onJava8() {
        System.out.println("onJava8");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void onJava11() {
        System.out.println("onJava11");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "pippo")
    void onUserPippo() {
        System.out.println("onUserPippo");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "pluto")
    void onUserPluto() {
        System.out.println("onUserPluto");
    }
}