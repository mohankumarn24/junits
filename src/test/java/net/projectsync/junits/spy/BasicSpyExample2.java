package net.projectsync.junits.spy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.mockito.Mockito.*;

public class BasicSpyExample2 {

    @Test
    void testVerifyWithMock() {

        // Create a mock object
        List<String> mockedList = Mockito.mock(List.class);

        // Use the mock
        mockedList.add("apple");
        mockedList.clear();

        // Verify interactions
        Mockito.verify(mockedList, Mockito.times(1)).add("apple");
        Mockito.verify(mockedList, Mockito.times(1)).clear();
    }

    @Test
    void testSpy() {

        // Create a real list and spy on it
        List<String> realList = new java.util.ArrayList<>();
        List<String> spyList = Mockito.spy(realList);

        // Use the spy object
        spyList.add("banana");
        spyList.add("cherry");

        // The real methods are called
        System.out.println(spyList.get(0)); // Output: banana

        // Verify interactions
        Mockito.verify(spyList, Mockito.times(1)).add("banana");
        Mockito.verify(spyList, Mockito.times(1)).add("cherry");
    }
}
