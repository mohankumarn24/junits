package net.projectsync.junits.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    public Calculator calculator;

    @BeforeEach
    public void beforeAll() {
        calculator = new Calculator();
    }

    // test private method using reflection
    @Test
    public void testPrivateMethodUpperCase() {
        try  {
            Calculator calc = new Calculator();

            // Get the private method by name and parameter types
            Method privateMethod = Calculator.class.getDeclaredMethod("privateMethod", String.class);

            // Make the private method accessible
            privateMethod.setAccessible(true);

            // access private variable
            ReflectionTestUtils.setField(calc, "isUpperCase", true);
            System.out.println("Flag:" + ReflectionTestUtils.getField(calc, "isUpperCase"));

            // Invoke the private method
            String countryCode = (String) privateMethod.invoke(calc, "INDIA");

            Assertions.assertTrue("IND".equals(countryCode));

        } catch (NoSuchMethodException e) {
            System.err.println("Method not found: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Cannot access method: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.err.println("Method threw an exception: " + e.getCause());
        }
    }

    // test private method using reflection
    @Test
    public void testPrivateMethodLowerCase() {
        try  {
            Calculator calc = new Calculator();

            // Get the private method by name and parameter types
            Method privateMethod = Calculator.class.getDeclaredMethod("privateMethod", String.class);

            // Make the private method accessible
            privateMethod.setAccessible(true);

            // access private variable
            ReflectionTestUtils.setField(calc, "isUpperCase", false);
            System.out.println("Flag:" + ReflectionTestUtils.getField(calc, "isUpperCase"));

            // Invoke the private method
            String countryCode = (String) privateMethod.invoke(calc, "INDIA");

            Assertions.assertTrue("ind".equals(countryCode));

        } catch (NoSuchMethodException e) {
            System.err.println("Method not found: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Cannot access method: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.err.println("Method threw an exception: " + e.getCause());
        }
    }

    /**
     * Cannot mock static method. In this scenario, call directly
     */
    @Test
    public void testStaticMethod() {
        /*
        try (MockedStatic<Calculator> mockedStatic = Mockito.mockStatic(Calculator.class)) {
            mockedStatic.when(() -> Calculator.getString()).thenReturn("Mocked String");
            String result = Calculator.getString();
            Assertions.assertTrue("Mocked String".equals(result));
        }
        */

        Assertions.assertTrue(Calculator.getString().equals("Original string"));
    }

    @Test
    public void testAdd() {
        assertEquals(8, calculator.add(6, 2));
    }

    @Test
    public void testSubraction() {
        assertEquals(4, calculator.subtract(6, 2));
    }

    @Test
    public void testMultiply() {
        assertEquals(12, calculator.multiply(6, 2));
    }

    @Test
    public void testDivision() {
        assertEquals(3, calculator.division(6, 2));
    }

    @Test
    public void testDivisionException() {
        ArithmeticException arithmeticException = Assertions.assertThrows(ArithmeticException.class, () -> {
            calculator.division(6, 0);
        });

        Assertions.assertEquals("/ by zero", arithmeticException.getMessage());
    }
}