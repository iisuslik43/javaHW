package ru.iisuslik.treeSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

class BinaryTree<E> implements Collection<E> {
    private Node<E> head = null;

    private int size = 0;

    private Comparator<? super E> comp;

    /**
     * TreeSet without constructor args will be use compareTo to compare elements
     */
    public BinaryTree() {
        comp = null;
    }

    /**
     * TreeSet will use your comparator
     *
     * @param comp - comparator to compare elements in TreeSet
     */
    public BinaryTree(@NotNull Comparator<? super E> comp) {
        this.comp = comp;
    }

    private int compare(E x, E y) {
        if (comp == null) {
            return ((Comparable<E>) x).compareTo(y);
        } else {
            return comp.compare(x, y);
        }
    }

    /**
     * {@link java.util.TreeSet#descendingIterator()}
     **/
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            Node<E> now = maximum(head);

            @Override
            public boolean hasNext() {
                return now != null;
            }

            @Override
            public E next() {
                E res = now.value;
                now = prevNode(now);
                return res;
            }
        };
    }

    /**
     * {@link java.util.TreeSet#descendingSet()}
     **/
    public BinaryTree<E> descendingTree() {
        return new BinaryTree<E>(this) {
            @Override
            public Iterator<E> iterator() {
                return super.descendingIterator();
            }

            @Override
            public Iterator<E> descendingIterator() {
                return super.iterator();
            }
        };
    }

    private BinaryTree(BinaryTree<E> old) {
        head = old.head;
        size = old.size;
    }

    /**
     * {@link java.util.TreeSet#first()}
     **/
    public E first() {
        return minimum(head).value;
    }

    /**
     * {@link java.util.TreeSet#last()}
     **/
    public E last() {
        return maximum(head).value;
    }

    /**
     * {@link java.util.TreeSet#lower}
     **/
    public E lower(@NotNull E e) {
        Node<E> now = head;
        while (true) {
            int compareRes = compare(e, now.value);
            if (compareRes < 0) {
                if (now.l != null)
                    now = now.l;
                else break;
            } else if (compareRes > 0) {
                if (now.r != null)
                    now = now.r;
                else break;
            } else {
                return prevNode(now).value;
            }
        }
        if (compare(now.value, e) > 0) {
            return prevNode(now).value;
        }
        return now.value;
    }

    /**
     * {@link java.util.TreeSet#floor}
     **/
    public E floor(@NotNull E e) {
        if (contains(e)) {
            return e;
        }
        return lower(e);
    }


    /**
     * {@link java.util.TreeSet#ceiling}
     **/
    public E ceiling(@NotNull E e) {
        if (contains(e)) {
            return e;
        }
        return higher(e);
    }

    /**
     * {@link java.util.TreeSet#higher}
     **/
    public E higher(@NotNull E e) {
        Node<E> now = head;
        while (true) {
            int compareRes = compare(e, now.value);
            if (compareRes < 0) {
                if (now.l != null) now = now.l;
                else break;
            } else if (compareRes > 0) {
                if (now.r != null) now = now.r;
                else break;
            } else {
                return nextNode(now).value;
            }
        }
        if (compare(now.value, e) < 0) {
            return nextNode(now).value;
        }
        return now.value;
    }


    /**
     * {@link Set#size()}
     **/
    public int size() {
        return size;
    }

    /**
     * {@link Set#isEmpty()}
     **/
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * {@link Set#contains(Object o)}
     **/
    public boolean contains(@NotNull Object o) {
        Node<E> next = head;
        while (next != null) {
            int compareResult = compare((E) o, next.value);
            if (compareResult == 0) {
                return true;
            } else if (compareResult < 0) {
                next = next.l;
            } else {
                next = next.r;
            }
        }
        return false;
    }

    private Node<E> nextNode(@Nullable Node<E> now) {
        if (now == null) {
            return null;
        }
        if (now.r != null) {
            return minimum(now.r);
        }
        Node<E> parent = now.parent;
        while (parent != null && now == parent.r) {
            now = parent;
            parent = now.parent;
        }
        return parent;
    }

    private Node<E> prevNode(@Nullable Node<E> now) {
        if (now == null) {
            return null;
        }
        if (now.l != null) {
            return maximum(now.l);
        }
        Node<E> parent = now.parent;
        while (parent != null && now == parent.l) {
            now = parent;
            parent = now.parent;
        }
        return parent;
    }


    /**
     * {@link Set#iterator()}
     **/
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> now = minimum(head);

            @Override
            public boolean hasNext() {
                return now != null;
            }

            @Override
            public E next() {
                E res = now.value;
                now = nextNode(now);
                return res;
            }
        };
    }

    /**
     * {@link Set#toArray()}
     **/
    public Object[] toArray() {
        Object[] res = new Object[size];
        int i = 0;
        for (E it : this) {
            res[i] = it;
            i++;
        }
        return res;
    }

    /**
     * {@link Set#toArray(T[] a)}
     **/
    public <T> T[] toArray(@NotNull T[] a) {
        if (a.length < size()) {
            a = (T[]) new Object[size()];
        }
        int i = 0;
        for (E el : this) {
            a[i] = (T) el;
            if (++i == a.length) {
                break;
            }
        }
        return a;
    }

    /**
     * {@link Set#add}
     **/
    public boolean add(@NotNull E e) {
        if (contains(e)) {
            return false;
        }
        size++;
        if (head == null) {
            head = new Node<>(e, null);
            return true;
        }
        Node<E> next = head;
        while (true) {
            int compareResult = compare(e, next.value);
            if (compareResult < 0) {
                if (next.l == null) {
                    next.l = new Node<>(e, next);
                    return true;
                }
                next = next.l;
            } else {
                if (next.r == null) {
                    next.r = new Node<>(e, next);
                    return true;
                }
                next = next.r;
            }
        }
    }

    /**
     * {@link Set#remove(Object o)}
     **/
    public boolean remove(@NotNull Object o) {
        if (!contains(o)) {
            return false;
        }
        size--;
        Node<E> root = head;
        head = delete(root, (E) o);
        return true;
    }

    private Node<E> delete(@Nullable Node<E> root, @NotNull E key) {//TODO
        if (root == null) return null;
        int compareResult = compare(key, root.value);
        if (compareResult < 0) {
            root.l = delete(root.l, key);
            if (root.l != null)
                root.l.parent = root;
        } else if (compareResult > 0) {
            root.r = delete(root.r, key);
            if (root.r != null)
                root.r.parent = root;
        } else if (root.l != null && root.r != null) {
            root.value = minimum(root.r).value;
            root.r = delete(root.r, root.value);
            if (root.r != null)
                root.r.parent = root;
        } else {
            if (root.l == null) {
                if (root.r != null)
                    root.r.parent = root.parent;
                root = root.r;
            } else {
                root.l.parent = root.parent;
                root = root.l;
            }
        }
        return root;
    }

    private Node<E> minimum(@Nullable Node<E> root) {
        while (root.l != null) {
            root = root.l;
        }
        return root;
    }

    private Node<E> maximum(@Nullable Node<E> root) {
        while (root.r != null) {
            root = root.r;
        }
        return root;
    }

    /**
     * {@link Set#containsAll(Collection)}
     **/
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@link Set#addAll(Collection)}
     **/
    public boolean addAll(@NotNull Collection<? extends E> c) {
        boolean changed = false;
        for (E el : c) {
            if (add(el)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * {@link Set#retainAll(Collection)}
     **/
    public boolean retainAll(@NotNull Collection<?> c) {
        ArrayList<E> res = new ArrayList<>();
        boolean changed = false;
        for (Object el : c) {
            if (contains(el)) {
                res.add((E) el);
                changed = true;
            }
        }
        this.clear();
        this.addAll(res);
        return changed;

    }

    /**
     * {@link Set#removeAll(Collection)}
     **/
    public boolean removeAll(@NotNull Collection<?> c) {
        boolean changed = false;
        for (Object el : c) {
            if (remove(el)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * {@link Set#clear()}
     **/
    public void clear() {
        head = null;
        size = 0;
    }


    private static class Node<E> {
        private Node<E> l = null;
        private Node<E> r = null;
        private E value;
        private Node<E> parent;

        private Node(E value, Node<E> parent) {
            this.value = value;
            this.parent = parent;
        }
    }
}
