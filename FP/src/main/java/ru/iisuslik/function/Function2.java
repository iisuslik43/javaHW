package ru.iisuslik.function;

import org.jetbrains.annotations.NotNull;

/**
 * 2 argument function A->B->C
 *
 * @param <A> type of first argument
 * @param <B> type of second argument
 * @param <C> type of result
 */
public interface Function2<A, B, C> {
    /**
     * Applying function
     *
     * @param arg1 first argument
     * @param arg2 second argument
     * @return result of function applying
     */
    C apply(A arg1, B arg2);

    /**
     * Composition of 1 argument function and 2 argument function
     * @param g 1 argument function from C to D
     * @param <D> type of result of g
     * @return 2 argument function A->B->D
     */
    default <D> Function2<A, B, D> compose(@NotNull Function1<C, D> g) {
        return (arg1, arg2) -> g.apply(apply(arg1, arg2));
    }

    /**
     * Make 1 argument function from 2 argument function by fixing first argument
     *
     * @param arg1 value of first argument
     * @return function from B to C
     */
    default Function1<B, C> bind1(@NotNull A arg1) {
        return arg2 -> apply(arg1, arg2);
    }

    /**
     * Make 1 argument function from 2 argument function by fixing first argument
     *
     * @param arg2 value of second argument
     * @return function from A to C
     */
    default Function1<A, C> bind2(@NotNull B arg2) {
        return arg1 -> apply(arg1, arg2);
    }

    /**
     * Make 1 argument function from 2 argument function by fixing first argument
     *
     * @param arg2 value of second argument
     * @return function from A to C
     */
    default Function1<A, C> curry(@NotNull B arg2) {
        return bind2(arg2);
    }
}
