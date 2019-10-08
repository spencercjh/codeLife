package builder;

/**
 * @author SpencerCJH
 * @date 2019/4/28 21:27
 */
public class ConcreteProduct extends AbstractBuilder {
    private Product product = null;

    @Override
    public Product setPart(int part) {
        return product.setPart(part);
    }

    @Override
    public Product setModel(String model) {
        return product.setModel(model);
    }

    @Override
    public Product buildProduct() {
        product = new Product();
        return product;
    }
}
