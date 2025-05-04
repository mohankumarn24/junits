package net.projectsync.junits.spy;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.NotAMockException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

// https://labex.io/tutorials/java-spy-in-mockito-117989
public class HandleNotAMockException {

    @Test
    public void testNotAMockException() {
        try {
            // Create a regular ArrayList (not a mock or spy)
            ArrayList<Integer> regularList = new ArrayList<>();
            regularList.add(5);

            // Try to verify an interaction on a regular object
            Mockito.verify(regularList).add(5);

            fail("Expected NotAMockException was not thrown");
        } catch (NotAMockException e) {
            // Expected exception
            System.out.println("NotAMockException Example - Caught expected exception: " + e.getMessage());
            assertTrue(e.getMessage().contains("Argument passed to verify() is of type ArrayList and is not a mock!"));
        }
    }
}
