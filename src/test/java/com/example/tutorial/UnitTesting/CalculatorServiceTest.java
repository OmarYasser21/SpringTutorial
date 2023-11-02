package com.example.tutorial.UnitTesting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    public void testAddHappyScenario() {
        double result = calculatorService.add(1.0, 2.0);
        assertEquals(3.0, result);
    }

    @Test
    public void testAddZeros() {
        assertThrows(ArithmeticException.class, () -> calculatorService.add(0.0, 3.0));
        assertThrows(ArithmeticException.class, () -> calculatorService.add(3.0, 0.0));
    }

    @Test
    public void testSubtractHappyScenario() {
        double result = calculatorService.subtract(3.0, 2.0);
        assertEquals(1.0, result);
    }

    @Test
    public void testSubtractNegativeResult() {
        assertThrows(ArithmeticException.class, () ->  calculatorService.subtract(3.0, 5.0));
    }

    @Test
    public void testMultiplyHappyScenario() {
        double result = calculatorService.multiply(4.0, 5.0);
        assertEquals(20.0, result);
    }

    @Test
    public void testMultiplyZeros() {
        assertThrows(ArithmeticException.class, () -> calculatorService.multiply(0.0, 3.0));
        assertThrows(ArithmeticException.class, () -> calculatorService.multiply(3.0, 0.0));
    }


    @Test
    public void testDivideHappyScenario() {
        double result = calculatorService.divide(10.0, 2.0);
        assertEquals(5.0, result);
    }

    @Test
    public void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculatorService.divide(4.0, 0.0));
    }

}
