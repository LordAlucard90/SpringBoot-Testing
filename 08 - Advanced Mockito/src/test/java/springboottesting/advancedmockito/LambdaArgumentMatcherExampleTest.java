package springboottesting.advancedmockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LambdaArgumentMatcherExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void argumentMatchBDDTest() {
        // given
        final String expectedParam = "expected";

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = expectedParam;

        given(injectedClassMock.mockedMethod(argThat(argument -> argument.param.equals(expectedParam)))).willReturn(expectedParam);

        // when
        var response = testedClass.testMethod(argumentClass);

        // then
        then(injectedClassMock).should().mockedMethod(any());
        assertEquals(expectedParam, response);
    }

    @Test
    void argumentDoesNotMatchBDDTest() {
        // given
        final String expectedParam = "expected";

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = "not" + expectedParam;

        given(injectedClassMock.mockedMethod(argThat(argument -> argument.param.equals(expectedParam)))).willReturn(expectedParam);

        // when
        var response = testedClass.testMethod(argumentClass);

        // then
        then(injectedClassMock).should().mockedMethod(any());
        assertNull(response);
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public String testMethod(ArgumentClass argumentClass) {
            return injectedClass.mockedMethod(argumentClass);
        }

        private static class ArgumentClass {
            private String param;
        }
    }

    private static class InjectedClass {
        public String mockedMethod(TestedClass.ArgumentClass argumentClass) { return ""; }
    }
}
