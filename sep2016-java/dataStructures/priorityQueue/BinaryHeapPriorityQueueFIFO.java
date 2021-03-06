package dataStructures.priorityQueue;

import dataStructures.heap.BinaryHeap;

import java.util.StringJoiner;

public class BinaryHeapPriorityQueueFIFO<T extends Comparable<? super T>> implements PriorityQueue<T> {
    private BinaryHeap<Node<T>> heap;

    private static class Node<S extends Comparable<? super S>> implements Comparable<Node<S>> {
        S data;
        int stamp;
        static int stampGen = 0;
        public Node(S data) {
            this.data = data;
            stamp = stampGen++;
        }
        public int compareTo(Node<S> node) {
            int res = data.compareTo(node.data);
            if (res == 0) {
                res = Integer.compare(stamp,node.stamp);
            }
            return res;
        }
    }

    /**
     * Creates an empty queue.
     * <p>Time complexity: O(1)
     */
    public BinaryHeapPriorityQueueFIFO() {
        heap = new BinaryHeap<>();
    }

    /**
     * {@inheritDoc}
     * <p>Time complexity: O(1)
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * {@inheritDoc}
     * Position of new element in queue depends on its priority.
     * The less the value of the element, the higher its priority.
     * <p>Time complexity: O(log n)
     */
    public void enqueue(T x) {
        heap.insert(new Node<>(x));
    }

    /**
     * {@inheritDoc}
     * <p>Time complexity: O(1)
     * @throws <code>EmptyQueueException</code> if queue stores no element.
     */
    public T first() {
        if(isEmpty())
            throw new EmptyPriorityQueueException("first on empty priority queue");
        else
            return heap.minElem().data;
    }

    /**
     * {@inheritDoc}
     * Position of new element in queue depends on its priority.
     * The less the value of the element, the higher its priority.
     * <p>Time complexity: O(log n)
     */
    public void dequeue() {
        if(isEmpty())
            throw new EmptyPriorityQueueException("first on empty priority queue");
        else
            heap.delMin();
    }

    /**
     * Returns representation of priority queue as a String.
     */
    @Override public String toString() {
        BinaryHeap<Node<T>> clonedHeap = new BinaryHeap<>(heap);
        String className = getClass().getSimpleName();
        StringJoiner sj = new StringJoiner(",", className+"(",")");
        while(!clonedHeap.isEmpty()) {
            sj.add(clonedHeap.minElem().data.toString());
            clonedHeap.delMin();
        }
        return sj.toString();
    }
}
