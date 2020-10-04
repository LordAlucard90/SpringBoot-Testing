package springboottesting.mockitobasics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReturningValuesFormMocksExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void evenTest() {
        when(injectedClassMock.generateAnIntValue()).thenReturn(2);

        assertTrue(testedClass.isEven());
    }

    @Test
    void oddTest() {
        when(injectedClassMock.generateAnIntValue()).thenReturn(1);

        assertFalse(testedClass.isEven());
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public boolean isEven() {
            return injectedClass.generateAnIntValue() % 2 == 0;
        }
    }

    private static class InjectedClass {
        public int generateAnIntValue() {
            return 0;
        }
    }
}
