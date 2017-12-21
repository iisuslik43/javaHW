package ru.iisuslik.reflector;

public class ClassExtendsSimple extends SimpleClass {
    int c = 43;
    @Override
    public void go(){
        c--;
    }
}
