package springboottesting.mockitobdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BDDVerificationExampleTest {
    @Mock
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Test
    void evenTest() {
        // given
        given(injectedClassMock.generateAnIntValue()).willReturn(2);

        // when
        boolean isEven = testedClass.isEven();

        // then
        assertTrue(isEven);
        then(injectedClassMock).should(times(1)).generateAnIntValue();
        then(injectedClassMock).shouldHaveNoMoreInteractions();
    }

    @Test
    void oddTest() {
        // given
        given(injectedClassMock.generateAnIntValue()).willReturn(1);

        // when
        testedClass.isEven();

        // then
        then(injectedClassMock).should(times(1)).generateAnIntValue();
        then(injectedClassMock).shouldHaveNoMoreInteractions();
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
