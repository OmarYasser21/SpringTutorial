package com.example.tutorial.UnitTesting;

import UnitTesting.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CalculatorService.class)
public class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    public void testAddHappyScenario() {
        double result = calculatorService.add(1.0, 2.0);
        assertEquals(3.0, result);
    }

    @Test
    public void testSubtractHappyScenario() {
        double result = calculatorService.subtract(3.0, 2.0);
        assertEquals(1.0, result);
    }

    @Test
    public void testMultiplyHappyScenario() {
        double result = calculatorService.multiply(4.0, 5.0);
        assertEquals(20.0, result);
    }

    @Test
    public void testDivideHappyScenario() {
        double result = calculatorService.divide(10.0, 2.0);
        assertEquals(5.0, result);
    }
}
