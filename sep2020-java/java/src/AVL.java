/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        // todo
        remainingCapacity = initialCapacity;
        weights = new ArrayList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        // todo
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight < remainingCapacity ){ // si no es mayor, cabe en el cubo
            remainingCapacity -= weight;
            weights.append(weight);
        }

        else {
            throw new RuntimeException("No cabe en el cubo");
        }
    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            // todo
            height = 1 + Math.max(height(left), height(right));
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            // todo
            maxRemainingCapacity = maxRemainingCapacity(this);
            maxRemainingCapacity = Math.max(maxRemainingCapacity, maxRemainingCapacity(left));
            maxRemainingCapacity = Math.max(maxRemainingCapacity, maxRemainingCapacity(right));


        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            // todo
            Node r = this.right;

            this.right = r.left;
            this.setHeight();
            this.setMaxRemainingCapacity();

            r.left = this;
            r.setHeight();
            r.setMaxRemainingCapacity();


            return r;
        }


        public List<Bin> inOrder() {

            List <Bin> res = new ArrayList<>();

            if(left != null){
                res = left.inOrder();
            }

            res.append(bin);

            if(right != null){
                res = right.inOrder();
            }



            return res;


        }

        //EXTRA Recorrido en pre-orden
        public List <Bin> preOrden(){
            List<Bin> res = new ArrayList<Bin>();

            res.append(bin);

            if(left != null){
                res = left.preOrden();
            }

            if(right != null){

                res = right.inOrder();

            }

            return res;
        }

        //EXTRA Recorrido en post-orden
        public List<Bin> postOrden () {
            List<Bin> res = new ArrayList<Bin>();

            if(this != null){
                res = left.postOrden();
                res = right.postOrden();
                res.append(bin);
            }

            return res;
        }
    }

    private static int height(Node node) {
        // todo
        return node == null ? 0 : node.height; //si el nodo es null devolvemos 0
    }

    private static int maxRemainingCapacity(Node node) {
        // todo


        return node == null ? 0 : node.maxRemainingCapacity ; // si el nodo es null devolvemos 0
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        // todo


        root = addRec(root,bin);

        //equilibrar
        int alt_izq = height(root.left);
        int alt_der = height(root.right);



        if(alt_der - alt_izq > 1){

            root.rotateLeft();

        }



    }

    private Node addRec(Node node,Bin b) {

        if(node == null){
            node = new Node();
            node.bin = b;
            node.right = null;
            node.left = null;
            node.setMaxRemainingCapacity();
            node.setHeight();
        }

        else {


            Node rr = addRec(node.right,b);


            if(height(rr) - height(node.left) > 1){

                node.right = rr;
                return node.rotateLeft();

            }

            else {

                node.right = rr;
                node.height = 1 + height(rr);
                node.setMaxRemainingCapacity();

            }


        }

        return node;
    }


    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        // todo



        // si el AVL es nulo
        if (root == null) {
            Bin b = new Bin(initialCapacity);
            b.addObject(weight); // creamos un cubo y añadimos el objeto
            addNewBin(b); // anadimos el cubo al final de espina derecha

        }

        //Si al avl no le queda capacidad restante añade un nuevo bin al final de la espina derecha
        else if (maxRemainingCapacity(root) < weight) {

            Bin b = new Bin(initialCapacity);
            b.addObject(weight);
            addNewBin(b);

        }

        else { //habra que añadirlo al root o alguno de los dos hijos

            root = add(weight,root);
        }



    }

    private Node add(int pesoObjeto,Node arbol) {


        if(maxRemainingCapacity(arbol.left) >= pesoObjeto){ //si cabe en la izquierda

            Node l = add(pesoObjeto,arbol.left);
            arbol.left = l;
            arbol.setMaxRemainingCapacity();
            return arbol;


        }

        else if(arbol.bin.remainingCapacity() >= pesoObjeto){ //si cabe en el nodo raiz

            arbol.bin.addObject(pesoObjeto);
            arbol.setMaxRemainingCapacity();
            return arbol;


        }


        else { //en otro caso se añade al primer cubo posible de la derecha

            Node r = add(pesoObjeto,arbol.right);
            arbol.right = r;
            arbol.setMaxRemainingCapacity();
            return arbol;
        }




        

    }

    public void addAll(int initialCapacity, int[] weights) {
        // todo

        for(int i : weights){
            this.addFirst(initialCapacity,i);
        }
    }

    public List<Bin> toList() {
        // todo
        return root.inOrder();
    }



    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}
}