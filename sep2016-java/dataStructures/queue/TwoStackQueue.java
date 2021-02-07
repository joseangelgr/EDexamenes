package dataStructures.queue;

import dataStructures.stack.ArrayStack;
import dataStructures.stack.LinkedStack;
import dataStructures.stack.Stack;

import java.util.StringJoiner;

public class TwoStackQueue<T> implements Queue<T> {
    private Stack<T> output;
    private Stack<T> input;

    public TwoStackQueue() {
        output = new ArrayStack<>();
        input = new LinkedStack<>();
    }

    @Override
    public boolean isEmpty() {
        return output.isEmpty();
    }

    @Override
    public void enqueue(T x) {
        input.push(x);
        mkValid();
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new EmptyQueueException("first in empty queue");
        }
        return output.top();
    }

    private void mkValid() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                T elem = input.top();
                input.pop();
                output.push(elem);
            }
        }
    }

    @Override
    public void dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException("dequeue in empty queue");
        }
        output.pop();
        mkValid();
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        Stack<T> copia = new ArrayStack<>();
        StringJoiner sj = new StringJoiner(",", className+"(",")");
        while (!output.isEmpty()) {
            T elem = output.top();
            sj.add(elem.toString());
            copia.push(elem);
            output.pop();
        }
        while (!copia.isEmpty()) {
            T elem = copia.top();
            output.push(elem);
            copia.pop();
        }
        while (!input.isEmpty()) {
            T elem = input.top();
            copia.push(elem);
            input.pop();
        }
        while (!copia.isEmpty()) {
            T elem = copia.top();
            sj.add(elem.toString());
            copia.pop();
            input.push(elem);
        }
        return sj.toString();
    }
}
