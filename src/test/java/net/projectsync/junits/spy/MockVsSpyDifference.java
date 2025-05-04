package net.projectsync.junits.spy;

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
        ArrayList<Integer> spyList = Mockito.spy(new ArrayList<>());

        // Add elements to both
        mockList.add(1);
        spyList.add(1);

        // Verify interactions (both will pass)
        Mockito.verify(mockList).add(1);
        Mockito.verify(spyList).add(1);

        // Check the size of both lists
        System.out.println("Mock vs Spy - Mock List Size: " + mockList.size());
        System.out.println("Mock vs Spy - Spy List Size: " + spyList.size());

        // Mock returns default values (0 for size) unless stubbed
        assertEquals(0, mockList.size());

        // Spy uses real method implementation, so size is 1
        assertEquals(1, spyList.size());

        // Stub both to return size 100
        Mockito.when(mockList.size()).thenReturn(100);
        Mockito.when(spyList.size()).thenReturn(100);

        // Both should now return 100 for size
        assertEquals(100, mockList.size());
        assertEquals(100, spyList.size());

        System.out.println("Mock vs Spy (After Stubbing) - Mock List Size: " + mockList.size());
        System.out.println("Mock vs Spy (After Stubbing) - Spy List Size: " + spyList.size());
    }
}
