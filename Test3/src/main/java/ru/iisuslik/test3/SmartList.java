package ru.iisuslik.test3;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * List that extends AbstractList and works faster with 0 - 5 elements:
 * if size == 1 - it contains just element
 * if 2 <= size <= 5 - it contains array[5]
 * if size > 5 - it contains ArrayList
 *
 * @param <E>
 */
public class SmartList<E> extends AbstractList<E> {

    private Object data = null;

    private int size = 0;

    /**
     * Construct SmartList without elements and with size = 0
     */
    public SmartList() {
        data = null;
    }


    /**
     * Construct SmartList and put all elements from collection to it
     *
     * @param c - collection from where will be added all elements
     */
    public SmartList(Collection<? extends E> c) {
        this.addAll(c);
    }

    /**
     * @see AbstractList#add(Object)
     */
    @Override
    public boolean add(E e) {
        if (size == 0) {
            data = e;
        } else if (size == 1) {
            Object[] arr = new Object[5];
            arr[0] = data;
            arr[1] = e;
            data = arr;
        } else if (size >= 2 && size <= 4) {
            Object[] arr = (Object[]) data;
            arr[size] = e;
        } else if (size == 5) {
            ArrayList<E> newData = new ArrayList<E>(Arrays.asList((E[]) data));
            newData.add(e);
            data = newData;
        } else {
            ((ArrayList<E>) data).add(e);
        }
        size++;
        return true;
    }

    /**
     * @see AbstractList#add(int, Object)
     */
    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            data = element;
        } else if (size == 1) {
            Object[] arr = new Object[5];
            arr[index == 0 ? 1 : 0] = data;
            arr[index] = element;
            data = arr;
        } else if (size >= 2 && size <= 4) {
            Object[] arr = (Object[]) data;
            for (int i = size - 1; i >= index; i--) {
                arr[i + 1] = arr[i];
            }
            arr[index] = element;
            data = arr;
        } else if (size == 5) {
            ArrayList<E> newData = new ArrayList<E>(Arrays.asList((E[]) data));
            newData.add(index, element);
            data = newData;
        } else {
            ((ArrayList<E>) data).add(index, element);
        }
        size++;
    }


    /**
     * @see AbstractList#remove(int)
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E old;
        if (size == 1) {
            old = (E) data;
            data = null;
            size--;
            return old;
        } else if (size == 2) {
            Object[] arr = (Object[]) data;
            old = (E) arr[index];
            int index2 = index == 0 ? 1 : 0;
            data = arr[index2];
            size--;
            return old;
        } else if (size >= 3 && size <= 5) {
            Object[] arr = (Object[]) data;
            old = (E) arr[index];
            for (int i = index; i < size - 1; i++) {
                arr[i] = arr[i + 1];
            }
            size--;
            return old;
        } else if (size == 6) {
            ArrayList<E> list = (ArrayList<E>) data;
            old = list.remove(index);
            data = list.toArray();
            size--;
            return old;
        } else {
            ArrayList<E> list = (ArrayList<E>) data;
            size--;
            return list.remove(index);
        }
    }


    /**
     * @see AbstractList#set(int, Object)
     */
    @Override
    public E set(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E old = null;
        if (size == 1) {
            old = (E) data;
            data = element;
            return old;
        } else if (size >= 2 && size <= 5) {
            Object[] arr = (Object[]) data;
            old = (E) arr[index];
            arr[index] = element;
            return old;
        } else {
            ArrayList<E> list = (ArrayList<E>) data;
            return list.set(index, element);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 1) {
            return (E) data;
        } else if (size <= 5) {
            Object[] arr = (Object[]) data;
            return (E) arr[index];
        } else {
            ArrayList<E> list = (ArrayList<E>) data;
            return list.get(index);
        }
    }

    /**
     * @see AbstractList#size()
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * @see AbstractList#clear()
     */
    @Override
    public void clear() {
        data = null;
        size = 0;
    }
}
