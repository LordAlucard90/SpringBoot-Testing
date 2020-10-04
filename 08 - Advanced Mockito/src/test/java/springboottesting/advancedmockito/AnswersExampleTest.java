package springboottesting.advancedmockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AnswersExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Captor
    private ArgumentCaptor<TestedClass.ArgumentClass> argumentClassCaptorAnnotated;

    // mock data
    private TestedClass.ArgumentClass argumentClass;

    @BeforeEach
    void setUp() {
        // given
        argumentClass = new TestedClass.ArgumentClass();

        given(injectedClassMock.mockedMethod(argumentClassCaptorAnnotated.capture()))
                .willAnswer(invocation -> {
                    TestedClass.ArgumentClass argument = invocation.getArgument(0);
                    if (argument.param == null) {
                        throw new RuntimeException("Must be not null");
                    }
                    return argument.param;
                });
    }

    @Test
    void answerBDDTest() {
        // given
        argumentClass.param = "param";

        // when
        testedClass.testMethod(argumentClass);

        // then
        TestedClass.ArgumentClass captured = argumentClassCaptorAnnotated.getValue();
        assertEquals(argumentClass, captured);
    }

    @Test
    void answerThrowsBDDTest() {
        // given
        argumentClass.param = null;

        // then
        assertThrows(
                RuntimeException.class,
                // when
                () -> testedClass.testMethod(argumentClass)
        );
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
