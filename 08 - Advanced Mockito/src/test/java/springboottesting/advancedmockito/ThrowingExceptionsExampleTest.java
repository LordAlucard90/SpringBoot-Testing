package springboottesting.advancedmockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ThrowingExceptionsExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void throwsAnExceptionTest() {
        doThrow(new RuntimeException("Test")).when(injectedClassMock).mockedMethod();

        assertThrows(
                RuntimeException.class,
                () -> testedClass.testMethod()
        );
    }

    @Test
    void throwsAnExceptionBDDTest() {
        // given
        willThrow(new RuntimeException("Test")).given(injectedClassMock).mockedMethod();

        // then
        assertThrows(
                RuntimeException.class,
                // when
                () -> testedClass.testMethod()
        );
        then(injectedClassMock).should().mockedMethod();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod() {
            injectedClass.mockedMethod();
        }
    }

    private static class InjectedClass {
        public void mockedMethod() { }
    }
}
