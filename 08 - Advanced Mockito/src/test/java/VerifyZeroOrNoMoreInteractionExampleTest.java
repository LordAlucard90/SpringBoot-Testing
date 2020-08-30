import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class VerifyZeroOrNoMoreInteractionExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void noMoreInteractionsBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).positive();

        // when
        testedClass.testMethod(true);

        // then
        then(injectedClassMock).should().positive();
        verifyNoMoreInteractions(injectedClassMock);
    }

    @Test
    void noInteractionsBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).positive();

        // when
        testedClass.testMethod(false);

        // then
        verifyNoInteractions(injectedClassMock);
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod(boolean condition) {
            if (condition) {
                injectedClass.positive();
            }
            // do nothing
        }
    }

    private static class InjectedClass {
        public void positive() { }
    }
}
