package com.example.tutorial.UnitTesting;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double add(double a, double b) {
        if (a == 0 || b == 0) {
            throw new ArithmeticException("Adding zeros is not allowed");
        }
        return a + b;
    }

    public double subtract(double a, double b) {
        double result = a - b;
        if (result < 0) {
            throw new ArithmeticException("Result is negative, which is not allowed");
        }
        return result;
    }

    public double multiply(double a, double b) {
        if (a == 0 || b == 0) {
            throw new ArithmeticException("Multiplication by zero is not allowed");
        }
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }
}
