package springboottesting.mokitobasics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArgumentMatcherExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void valueIsForwarded() {
        int baseValue = 42;

        testedClass.forwardAnIntValue(baseValue);

        verify(injectedClassMock, times(1)).processAValue(anyInt());
        verify(injectedClassMock, never()).processAValue(anyFloat());
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void forwardAnIntValue(int value) {
            injectedClass.processAValue(value * 2);
        }
    }

    private static class InjectedClass {
        public void processAValue(int value) {
            // do something here'
        }

        public void processAValue(float value) {
            // do something here'
        }
    }
}
