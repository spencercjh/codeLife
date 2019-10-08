package builder;

/**
 * @author SpencerCJH
 * @date 2019/4/28 21:25
 */
public class Product {
    private int part;
    private String model;

    public int getPart() {
        return part;
    }

    public Product setPart(int part) {
        this.part = part;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Product setModel(String model) {
        this.model = model;
        return this;
    }

    public void doSomething() {
        System.out.println("Product");
    }
}
