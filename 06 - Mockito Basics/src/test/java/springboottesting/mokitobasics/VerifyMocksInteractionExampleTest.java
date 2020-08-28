package springboottesting.mokitobasics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VerifyMocksInteractionExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void verifyMockInteractionTest() {
        testedClass.testMe();
        verify(injectedClassMock, times(1)).doSomething();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMe() {
            injectedClass.doSomething();
        }
    }

    private static class InjectedClass {
        public void doSomething() {
            // dummy code
        }
    }
}
