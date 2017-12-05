package ru.iisuslik.function;

import org.jetbrains.annotations.NotNull;

/**
 * Function from A to boolean
 *
 * @param <A> argument type
 */
public interface Predicate<A> extends Function1<A, Boolean> {
    /**
     * Realization of disjunction of 2 predicates
     *
     * @param p second disjunction argument
     * @return predicate that returns true if this(x) || p(x)
     */
    default Predicate<A> or(@NotNull Predicate<A> p) {
        return x -> apply(x) || p.apply(x);
    }

    /**
     * Realization of conjunction of 2 predicates
     *
     * @param p second conjunction argument
     * @return true if this(x) && p(x)
     */
    default Predicate<A> and(@NotNull Predicate<A> p) {
        return x -> apply(x) && p.apply(x);
    }

    /**
     * Realization of predicate negation
     *
     * @return predicate that returns true if !this(x)
     */
    default Predicate<A> not() {
        return x -> !apply(x);
    }

    /**
     * Predicate that always returns true
     */
    Predicate<Object> ALWAYS_TRUE = x -> true;

    /**
     * Predicate that always returns false
     */
    Predicate<Object> ALWAYS_FALSE = x -> false;
}
