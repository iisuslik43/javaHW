package ru.iisuslik.reflector;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Tests to class Reflector
 */
public class ReflectorTest {

    /**
     * Try to print, compile and diff class with 1 field and 1 method
     */
    @Test
    public void printSimpleClass() throws Exception {
        printAndCompare(SimpleClass.class);
    }

    /**
     * Try to print, compile and diff class with many fields and methods with different modifiers
     */
    @Test
    public void printHardClass() throws Exception {
        printAndCompare(MyClass.class);
    }


    /**
     * Try to compare 2 classes with different field name
     */
    @Test
    public void compareDifferentNames() throws Exception {
        assertEquals("private int a;\nprivate int b;\n",
                Reflector.diffToString(SimpleClass.class, SimpleWithB.class));
    }

    /**
     * Try to compare 2 classes with different field type
     */
    @Test
    public void compareDifferentTypes() throws Exception {
        assertEquals("private int a;\nprivate char a;\n",
                Reflector.diffToString(SimpleClass.class, SimpleWithChar.class));
    }

    /**
     * Try to compare class with itself
     */
    @Test
    public void compareSameClass() throws Exception {
        assertEquals("", Reflector.diffToString(MyClass.class, MyClass.class));
    }

    /**
     * Try to compare class with method <E> and class with method <E extends Object>
     */
    @Test
    public void compareExtendsObject() throws Exception {
        assertEquals("", Reflector.diffToString(GenericClass.class, GenericClassStrange.class));
    }


    /**
     * Try to print, compile and diff class with many inner and nested classes
     */
    @Test
    public void printHardClassWithInner() throws Exception {
        printAndCompare(ClassWithInner.class);
    }

    /**
     * Try to print, compile and diff class with one inner class
     */
    @Test
    public void printSimpleClassWithInner() throws Exception {
        printAndCompare(OneInner.class);
    }

    /**
     * Try to print class that extends SimpleClass
     */
    @Test
    public void printSimpleClassExtendsSimple() throws Exception {
        print(ClassExtendsSimple.class);
        String res = Reflector.classToString(ClassExtendsSimple.class);
        assertTrue(res.contains("public class SomeClass"));
        assertTrue(res.contains("extends SimpleClass"));
        assertTrue(res.contains("int c"));
        assertTrue(res.contains("public void go()"));
    }

    /**
     * Try to print class that implements Interface
     */
    @Test
    public void printSimpleClassImplementsInterface() throws Exception {
        print(ClassImplementsInterface.class);
        String res = Reflector.classToString(ClassImplementsInterface.class);
        assertTrue(res.contains("public class SomeClass"));
        assertTrue(res.contains("implements Interface"));
        assertTrue(res.contains("void nothing()"));
    }

    /**
     * Try to print, compile and diff class with one nested class
     */
    @Test
    public void printSimpleClassWithNested() throws Exception {
        printAndCompare(OneNested.class);
    }

    /**
     * Try to print, compile and diff class with one nested class
     */
    @Test
    public void printSimpleClassWithGenerics() throws Exception {
        printAndCompare(GenericClass.class);
    }

    private void print(Class<?> clazz) {
        Reflector.printStructure(clazz);
    }

    private void printAndCompare(Class<?> originalClass) throws Exception {
        Reflector.printStructure(originalClass);
        Class<?> clazz = writeClassAndCompile(Reflector.classToString(originalClass), originalClass);
        Reflector.diffClasses(originalClass, clazz);
        assertEquals("", Reflector.diffToString(clazz, originalClass));
    }

    private Class<?> writeClassAndCompile(String allClass, Class<?> original) throws IOException,
            ClassNotFoundException, InterruptedException {
        char sep = File.separatorChar;
        SimpleClass aaa = new SimpleClass();
        Class.forName("ru.iisuslik.reflector.SimpleClass");
        String filename = "." + sep + "src" + sep + "test" + sep + "resources" + sep + "SomeClass.java";
        String filenameDotClass = "." + sep + "src" + sep + "test" + sep + "resources" + sep + "SomeClass.class";
        PrintWriter out = new PrintWriter(filename);
        out.print(allClass);
        out.close();
        if (original.getSuperclass() != null) {
            ReflectorTest.class.getClassLoader().loadClass(original.getSuperclass().getName());
        }
        Runtime.getRuntime().exec("javac " + filename).waitFor();
        byte b[] = fetchClassFromFS(filenameDotClass);
        Class<?> clazz = new ClassLoader() {
            public Class<?> getClass(String s, byte[] b, int l, int r) {
                return super.defineClass(s, b, l, r);
            }
        }.getClass("SomeClass", b, 0, b.length);
        File dir = new File("." + sep + "src" + sep + "test" + sep + "resources");
        if (dir.listFiles() != null)
            for (File f : dir.listFiles()) {
                f.deleteOnExit();
            }
        return clazz;
    }

    private byte[] fetchClassFromFS(String path) throws IOException {
        InputStream is = new FileInputStream(new File(path));
        long length = new File(path).length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + path);
        }
        is.close();
        return bytes;
    }
}

