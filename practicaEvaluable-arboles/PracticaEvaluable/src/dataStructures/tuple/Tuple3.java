package dataStructures.tuple;

public class Tuple3<A,B,C> {
    A elem1;
    B elem2;
    C elem3;

    public Tuple3(A p, B s, C t) {
        elem1 =p;
        elem2 = s;
        elem3 = t;
    }

    public A _1() {
        return elem1;
    }

    public B _2() {
        return elem2;
    }

    public C _3() {
        return elem3;
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className+"(" + elem1 + ", " + elem2 + ", " + elem3 +")";
    }
}
