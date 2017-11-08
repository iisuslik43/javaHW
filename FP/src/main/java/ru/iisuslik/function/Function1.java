package ru.iisuslik.function;

/**
 * Interface of Function from A to B
 *
 * @param <A> type of argument
 * @param <B> type of result
 */
public interface Function1<A, B> {
    /**
     * Applying function
     *
     * @param arg function argument
     * @return result of function applying
     */
    B apply(A arg);

    /**
     * Realization of functions compose: g(f)
     *
     * @param g 1 argument function from B to C
     * @param <C> type of result of function g
     * @return function from A to C
     */
    default <C> Function1<A, C> compose(Function1<B, C> g) {
        return arg -> g.apply(apply(arg));
    }
}
