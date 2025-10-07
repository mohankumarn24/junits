package net.projectsync.junits.spy;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

// https://labex.io/tutorials/java-spy-in-mockito-117989
@ExtendWith(MockitoExtension.class)
public class SpyWithAnnotation {

    // Declare a spy using annotation
    @Spy
    ArrayList<Integer> annotationSpyList = new ArrayList<>();

    /*
    // Since you are already using @ExtendWith(MockitoExtension.class), you do NOT need this at all.
    // The extension automatically initializes all @Mock and @Spy annotations
    @Before("testSpyWithAnnotation")
    public void initSpies() {
        // Initialize mocks and spies with annotations
        MockitoAnnotations.initMocks(this);
    }
    */

    @Test
    public void testSpyWithAnnotation() {
        // Adding elements to the spy
        annotationSpyList.add(5);
        annotationSpyList.add(10);
        annotationSpyList.add(15);

        // Verifying interactions
        Mockito.verify(annotationSpyList).add(5);
        Mockito.verify(annotationSpyList).add(10);
        Mockito.verify(annotationSpyList).add(15);

        // Verifying that elements were actually added to the list
        assertEquals(3, annotationSpyList.size());

        System.out.println("Annotation Spy Example - Spy List Content: " + annotationSpyList);
        System.out.println("Annotation Spy Example - Spy List Size: " + annotationSpyList.size());
    }
}
