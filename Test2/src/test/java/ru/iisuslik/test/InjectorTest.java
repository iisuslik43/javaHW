package ru.iisuslik.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class InjectorTest {
    @Test
    public void initializeTest() throws Exception {
        System.out.println(String.class.getName());
        System.out.println(String.class.getSimpleName());
        System.out.println(String.class.getCanonicalName());
    }

    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        ArrayList<Class<?>> empty = new ArrayList<>();
        Object object = Injector.initialize(ClassWithoutDependencies.class.getName(), empty);
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        ArrayList<Class<?>> list = new ArrayList<>();
        list.add(ClassWithoutDependencies.class);
        Object object = Injector.initialize(
                ClassWithOneClassDependency.class.getName(), list
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        ArrayList<Class<?>> list = new ArrayList<>();
        list.add(InterfaceImpl.class);
        list.add(Interface.class);
        Object object = Injector.initialize(
                ClassWithOneInterfaceDependency.class.getName(), list
        );
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }














    public static class ClassWithOneClassDependency {

        public final ClassWithoutDependencies dependency;

        public ClassWithOneClassDependency(ClassWithoutDependencies dependency) {
            this.dependency = dependency;
        }
    }

    public static class ClassWithOneInterfaceDependency {

        public final Interface dependency;

        public ClassWithOneInterfaceDependency(Interface dependency) {
            this.dependency = dependency;
        }
    }


    public static class ClassWithoutDependencies {
        public ClassWithoutDependencies(){}
    }
    public static class InterfaceImpl implements Interface {
    }
    public static interface Interface {
    }

}