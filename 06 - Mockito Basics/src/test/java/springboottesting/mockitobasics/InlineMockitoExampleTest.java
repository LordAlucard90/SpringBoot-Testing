package springboottesting.mockitobasics;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class InlineMockitoExampleTest {
    @Test
     void mapMockTest() {
        Map mapMock = mock(Map.class);
        assertEquals(0, mapMock.size());
    }
}
