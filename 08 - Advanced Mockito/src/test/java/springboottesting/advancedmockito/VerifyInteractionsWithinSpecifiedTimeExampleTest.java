package springboottesting.advancedmockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.timeout;

@ExtendWith(MockitoExtension.class)
class VerifyInteractionsWithinSpecifiedTimeExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void interactionsWithinSpecifiedTimeBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).someMethod();

        // when
        testedClass.testMethod();

        // then
        then(injectedClassMock).should(timeout(100)).someMethod();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod() {
            injectedClass.someMethod();
        }
    }

    private static class InjectedClass {
        public void someMethod() { }
    }
}
