package ru.iisuslik.test;

import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Injector {

    private static HashMap<String, Integer> classesNumbers;
    private static HashMap<String, Class<?>> classesMap;
    private static List<Class<?>> classes;

    private static boolean [][] g;
    private static int [] used;
    private static Object [] realized;


    private static int n;
    private static int root;


    private static void buildGraf(int i) throws ImplementationNotFoundException {
        Class<?> clazz = classes.get(i);
        Constructor constr = clazz.getDeclaredConstructors()[0];
        System.out.println(constr.getGenericParameterTypes().length);
        System.out.println(clazz.getName());
        for(Type t: constr.getGenericParameterTypes()) {
            if(classesNumbers.containsKey(t.getTypeName())) {
                int classNumber = classesNumbers.get(t.getTypeName());
                if(classes.get(classNumber).isInterface()){
                    g[i][findInterfaces(classes.get(classNumber).getName())] = true;
                    continue;
                }
                g[i][classNumber] = true;
                buildGraf(classNumber);
            }
            else {
                throw new ImplementationNotFoundException();
            }
        }
    }

    private static int findInterfaces(String name) throws ImplementationNotFoundException {
        for(int i = 0; i < n; i++) {
            Class<?> clazz = classes.get(i);
            for(Class<?> interf: clazz.getInterfaces()) {
                if(interf.getName().equals(name)){
                    return i;
                }
            }
        }
        throw new ImplementationNotFoundException();
    }


    private static Object dfs(int v) throws InjectionCycleException, ImplementationNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        used[v] = 1;
        Class<?> now = classes.get(v);
        Constructor constr = now.getConstructors()[0];
        int size = constr.getGenericParameterTypes().length;
        //System.out.println(size);
        //System.out.println(now.getConstructors().length);
        //System.out.println(now.getName());
        if (size == 0)
            return now.newInstance();
        HashMap<String, Object> r = new HashMap<>();
        for(int i = 0; i < n; i++) {
            if(g[v][i]) {
                if(used[i] == 1)
                    throw new InjectionCycleException();
                if(realized[i] != null) {
                    r.put(classes.get(i).getName(), realized[i]);
                } else {
                    r.put(classes.get(i).getName(), dfs(i));
                }
            }
        }
        Object [] args = new Object[size];
        for(int i = 0; i < size; i++) {
            String argName = constr.getGenericParameterTypes()[i].getTypeName();
            if(!r.containsKey(argName)) {
                throw new ImplementationNotFoundException();
            }
            args[i] = r.get(argName);
        }
        used[v] = 2;
        Object res = constr.newInstance(args);
        realized[v] = res;
        return res;
    }

    public static Object initialize(String rootClassName, List<Class<?>> classesArg)
            throws ImplementationNotFoundException, AmbiguousImplementationException, InjectionCycleException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        classes = classesArg;
        classesMap = new HashMap<>();
        classesNumbers = new HashMap<>();
        classes.add(Class.forName(rootClassName));
        n = classes.size();
        realized = new Object[n];
        g = new boolean[n][n];
        used = new int[n];
        int rootCount = 0;
        for (int i = 0; i < n; i++) {
            Class<?> clazz = classes.get(i);
            classesNumbers.put(clazz.getName(), i);
            classesMap.put(clazz.getName(), clazz);
            if (clazz.getName().equals(rootClassName)) {
                rootCount++;
                root = i;
            }
        }
        if (rootCount == 0) {
            throw new ImplementationNotFoundException();
        }
        if (rootCount > 1) {
            throw new AmbiguousImplementationException();
        }
        for(int i = 0; i < n; i++) {
            buildGraf(i);
        }



        return dfs(root);
    }
}


