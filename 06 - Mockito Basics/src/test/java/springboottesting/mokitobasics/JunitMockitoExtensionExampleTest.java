package springboottesting.mokitobasics;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JunitMockitoExtensionExampleTest {
    @Mock
    Map<String, Object> mapMock;

    @Test
    void mapMockTest() {
        assertEquals(0, mapMock.size());
    }
}
