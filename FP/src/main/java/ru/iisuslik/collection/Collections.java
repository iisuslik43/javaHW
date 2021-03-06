package ru.iisuslik.collection;

import org.jetbrains.annotations.NotNull;
import ru.iisuslik.function.Function1;
import ru.iisuslik.function.Function2;
import ru.iisuslik.function.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class with some useful methods with lists and functions
 */
public class Collections {
    /**
     * Apply function if all elements of iterable
     *
     * @param func      funtion from A to B that will apply to container
     * @param container iterable class that we want to map
     * @param <A>       type of values in iterable
     * @param <B>       type of function result
     * @return list that contains result of mapping
     */
    public static <A, B> List<B> map(@NotNull final Function1<A, B> func, @NotNull final Iterable<A> container) {
        ArrayList<B> arrRes = new ArrayList<>();
        for (A element : container) {
            arrRes.add(func.apply(element));
        }
        return arrRes;
    }

    /**
     * Filters container returns list that contains only element with pred(element) = true
     *
     * @param pred      predicate function
     *                  that is true if you want to take this element to list and false else
     * @param container iterable class that you want to filter
     * @param <A>       type of elements in container and predicate argument
     * @return list that contains filtered elements
     */
    public static <A> List<A> filter(@NotNull final Predicate<A> pred, @NotNull final Iterable<A> container) {
        ArrayList<A> result = new ArrayList<>();
        for (A element : container) {
            if (pred.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * Puts elements of container in list while pred(element) = true
     *
     * @param pred      predicate that applies to elements of container
     * @param container iterable class from that will be taken elements
     * @param <A>       <A> type of elements in container and predicate argument
     * @return list that contains first elements: pred(element) = true
     */
    public static <A> List<A> takeWhile(@NotNull final Predicate<A> pred, @NotNull final Iterable<A> container) {
        ArrayList<A> result = new ArrayList<>();
        for (A element : container) {
            if (pred.apply(element)) {
                result.add(element);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Puts elements of container in list while pred(element) = false
     *
     * @param pred      predicate that applies to elements of container
     * @param container iterable class from that will be taken elements
     * @param <A>       type of elements in container and predicate argument
     * @return list that contains first elements: pred(element) = false
     */
    public static <A> List<A> takeUntil(@NotNull final Predicate<A> pred, @NotNull final Iterable<A> container) {
        return takeWhile(pred.not(), container);
    }


    /**
     * Apply function to element in container and result in the rest of container
     * Begins from the end of container
     *
     * @param func      function that will be applied
     * @param container collection with elements
     * @param first     element that will be argument for first calling of func
     * @param <A>       type of elements in container and first func argument
     * @param <B>       type of function result and second func argument
     * @return result of applying function to all elements
     */
    public static <A, B> B foldr(@NotNull final Function2<A, B, B> func, @NotNull final Collection<A> container,
                                 @NotNull final B first) {

        ArrayList<A> forFoldr = new ArrayList<>();
        forFoldr.addAll(container);

        B now = first;
        for (int i = forFoldr.size() - 1; i >= 0; i--) {
            now = func.apply(forFoldr.get(i), now);
        }
        return now;
    }

    /**
     * Apply function to element in container and result in the rest of container
     * Begins from the first element of container
     *
     * @param func      function that will be applied
     * @param container collection with elements
     * @param first     element that will be argument for last calling of func
     * @param <A>       type of elements in container and second func argument
     * @param <B>       type of function result and first func argument
     * @return result of applying function to all elements
     */
    public static <A, B> B foldl(@NotNull final Function2<B, A, B> func, @NotNull final Collection<A> container,
                                 @NotNull final B first) {
        B now = first;
        for (A element : container) {
            now = func.apply(now, element);
        }
        return now;
    }
}
