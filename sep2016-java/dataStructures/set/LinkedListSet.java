/**
 * @author Pepe Gallardo, Data Structures, Grado en Inform�tica. UMA.
 *
 * Sets implemented using Java's linked lists
 */

package dataStructures.set;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringJoiner;

public class LinkedListSet<T extends Comparable<? super T>> implements Set<T> {
    protected LinkedList<T> elements;

    public LinkedListSet () {
        elements = new LinkedList<>();
    }

    public boolean isElem(T el) {
        return elements.contains(el);
    }

    public void insert(T el) {
        if (!elements.contains(el))
            elements.addLast(el);
    }

    public void delete(T el) {
        elements.remove(el);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() { return elements.size(); }

    public String toString() {
        String className = getClass().getSimpleName();
        StringJoiner joiner = new StringJoiner(",",className+"(",")");
        Iterator<T> it = elements.iterator();
        while(it.hasNext())
            joiner.add(it.next().toString());

        return joiner.toString();
    }

    /**
     * Iterator over elements in set.
     * Note that {@code remove} method is not supported. Note also
     * that list structure should not be modified during iteration as
     * iterator state may become inconsistent.
     * @see Iterable#iterator()
     */
    public Iterator<T> iterator() {
        return elements.iterator();
    }
}


