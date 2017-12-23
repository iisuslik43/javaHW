package ru.iisuslik.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Calculates arithmetic expression in reverse Polish notation
 */
public class Calculator {

    private static boolean isInt(@NotNull String s) {
        for (char c : s.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


    private Stack<Integer> stack;

    /**
     * New Calculator that uses stack you give in arg
     *
     * @param st stack that calculator will use
     */
    public Calculator(@NotNull Stack<Integer> st) {
        stack = st;
    }

    /**
     * Calculates arithmetic expression in reverse Polish notation
     *
     * @param poland ArrayList of numbers and operations in reverse Polish notation
     * @return calculation result
     */
    public int calculate(@NotNull ArrayList<String> poland) {
        stack.clear();
        for (String now : poland) {
            if (isInt(now)) {
                stack.push(Integer.parseInt(now));
            }
            else {
                int b = stack.pop();
                int a = stack.pop();
                switch (now) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                }
            }
        }
        return stack.pop();
    }
}
