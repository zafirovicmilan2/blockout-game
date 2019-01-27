package geometry;

public class Coordinates <T>{
    private T i, j, k;

    public Coordinates(T i, T j, T k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public T getI() {
        return i;
    }

    public T getJ() {
        return j;
    }

    public T getK() {
        return k;
    }
}
