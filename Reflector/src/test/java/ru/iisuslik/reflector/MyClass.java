package ru.iisuslik.reflector;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class MyClass {
    int a;
    public final char b = '2';
    private String s;
    private MyClass() {}
    protected MyClass(int a, char b) {
        this.a = b;
        s = s + b;
    }
    public MyClass(String s) {
        this.s = s;
    }

    void print(String s){
        System.out.println(s);
    }
    public String getString() throws IOException, ArithmeticException{
        return "LOL";
    }
    private int sum(int a, int b) {
        return a + b;
    }

    protected double returnDouble(Collection<?> c) {
        return 0;
    }

    boolean justFalse() {
        return false;
    }
    <T> ArrayList<T> arr(Collection<? extends T> col) {
        return null;
    }
    <T> T kek() {
        return null;
    }
}
