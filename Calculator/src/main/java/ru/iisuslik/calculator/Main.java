package ru.iisuslik.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Console app that realizes simple calculator with +,-,*,(,)
 */
public class Main {

    private static boolean isInt(@NotNull String s) {
        for (char c : s.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<String> split(@NotNull String s) {
        StringBuilder sb = null;
        boolean numb = false;
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                if (!numb) {
                    sb = new StringBuilder();
                    numb = true;
                }
                sb.append(c);
            } else {
                if (sb != null) {
                    res.add(sb.toString());
                    sb = null;
                    numb = false;
                }
                String t = "" + c;
                res.add(t);
            }
        }
        if (sb != null) {
            res.add(sb.toString());
        }
        return res;
    }

    private static ArrayList<String> toPoland(@NotNull ArrayList<String> expr) {
        ArrayList<String> res = new ArrayList<>();
        Stack<String> st = new Stack<>();
        expr.add("#");
        int i = 0;
        while (true) {
            String now = expr.get(i);
            if (isInt(now)) {
                res.add(now);
                i++;
            } else if (now.equals("+") || now.equals("-")) {
                if (st.size() == 0 || st.top().equals("(")) {
                    st.push(now);
                    i++;
                } else if (st.top().equals("-") || st.top().equals("+") || st.top().equals("*") || st.top().equals("/")) {
                    res.add(st.pop());
                }
            } else if (now.equals("*") || now.equals("/")) {
                if (st.size() == 0 || st.top().equals("(") || st.top().equals("+") || st.top().equals("-")) {
                    st.push(now);
                    i++;
                } else if (st.top().equals("/") || st.top().equals("*")) {
                    res.add(st.pop());
                }
            } else if (now.equals("(")) {
                st.push(now);
                i++;
            } else if (now.equals(")")) {
                if (st.top().equals("-") || st.top().equals("+") || st.top().equals("*") || st.top().equals("/")) {
                    res.add(st.pop());
                } else if (st.top().equals("(")) {
                    st.pop();
                    i++;
                }
            } else if (now.equals("#")) {
                while (st.size() != 0) {
                    res.add(st.pop());
                }
                return res;
            }
        }
    }

    /**
     * Calculates arithmetic expression that given in args
     *
     * @param args arithmetic expression thar will be calculated
     */
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg);
        }
        String s = sb.toString();
        ArrayList<String> expr = split(s);
        System.out.println(expr);
        ArrayList<String> poland = toPoland(expr);
        System.out.println(poland);
        Calculator calc = new Calculator(new Stack<>());
        int result = calc.calculate(poland);
        System.out.print(result);
    }
}
