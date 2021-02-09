package pintaArbol;

import dataStructures.tuple.Tuple3;

public class NodeB<T> {
    NodeB<T> lt;
    NodeB<T> rt;
    T elem;

    public NodeB(NodeB<T> i, T v, NodeB<T> d) {
        lt = i;
        elem = v;
        rt = d;
    }

    public NodeB(T v) {
        this(null, v, null);
    }

    @Override
    public String toString() {
        String si =  (lt != null)? lt.toString():"EmptyB";
        String sd =  (rt != null)? rt.toString():"EmptyB";
        return "(NodeB " + si +" " + elem + " " + sd + ")";
    }

    public static <S> int columnas(NodeB<S> ar) {
        return (ar==null)? 0 : columnas(ar.lt) + columnas(ar.rt) + 1;
    }

    public static <S> int columnasAM(NodeB<Tuple3<S,Integer, Integer>> ar) {
        return (ar==null) ? 0 : ar.elem._2() + ar.elem._3() + 1;
    }

    public static <S> NodeB<Tuple3<S,Integer, Integer>> anota(NodeB<S> ar) {
        NodeB<Tuple3<S,Integer,Integer>> sol = null;
        if (ar != null) {
            NodeB<Tuple3<S,Integer,Integer>> ia = anota(ar.lt);
            NodeB<Tuple3<S,Integer,Integer>> da = anota(ar.rt);
            sol = new NodeB<>(ia, new Tuple3<>(ar.elem, columnasAM(ia), columnasAM(da)), da);
        }
        return sol;
    }

    public static <S> NodeB<Tuple3<S,Integer,Integer>> posCuadro(NodeB<Tuple3<S,Integer, Integer>> ar) {
        return posCuadrop(ar,0,0);
    }

    private static <S> NodeB<Tuple3<S,Integer,Integer>> posCuadrop(NodeB<Tuple3<S,Integer, Integer>> ar, int f, int c) {
        NodeB<Tuple3<S,Integer, Integer>> sol = null;
        if (ar != null) {
            sol = new NodeB<>(posCuadrop(ar.lt, f + 1, c),
                    new Tuple3<>(ar.elem._1(), f, c + ar.elem._2()),
                    posCuadrop(ar.rt, f + 1, c + ar.elem._2() + 1));
        }
        return sol;
    }


    public static <S> NodeB<Tuple3<S,Integer, Integer>> longFlecha(NodeB<Tuple3<S,Integer, Integer>> ar) {
        NodeB<Tuple3<S,Integer, Integer>> sol = null;
        if (ar != null) {
            int ai = anchuraI(ar.lt);
            int ad = anchuraD(ar.rt);
            sol = new NodeB<>(longFlecha(ar.lt),
                    new Tuple3<>(ar.elem._1(), ar.elem._2()-ai,ar.elem._3()-ad ),
                    longFlecha(ar.rt));
        }
        return sol;
    }

    private static <S> int anchuraI(NodeB<Tuple3<S,Integer, Integer>> ar) {
        return (ar == null) ? 0: ar.elem._2();
    }
    private static <S> int anchuraD(NodeB<Tuple3<S,Integer, Integer>> ar) {
        return (ar == null) ? 0: ar.elem._3();
    }

}

// Cuál será el valor del nodo raíz del resultado de ejecutar
// Nodo.posCuadro(Nodo.anota(Nodo.longFlecha(Nodo.anota(Nodo.ejemplo))))
// Tuple3(Tuple3(w, 4, 2), 0, 7)