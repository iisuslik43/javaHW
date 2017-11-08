package ru.iisuslik.collection;

import ru.iisuslik.function.Function1;
import ru.iisuslik.function.Function2;
import ru.iisuslik.function.Predicate;

import java.util.ArrayList;
import java.util.Collection;

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
    public static <A, B> ArrayList<B> map(Function1<A, B> func, Iterable<A> container) {
        ArrayList<B> arrRes = new ArrayList<>();
        for (A element : container) {
            arrRes.add(func.apply(element));
        }
        return arrRes;
    }

    /**
     * Filters container returns list that contains only element with pred(element) = true
     *
     * @param pred predicate function
     *             that is true if you want to take this element to list and false else
     * @param container iterable class that you want to filter
     * @param <A> type of elements in container and predicate argument
     * @return list that contains filtered elements
     */
    public static <A> ArrayList<A> filter(Predicate<A> pred, Iterable<A> container) {
        ArrayList<A> arrRes = new ArrayList<>();
        for (A element : container) {
            if (pred.apply(element)) {
                arrRes.add(element);
            }
        }
        return arrRes;
    }

    /**
     * Puts elements of container in list while pred(element) = true
     *
     * @param pred predicate that applies to elements of container
     * @param container iterable class from that will be taken elements
     * @param <A> <A> type of elements in container and predicate argument
     * @return list that contains first elements: pred(element) = true
     */
    public static <A> ArrayList<A> takeWhile(Predicate<A> pred, Iterable<A> container) {
        ArrayList<A> arrRes = new ArrayList<>();
        for (A element : container) {
            if (pred.apply(element)) {
                arrRes.add(element);
            } else {
                break;
            }
        }
        return arrRes;
    }

    /**
     * Puts elements of container in list while pred(element) = false
     *
     * @param pred predicate that applies to elements of container
     * @param container iterable class from that will be taken elements
     * @param <A> type of elements in container and predicate argument
     * @return list that contains first elements: pred(element) = false
     */
    public static <A> ArrayList<A> takeUntil(Predicate<A> pred, Iterable<A> container) {
        return takeWhile(pred.not(), container);
    }


    /**
     * Apply function to element in container and result in the rest of container
     * Begins from the end of container
     *
     * @param func function that will be applied
     * @param container collection with elements
     * @param first element that will be argument for first calling of func
     * @param <A> type of elements in container and first func argument
     * @param <B> type of function result and second func argument
     * @return result of applying function to all elements
     */
    public static <A, B> B foldr(Function2<A, B, B> func, Collection<A> container, B first) {

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
     * @param func function that will be applied
     * @param container collection with elements
     * @param first element that will be argument for last calling of func
     * @param <A> type of elements in container and second func argument
     * @param <B> type of function result and first func argument
     * @return result of applying function to all elements
     */
    public static <A, B> B foldl(Function2<B, A, B> func, Collection<A> container, B first) {
        B now = first;
        for (A element : container) {
            now = func.apply(now, element);
        }
        return now;
    }
}
