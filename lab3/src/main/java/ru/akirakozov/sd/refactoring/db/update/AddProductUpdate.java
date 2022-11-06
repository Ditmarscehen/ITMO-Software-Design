package ru.akirakozov.sd.refactoring.db.update;

import ru.akirakozov.sd.refactoring.model.Product;

public final class AddProductUpdate extends Update {
    private final Product product;

    public AddProductUpdate(Product product) {
        this.product = product;
    }

    @Override
    protected String query() {
        return "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + product.name() + "\"," + product.price() + ")";
    }
}
