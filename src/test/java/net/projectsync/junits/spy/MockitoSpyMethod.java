package net.projectsync.junits.spy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

// https://labex.io/tutorials/java-spy-in-mockito-117989
@ExtendWith(MockitoExtension.class)
public class MockitoSpyMethod {

    // Using Mockito.spy() Method
    @Test
    public void testSpyWithMockitoSpyMethod() {

        // Create an ArrayList and a spy of it
        ArrayList<Integer> realList = new ArrayList<>();
        ArrayList<Integer> spyList = Mockito.spy(realList);

        // Adding elements to the spy (real methods are called)
        spyList.add(5);
        spyList.add(10);
        spyList.add(15);

        // Verifying interactions
        Mockito.verify(spyList, Mockito.times(1)).add(5);
        Mockito.verify(spyList, Mockito.times(1)).add(10);
        Mockito.verify(spyList, Mockito.times(1)).add(15);

        // Verifying that elements were actually added to the list
        Assertions.assertEquals(3, spyList.size());

        System.out.println("Spy Method Example - Spy List Content: " + spyList);
        System.out.println("Spy Method Example - Spy List Size: " + spyList.size());
    }
}
