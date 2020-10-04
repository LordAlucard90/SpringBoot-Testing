package springboottesting.mockitobdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BDDMockitoExampleTest {
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
    }

    @Test
    void oddTest() {
        // given
        given(injectedClassMock.generateAnIntValue()).willReturn(1);

        // when
        boolean isEven = testedClass.isEven();

        // then
        assertFalse(isEven);
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
