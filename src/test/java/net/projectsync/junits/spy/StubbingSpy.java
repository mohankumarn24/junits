package net.projectsync.junits.spy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

// https://labex.io/tutorials/java-spy-in-mockito-117989
public class StubbingSpy {

    @Test
    public void testStubbingSpy() {

        // Create a spy of ArrayList
        ArrayList<Integer> spyList = Mockito.spy(new ArrayList<>());

        // Add an element
        spyList.add(5);

        // Verify the element was added
        Mockito.verify(spyList).add(5);

        // By default, contains() should return true for element 5
        Assertions.assertTrue(spyList.contains(5));
        System.out.println("Stubbing Example - Default behavior: spyList.contains(5) = " + spyList.contains(5));

        // Stub the contains() method to always return false for the value 5
        Mockito.doReturn(false).when(spyList).contains(5);

        // Now contains() should return false, even though the element is in the list
        Assertions.assertFalse(spyList.contains(5));
        System.out.println("Stubbing Example - After stubbing: spyList.contains(5) = " + spyList.contains(5));

        // The element is still in the list (stubbing didn't change the list content)
        Assertions.assertEquals(1, spyList.size());
        Assertions.assertEquals(Integer.valueOf(5), spyList.get(0));
        System.out.println("Stubbing Example - Spy List Content: " + spyList);
    }
}
