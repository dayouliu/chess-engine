package game.data;

// "RC mod"

public class RCM {

    public RC s, e;
    public int a, b;

    public RCM(RC s, RC e, int a, int b) {
        this.s = s;
        this.e = e;
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "RCM{" +
                "s=" + s +
                ", e=" + e +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
