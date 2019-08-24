
package com.sb.sweetbucket.rest.response;

import java.io.Serializable;
import java.util.List;

public class HomeResponse implements Serializable {

    private List<Category> category = null;
    private List<Product> products = null;
    private List<Shop> shops = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }


    @Override
    public String toString() {
        return "HomeResponse{" +
                "category=" + category +
                ", products=" + products +
                ", shops=" + shops +
                '}';
    }
}
