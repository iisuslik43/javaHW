package ru.iisuslik.reflector;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Objects;

/**
 * Class that realizes 2 functions:
 * 1) Compare 2 Class<?> by fields and methods
 * 2) Print Class<?>
 * Both functions have 2 realization: System.out.println(result) and return (String) result
 */
public class Reflector {
    /**
     * Prints class definition
     *
     * @param someClass class to print
     */
    public static void printStructure(@NotNull Class<?> someClass) {
        System.out.print(classToString(someClass, 0, false));
    }

    /**
     * This method returns correct definition of class with all methods, fields and inner classes
     *
     * @param someClass class to print
     * @return string that contains class
     */
    public static String classToString(@NotNull Class<?> someClass) {
        return classToString(someClass, 0, false);
    }

    /**
     * Compares fields and methods in 2 classes and print differences
     *
     * @param class1 first class to compare
     * @param class2 second class to compare
     */
    public static void diffClasses(@NotNull Class<?> class1, @NotNull Class<?> class2) {
        System.out.println(diffToString(class1, class2));
    }

    /**
     * Compares fields and methods in 2 classes and return string with differences
     *
     * @param class1 first class to compare
     * @param class2 second class to compare
     * @return string with comparing result
     */
    public static String diffToString(@NotNull Class<?> class1, @NotNull Class<?> class2) {
        return diffMethods(class1, class2) +
                diffFields(class1, class2);
    }

    private static Class<?> someClass;

    private static String addField(@NotNull Field f) {
        StringBuilder buf = new StringBuilder();
        buf.append(Modifier.toString(f.getModifiers()));
        if (f.getModifiers() != 0)
            buf.append(' ');
        buf.append(f.getGenericType().getTypeName()).append(' ');
        buf.append(f.getName());
        if (Modifier.isFinal(f.getModifiers())) {
            if (f.getType().isPrimitive()) {
                if (f.getType().getSimpleName().equals("boolean"))
                    buf.append(" = false");
                else
                    buf.append(" = 0");
            } else {
                buf.append(" = null");
            }
        }
        buf.append(";");
        return buf.toString();
    }

    private static String addAllFields(int depth) {
        StringBuilder buf = new StringBuilder();
        Field[] fields = someClass.getDeclaredFields();

        for (Field f : fields) {
            if (f.getName().equals("this$0"))
                continue;
            buf.append(addField(f));
            nextLine(depth, buf);
        }
        return buf.toString();
    }

    private static String addClass(int depth, boolean isInner) {
        StringBuilder buf = new StringBuilder();
        String classAll = someClass.toGenericString();
        int pos = classAll.indexOf(someClass.getName());

        if (!isInner) {
            classAll = classAll.substring(0, pos) + "SomeClass" + classAll.substring(pos + someClass.getName().length());
        } else {
            while (!Objects.equals(classAll, cutDollar(classAll))) {
                classAll = cutDollar(classAll);
            }
        }

        buf.append(classAll);
        if (someClass.getSuperclass() != null && !someClass.getSuperclass().getSimpleName().equals("Object")) {
            buf.append(" extends ").append(someClass.getSuperclass().getSimpleName());
        }
        if (someClass.getInterfaces().length != 0) {
            buf.append(" implements");
            int n = someClass.getInterfaces().length;
            for (int i = 0; i < n; i++) {
                buf.append(' ').append(someClass.getInterfaces()[i].getSimpleName());
                if (i != n - 1)
                    buf.append(',');
            }
        }
        buf.append(" {");
        nextLine(depth, buf);

        return buf.toString();
    }

    private static String addAllMethods(int depth) {
        StringBuilder buf = new StringBuilder();
        Method[] arr = someClass.getDeclaredMethods();
        for (int i = 0; i < arr.length; i++) {
            Method m = arr[i];
            buf.append(addMethod(m, depth));
            nextLine(depth - (i == (arr.length - 1) ? 1 : 0), buf);
        }
        return buf.toString();
    }

    private static String addMethod(@NotNull Method m, int depth) {
        StringBuilder buf = new StringBuilder();
        boolean hasArgs = m.getParameterCount() != 0;
        buf.append(cutMethodString(m.toGenericString(), hasArgs));
        if (Modifier.isAbstract(m.getModifiers())) {
            buf.append(';');
        } else {
            if (m.getReturnType().getSimpleName().equals("void")) {
                buf.append(" {");
                nextLine(depth + 1, buf);
                buf.append("return");
            } else {
                buf.append(" {");
                nextLine(depth + 1, buf);
                buf.append("return ");
                if (m.getReturnType().isPrimitive()) {
                    if (m.getReturnType().getSimpleName().equals("boolean"))
                        buf.append("false");
                    else
                        buf.append("0");
                } else {
                    buf.append("null");
                }
            }
            buf.append(";");
            nextLine(depth, buf);
            buf.append("}");
        }
        return buf.toString();
    }

