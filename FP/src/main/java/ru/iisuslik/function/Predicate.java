package ru.iisuslik.function;

public interface Predicate<A> extends Function1<A, Boolean> {
    default Predicate<A> or(Predicate<A> p) {
        return x -> apply(x) || p.apply(x);
    }

    default Predicate<A> and(Predicate<A> p) {
        return x -> apply(x) && p.apply(x);
    }

    default Predicate<A> not() {
        return x -> !apply(x);
    }

    Predicate ALWAYS_TRUE = x -> true;
    Predicate ALWAYS_FALSE = x -> false;
}
