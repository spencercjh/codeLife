package bridge;

/**
 * @author SpencerCJH
 * @date 2019/4/29 17:07
 */
public abstract class Abstraction {
    private Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public void request(){
        implementor.doSomething();
    }

    public Implementor getImplementor(){
        return implementor;
    }
}
