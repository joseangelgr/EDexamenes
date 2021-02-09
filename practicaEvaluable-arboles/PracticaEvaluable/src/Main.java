import pintaArbol.NodeB;

public class Main {

    public static void main(String[] args) {

        NodeB<Character> ejemplo =
                new NodeB<>(new NodeB<>(new NodeB<>(new NodeB<>('s'), 't', new NodeB<>('q')), 'b', new NodeB<>(new NodeB<>('e'), 'f', (new NodeB<>('p')))),
                        'w',
                            new NodeB<>(new NodeB<>('r'), 'a', (new NodeB<>(new NodeB<>('k'), 'u', new NodeB<>('d')))));

        System.out.println(">ejemplo");
        System.out.println(ejemplo);
        System.out.println(">columnas(ejemplo)");
        System.out.println(NodeB.columnas(ejemplo));
        System.out.println(">anota(ejemplo)");
        System.out.println(NodeB.anota(ejemplo));
        System.out.println(">columnasAM(anotas(ejemplo))");
        System.out.println(NodeB.columnasAM(NodeB.anota(ejemplo)));
        System.out.println(">posCuadro(anota(ejemplo))");
        System.out.println(NodeB.posCuadro(NodeB.anota(ejemplo)));
        System.out.println(">lonFlecha(anota(ejemplo))");
        System.out.println(NodeB.longFlecha(NodeB.anota(ejemplo)));
        System.out.println(">posCuadro(anota(longFlecha(anota(ejemplo))))");
        System.out.println(NodeB.posCuadro(NodeB.anota(NodeB.longFlecha(NodeB.anota(ejemplo)))));
    }
}

/*
>ejemplo
(NodeB (NodeB (NodeB (NodeB EmptyB s EmptyB) t (NodeB EmptyB q EmptyB)) b (NodeB (NodeB EmptyB e EmptyB) f (NodeB EmptyB p EmptyB))) w (NodeB (NodeB EmptyB r EmptyB) a (NodeB (NodeB EmptyB k EmptyB) u (NodeB EmptyB d EmptyB))))
>columnas(ejemplo)
13
>anota(ejemplo)
(NodeB (NodeB (NodeB (NodeB EmptyB Tuple3(s, 0, 0) EmptyB) Tuple3(t, 1, 1) (NodeB EmptyB Tuple3(q, 0, 0) EmptyB)) Tuple3(b, 3, 3) (NodeB (NodeB EmptyB Tuple3(e, 0, 0) EmptyB) Tuple3(f, 1, 1) (NodeB EmptyB Tuple3(p, 0, 0) EmptyB))) Tuple3(w, 7, 5) (NodeB (NodeB EmptyB Tuple3(r, 0, 0) EmptyB) Tuple3(a, 1, 3) (NodeB (NodeB EmptyB Tuple3(k, 0, 0) EmptyB) Tuple3(u, 1, 1) (NodeB EmptyB Tuple3(d, 0, 0) EmptyB))))
>columnasAM(anotas(ejemplo))
13
>posCuadro(anota(ejemplo))
(NodeB (NodeB (NodeB (NodeB EmptyB Tuple3(s, 3, 0) EmptyB) Tuple3(t, 2, 1) (NodeB EmptyB Tuple3(q, 3, 2) EmptyB)) Tuple3(b, 1, 3) (NodeB (NodeB EmptyB Tuple3(e, 3, 4) EmptyB) Tuple3(f, 2, 5) (NodeB EmptyB Tuple3(p, 3, 6) EmptyB))) Tuple3(w, 0, 7) (NodeB (NodeB EmptyB Tuple3(r, 2, 8) EmptyB) Tuple3(a, 1, 9) (NodeB (NodeB EmptyB Tuple3(k, 3, 10) EmptyB) Tuple3(u, 2, 11) (NodeB EmptyB Tuple3(d, 3, 12) EmptyB))))
>lonFlecha(anota(ejemplo))
(NodeB (NodeB (NodeB (NodeB EmptyB Tuple3(s, 0, 0) EmptyB) Tuple3(t, 1, 1) (NodeB EmptyB Tuple3(q, 0, 0) EmptyB)) Tuple3(b, 2, 2) (NodeB (NodeB EmptyB Tuple3(e, 0, 0) EmptyB) Tuple3(f, 1, 1) (NodeB EmptyB Tuple3(p, 0, 0) EmptyB))) Tuple3(w, 4, 2) (NodeB (NodeB EmptyB Tuple3(r, 0, 0) EmptyB) Tuple3(a, 1, 2) (NodeB (NodeB EmptyB Tuple3(k, 0, 0) EmptyB) Tuple3(u, 1, 1) (NodeB EmptyB Tuple3(d, 0, 0) EmptyB))))
>posCuadro(anota(longFlecha(anota(ejemplo))))
...
*/
