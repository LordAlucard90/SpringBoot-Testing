import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArgumentCaptureExampleTest {
    @Mock(lenient = true)
    private InjectedClass injectedClassMock;

    @InjectMocks
    private TestedClass testedClass;

    @Captor
    private ArgumentCaptor<TestedClass.ArgumentClass> argumentClassCaptorAnnotated;

    @Test
    void argumentCaptureBDDTest() {
        // given
        final String expectedParam = "param";
        final ArgumentCaptor<TestedClass.ArgumentClass> argumentClassCaptor = ArgumentCaptor.forClass(TestedClass.ArgumentClass.class);

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = expectedParam;

        given(injectedClassMock.mockedMethod(argumentClassCaptor.capture())).willReturn(expectedParam);

        // when
        testedClass.testMethod(argumentClass);

        // then
        TestedClass.ArgumentClass captured = argumentClassCaptor.getValue();
        assertEquals(argumentClass, captured);
    }

    @Test
    void argumentCaptureWithAnnotationBDDTest() {
        // given
        final String expectedParam = "param";

        TestedClass.ArgumentClass argumentClass = new TestedClass.ArgumentClass();
        argumentClass.param = expectedParam;

        given(injectedClassMock.mockedMethod(argumentClassCaptorAnnotated.capture())).willReturn(expectedParam);

        // when
        testedClass.testMethod(argumentClass);

        // then
        TestedClass.ArgumentClass captured = argumentClassCaptorAnnotated.getValue();
        assertEquals(argumentClass, captured);
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
