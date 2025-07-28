package net.projectsync.junits.spy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

// https://labex.io/tutorials/java-spy-in-mockito-117989
public class BasicSpyExample1 {

    @Test
    public void basicSpyExample() {

        // Create a list and spy on it
        List<String> realList = new ArrayList<>();
        List<String> spyList = Mockito.spy(realList);

        // Using the spy to add an item (the real method is called)
        spyList.add("Hello");

        // Verify the interaction happened
        Mockito.verify(spyList).add("Hello");

        // The real method was called, so the list actually has the item
        assertEquals(1, spyList.size());
        assertEquals("Hello", spyList.get(0));

        System.out.println("Basic Spy Example - Spy List Content: " + spyList);
        System.out.println("Basic Spy Example - Spy List Size: " + spyList.size());
    }
}
