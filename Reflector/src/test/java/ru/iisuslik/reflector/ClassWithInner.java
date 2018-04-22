package ru.iisuslik.reflector;

public class ClassWithInner {
    int a;

    public static class A {
        private int b;

        public String a(boolean b) {
            return "LEL";
        }
    }

    private class B {
        public final String s = "a";
        public B(){}
        private void nothing() {}
    }

    static class C {
        private int b;

        static class D {}
    }

    protected interface E {
        void gogo(int s);
    }
}
