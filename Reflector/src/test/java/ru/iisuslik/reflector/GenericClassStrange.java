package ru.iisuslik.reflector;

import java.util.Collection;

public class GenericClassStrange<T> {
    T data;
    public <E extends Object> void b(Collection<E> c) {

    }
}
