import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SpiesExampleTest {
    @Spy
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void spyRealMethodBDDTest() {
        // given
        String test = "test";
        given(injectedClassMock.method(anyString())).willCallRealMethod();

        // when
        String response = testedClass.testMethod(test);

        // then
        then(injectedClassMock).should().method(anyString());
        assertEquals("_" + test + "_", response);
    }

    @Test
    void spyMockBDDTest() {
        // given
        String test = "test";
        given(injectedClassMock.method(anyString())).willReturn("something else");

        // when
        String response = testedClass.testMethod(test);

        // then
        then(injectedClassMock).should().method(anyString());
        assertNotEquals("_" + test + "_", response);
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public String testMethod(String string) {
            return injectedClass.method(string);
        }

        private static class ArgumentClass {
            private String param;
        }
    }

    private static class InjectedClass {
        public String method(String string) { return "_" + string + "_"; }
    }
}
