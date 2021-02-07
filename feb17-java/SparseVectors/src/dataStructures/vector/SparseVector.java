/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import java.util.ArrayList;
import java.util.Iterator;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i) {
            // TODO
            if( i < 0 || i >= sz){

                throw new VectorException("Index out of bounds exception");

            }
            return this.elem;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODO

            if( i < 0 || i >= sz){

                throw new VectorException("Index out of bounds exception");

            }

            if(sz == 1 ){


                return new Unif<T>(x);

            }


            else if (i < (sz/2)) {

                return new Node<>(new Unif<>(elem).set(sz/2,i ,elem),new Unif<>(elem));

            }

            else {

                return new Node<>(new Unif<>(elem),new Unif<>(elem).set((sz/2),i - (sz/2),elem));
            }

        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation

    protected static class Node<T> implements Tree<T> {

        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {
            // TODO
            //

            T res = null;

            if(i < 0 || i > sz){
               throw new VectorException("Index out of bounds exception");
            }


            if(this.left instanceof Unif<?>){
                Unif<T> u = (Unif<T>) left;
                res = u.elem;
            }

            else if(this.right instanceof Unif<?> ){
                Unif<T> u = (Unif<T>) right;
                res = u.elem;
            }


            else {

                if(i < (sz/2)){

                    res = left.get((sz/2), i);

                }

                else {
                    res = right.get((sz/2), i - (sz/2) );
                }

            }

            return res;

        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODO

            if( i < 0 || i >= sz){

                throw new VectorException("Index out of bounds exception");

            }

            if(this.left instanceof Unif<?>){
                Unif<T> u = (Unif<T>) left;
                u.elem = x;
                left = u;
            }
            if(this.right instanceof Unif<?>){
                Unif<T> u = (Unif<T>) right;
                u.elem = x;
                right = u;
            }

            if( i <= (sz/2) ){

                left = left.set(sz/2, i , x);

            }

            else {

                right = right.set(sz/2, i - (sz/2), x);

            }

            return this;
        }

        protected Tree<T> simplify() {
            // TODO

            if(left instanceof Unif<?> && right instanceof Unif<?>) {

                Unif<T> l = (Unif<T>) left;
                Unif<T> r = (Unif<T>) right;

                if(l.elem.equals(r.elem)){
                    return l;
                }

                else {

                    return this;
                }


            }

            return this;
        }

        @Override
        public String toString() {
            return "Node(" + left + ", " + right + ")";
        }
    }

    // SparseVector Implementation

    private int size;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        // TODO

        if(n < 0){
            throw new VectorException("No se puede vector de 2^n elementos con n=0");
        }

        size = (int) Math.pow(2,n);

        root = new Unif<T>(elem);


    }

    public int size() {
        // TODO
        return size;
    }

    public T get(int i) {
        // TODO
        if (i < 0 || i >= size) throw new VectorException("Index out of bounds");
        return root.get(size,i);
    }

    public void set(int i, T x) {
        if (i < 0 || i >= size) throw new VectorException("Index out of bounds");
        root = root.set(size,i,x);
    }

    @Override
    public Iterator<T> iterator() {
        // TODO
        ArrayList<T> elems = new ArrayList<T>();
        for(int i = 0; i < this.size; i++){
            elems.add(root.get(size,i));
        }
        return elems.iterator();
    }


    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }
}
