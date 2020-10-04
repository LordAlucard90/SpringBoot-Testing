package springboottesting.advancedmockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class VerifyInteractionsOrderExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void orderBDDTest() {
        // given
        willDoNothing().given(injectedClassMock).first();
        willDoNothing().given(injectedClassMock).second();

        InOrder inOrder = Mockito.inOrder(injectedClassMock);

        // when
        testedClass.testMethod();

        // then
        inOrder.verify(injectedClassMock).first();
        inOrder.verify(injectedClassMock).second();
    }

    private static class TestedClass {
        private InjectedClass injectedClass;

        public TestedClass(InjectedClass injectedClass) {
            this.injectedClass = injectedClass;
        }

        public void testMethod() {
            injectedClass.first();
            injectedClass.second();
        }
    }

    private static class InjectedClass {
        public void first() { }
        public void second() { }
    }
}
