package chapter2.delegation;

interface Interface {
    void a();

    void b();
}

/**
 * @author SpencerCJH
 * @date 2019/10/18 20:44
 */
public class Proxy implements Interface {
    private Interface o = new AClass();

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.a();
        proxy.toB();
        proxy.a();
    }

    @java.lang.Override
    public void a() {
        o.a();
    }

    @java.lang.Override
    public void b() {
        o.b();
    }

    public void toA() {
        o = new AClass();
    }

    public void toB() {
        o = new BClass();
    }
}

class AClass implements Interface {

    @java.lang.Override
    public void a() {
        System.out.println("A a");
    }

    @java.lang.Override
    public void b() {
        System.out.println("A b");
    }
}

class BClass implements Interface {

    @java.lang.Override
    public void a() {
        System.out.println("B a");
    }

    @java.lang.Override
    public void b() {
        System.out.println("B b");
    }
}