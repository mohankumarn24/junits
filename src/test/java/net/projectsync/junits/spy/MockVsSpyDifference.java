package net.projectsync.junits.spy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

// https://labex.io/tutorials/java-spy-in-mockito-117989
public class MockVsSpyDifference {

    @Test
    public void testMockVsSpyDifference() {

        // Create a mock and a spy of ArrayList
        ArrayList<Integer> mockList = Mockito.mock(ArrayList.class);
        ArrayList<Integer> spyList  = Mockito.spy(new ArrayList<>());

        // Add elements to both
        mockList.add(1);
        spyList.add(1);

        // Verify interactions (both will pass)
        Mockito.verify(mockList, Mockito.times(1)).add(1);
        Mockito.verify(spyList,  Mockito.times(1)).add(1);

        Assertions.assertEquals(0, mockList.size());   // Mock returns default values (0 for size) unless stubbed
        Assertions.assertEquals(1, spyList.size());    // Spy uses real method implementation, so size is 1

        // Stub both to return size 100
        Mockito.when(mockList.size()).thenReturn(100);
        Mockito.when(spyList.size()).thenReturn(100);

        // Both should now return 100 for size
        Assertions.assertEquals(100, mockList.size());
        Assertions.assertEquals(100, spyList.size());
    }
}