    private static String addAllConstructors(int depth, boolean isInner) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < someClass.getDeclaredConstructors().length; i++) {
            Constructor cons = someClass.getDeclaredConstructors()[i];
            if (cons.getGenericParameterTypes().length > 0) {
                if (someClass.getEnclosingClass() != null)
                    if (cons.getGenericParameterTypes()[0].getTypeName().equals(someClass.getEnclosingClass().getName()))
                        continue;
            }
            buf.append(addConstructor(cons, depth, isInner));
        }
        return buf.toString();
    }

    private static String addConstructor(@NotNull Constructor cons, int depth, boolean isInner) {
        StringBuilder buf = new StringBuilder();
        boolean hasArgs = cons.getParameterCount() != 0;

        String constrString = cons.toGenericString();
        if (!isInner)
            constrString = changeName(cons.getName(), constrString);
        buf.append(cutConstructor(constrString, hasArgs));
        buf.append(" {}");
        nextLine(depth, buf);
        return buf.toString();
    }

    private static String changeName(@NotNull String consName, String cons) {
        int i = cons.indexOf(consName);
        return cons.substring(0, i) + "SomeClass" + cons.substring(i + consName.length(), cons.length());

    }

    private static void nextLine(int depth, @NotNull StringBuilder b) {
        b.append('\n');
        for (int i = 0; i < depth; i++) {
            b.append("    ");
        }
    }

    private static String cutMethodString(@NotNull String name, boolean hasArgs) {
        int ind = name.indexOf(someClass.getName());
        if (ind == -1)
            return name;
        int i = ind - 1;
        int j = ind + someClass.getName().length();

        String res = name.substring(0, i + 1) + name.substring(j + 1, name.length());
        if (hasArgs)
            return addArgsNames(res);
        return res;
    }

    private static String cutConstructor(@NotNull String name, boolean hasArgs) {
        int i = name.indexOf(someClass.getName());
        int j = name.indexOf(someClass.getSimpleName());
        if (i == -1)
            i = j = 1;
        String res = name.substring(0, i) + name.substring(j, name.length());
        if (hasArgs)
            res = addArgsNames(res);
        res = cutDollar(res);
        return res;
    }

    private static String cutDollar(@NotNull String s) {
        int i = s.indexOf('$');
        if (i == -1)
            return s;
        int j;
        for (j = i; j >= 0; j--) {
            if (s.charAt(j) == ' ') {
                break;
            }
        }
        if (j == -1) {
            return s.substring(i + 1, s.length());
        }
        return s.substring(0, j + 1) + s.substring(i + 1, s.length());
    }

    private static String addArgsNames(@NotNull String name) {
        String argName = "a";
        int count = 1;
        StringBuilder res = new StringBuilder();
        boolean stop = false;
        for (char c : name.toCharArray()) {
            if (!stop && c == ',' || c == ')') {
                res.append(' ').append(argName).append(count++);
            }
            res.append(c);
            if (c == ')')
                stop = true;
        }
        return res.toString();
    }

    private static String classToString(@NotNull Class<?> someClazz, int depth, boolean isInner) {
        StringBuilder buf = new StringBuilder();
        someClass = someClazz;
        buf.append(addClass(depth + 1, isInner));
        buf.append(addAllFields(depth + 1));

        buf.append(addAllConstructors(depth + 1, isInner));

        buf.append(addAllMethods(depth + 1));
        for (int i = 0; i < someClass.getDeclaredClasses().length; i++) {
            Class<?> c = someClass.getDeclaredClasses()[i];
            buf.append(classToString(c, depth + 1, true));
            if (i != someClass.getDeclaredClasses().length - 1)
                nextLine(depth + 1, buf);
            else
                nextLine(depth, buf);
            someClass = someClazz;
        }
        buf.append("}");
        return buf.toString();
    }

    private static String diffFields(@NotNull Class<?> class1, @NotNull Class<?> class2) {
        StringBuilder buf = new StringBuilder();

        HashSet<Field> set1 = new HashSet<>();
        HashSet<Field> set2 = new HashSet<>();

        for (Field f : class1.getDeclaredFields()) {
            for (Field other : class2.getDeclaredFields()) {
                if (compareFields(f, other)) {
                    set1.add(f);
                    set2.add(other);
                    break;
                }
            }
        }
        for (Field f : class1.getDeclaredFields()) {
            if (!set1.contains(f))
                buf.append(addField(f)).append('\n');
        }
        for (Field f : class2.getDeclaredFields()) {
            if (!set2.contains(f))
                buf.append(addField(f)).append('\n');
        }

        return buf.toString();
    }

    private static boolean compareFields(@NotNull Field f1, @NotNull Field f2) {
        return compareTypes(f1.getGenericType(), f2.getGenericType()) && f1.getName().equals(f2.getName());
    }

    private static String diffMethods(@NotNull Class<?> class1, @NotNull Class<?> class2) {
        StringBuilder buf = new StringBuilder();
        HashSet<Method> set1 = new HashSet<>();
        HashSet<Method> set2 = new HashSet<>();
        for (Method m : class1.getDeclaredMethods()) {
            for (Method other : class2.getDeclaredMethods()) {
                if (compareMethods(m, other)) {
                    set1.add(m);
                    set2.add(other);
                    break;
                }
            }
        }
        for (Method m : class1.getDeclaredMethods()) {
            if (!set1.contains(m))
                buf.append(addMethod(m, 0)).append('\n');
        }
        for (Method m : class2.getDeclaredMethods()) {
            if (!set2.contains(m))
                buf.append(addMethod(m, 0)).append('\n');
        }

        return buf.toString();
    }

    private static boolean compareMethods(@NotNull Method m1, @NotNull Method m2) {
        if (!compareTypes(m1.getGenericReturnType(), m2.getGenericReturnType())) {
            return false;
        }
        if (m1.getGenericParameterTypes().length != m2.getGenericParameterTypes().length) {
            return false;
        }
        for (int i = 0; i < m1.getGenericParameterTypes().length; i++) {
            if (!compareTypes(m1.getGenericParameterTypes()[i], m2.getGenericParameterTypes()[i])) {
                return false;
            }
        }
        return m1.getName().equals(m2.getName());
    }

    private static boolean compareTypes(@NotNull Type t1, @NotNull Type t2) {
        String s1 = correctType(t1.getTypeName());
        String s2 = correctType(t2.getTypeName());
        return s1.equals(s2);
    }

    private static String correctType(@NotNull String t) {
        int i = t.indexOf("extends Object");
        if (i != -1)
            return t.substring(0, i) + t.substring(i + 13, t.length());
        return t;
    }


}
