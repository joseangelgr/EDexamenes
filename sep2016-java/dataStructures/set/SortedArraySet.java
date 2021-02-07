package dataStructures.set;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class SortedArraySet<T extends Comparable<? super T>> implements Set<T> {
    private T [] elements;
    private int nextFree;
    private static final int TAM_DEFAULT = 10;

    public SortedArraySet() {
        elements = (T[]) new Comparable[TAM_DEFAULT];
        nextFree = 0;
    }

    @Override
    public boolean isEmpty() {
        return nextFree == 0;
    }

    @Override
    public int size() {
        return nextFree;
    }

    @Override
    public void insert(T x) {
        int pos = locate(x);
        boolean found = (pos < nextFree) && elements[pos].equals(x);
        if (!found) {
            ensureCapacity();
            for (int i = nextFree; i > pos; i--) {
                elements[i] = elements[i-1];
            }
            elements[pos] = x;
            nextFree++;
        }
    }

    // donde está el elemento o dónde debería estar
    private int locate(T x) {
        int pos = 0;
        while (pos < nextFree && elements[pos].compareTo(x) < 0) {
            pos++;
        }
        return pos;
    }

    private void ensureCapacity() {
        if (nextFree == elements.length) {
            elements = Arrays.copyOf(elements, elements.length*2);
        }
    }

    @Override
    public boolean isElem(T x) {
        int pos = locate(x);
        return (pos < nextFree) && elements[pos].equals(x);
    }

    @Override
    public void delete(T x) {
        int pos = locate(x);
        boolean found = (pos < nextFree) && elements[pos].equals(x);
        if (found) {
            for( int i = pos; i < nextFree - 1; i++) {
                elements[i] = elements[i+1];
            }
            nextFree--;
        }
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        StringJoiner sj = new StringJoiner(",",className+"(",")");
        for (int i = 0; i < nextFree; i++) {
            sj.add(elements[i].toString());
        }
        return sj.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedArraySetIterator();
    }

    private class SortedArraySetIterator implements Iterator<T> {
        int current;
        public SortedArraySetIterator() {
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < nextFree;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T elem = elements[current];
            current++;
            return elem;
        }
    }
}
