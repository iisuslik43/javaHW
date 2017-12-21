package ru.iisuslik.reflector;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

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

    @Test
    public void compareDifferentNames() throws Exception {
        assertEquals("private int a;\nprivate int b;\n", Reflector.diffToString(SimpleClass.class, SimpleWithB.class));
    }

    @Test
    public void compareDifferentTypes() throws Exception {
        assertEquals("private int a;\nprivate char a;\n",
                Reflector.diffToString(SimpleClass.class, SimpleWithChar.class));
    }

    @Test
    public void compareSameClass() throws Exception {
        assertEquals("", Reflector.diffToString(MyClass.class, MyClass.class));
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
     * Try to print, compile and diff class extends SimpleClass
     */
    @Test
    public void printSimpleClassExtendsSimple() throws Exception {
        print(ClassExtendsSimple.class);
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

    private Class<?> writeClassAndCompile(String allClass, Class<?> original) throws IOException, ClassNotFoundException, InterruptedException {
        char sep = File.separatorChar;
        SimpleClass aaa = new SimpleClass();
        Class.forName("ru.iisuslik.reflector.SimpleClass");
        String filename = "." + sep + "src" + sep + "test" + sep + "resources" + sep + "SomeClass.java";
        String filenameDotClass = "." + sep + "src" + sep + "test" + sep + "resources" + sep + "SomeClass.class";
        PrintWriter out = new PrintWriter(filename);
        out.print(allClass);
        out.close();
        if(original.getSuperclass() != null) {
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

        // Get the size of the file
        long length = new File(path).length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + path);
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;

    }
}

