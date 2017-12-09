package ru.iisuslik.treeSet;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Realization of interface MyTreeSet that uses not balanced BST
 *
 * @param <E> type of values that contains in set
 */
public class TreeSet<E extends Comparable<? super E>> implements MyTreeSet<E> {

    private BinaryTree<E> tree;

    /**
     * TreeSet without constructor args will be use compareTo to compare elements
     */
    public TreeSet() {

        tree = new BinaryTree<>();
    }

    /**
     * TreeSet will use your comparator
     *
     * @param comp - comparator to compare elements in TreeSet
     */
    public TreeSet(@NotNull Comparator<? super E> comp) {
        tree = new BinaryTree<>(comp);
    }


    /**
     * {@link java.util.TreeSet#descendingIterator()}
     **/
    @Override
    public Iterator<E> descendingIterator() {
        return tree.descendingIterator();
    }

    /**
     * {@link java.util.TreeSet#descendingSet()}
     **/
    @Override
    public MyTreeSet<E> descendingSet() {
        TreeSet<E> newTreeSet = new TreeSet<>();
        newTreeSet.tree = tree.descendingTree();
        return newTreeSet;
    }

    /**
     * {@link java.util.TreeSet#first()}
     **/
    @Override
    public E first() {
        return tree.first();
    }

    /**
     * {@link java.util.TreeSet#last()}
     **/
    @Override
    public E last() {
        return tree.last();
    }

    /**
     * {@link java.util.TreeSet#lower}
     **/
    @Override
    public E lower(@NotNull E e) {
        return tree.lower(e);
    }

    /**
     * {@link java.util.TreeSet#floor}
     **/
    @Override
    public E floor(@NotNull E e) {
        return tree.floor(e);
    }


    /**
     * {@link java.util.TreeSet#ceiling}
     **/
    public E ceiling(@NotNull E e) {
        return tree.ceiling(e);
    }

    /**
     * {@link java.util.TreeSet#higher}
     **/
    public E higher(@NotNull E e) {
        return tree.higher(e);
    }


    /**
     * {@link Set#size()}
     **/
    @Override
    public int size() {
        return tree.size();
    }

    /**
     * {@link Set#isEmpty()}
     **/
    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     * {@link Set#contains(Object o)}
     **/
    @Override
    public boolean contains(@NotNull Object o) {
        return tree.contains(o);
    }

    /**
     * {@link Set#iterator()}
     **/
    @Override
    public Iterator<E> iterator() {
        return tree.iterator();
    }

    /**
     * {@link Set#toArray()}
     **/
    @Override
    public Object[] toArray() {
        return tree.toArray();
    }

    /**
     * {@link Set#toArray(T[] a)}
     **/
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return tree.toArray(a);
    }

    /**
     * {@link Set#add}
     **/
    @Override
    public boolean add(@NotNull E e) {
        return tree.add(e);
    }

    /**
     * {@link Set#remove(Object o)}
     **/
    @Override
    public boolean remove(@NotNull Object o) {
        return tree.remove(o);
    }

    /**
     * {@link Set#containsAll(Collection)}
     **/
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return tree.containsAll(c);
    }

    /**
     * {@link Set#addAll(Collection)}
     **/
    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return tree.addAll(c);
    }

    /**
     * {@link Set#retainAll(Collection)}
     **/
    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return tree.retainAll(c);

    }

    /**
     * {@link Set#removeAll(Collection)}
     **/
    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return tree.removeAll(c);
    }

    /**
     * {@link Set#clear()}
     **/
    @Override
    public void clear() {
        tree.clear();
    }


}
