package com.sb.sweetbucket.model;

import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.ShopsResponse;

import java.util.List;

/**
 * Created by harmeet on 01-09-2019.
 */

public class HomeDataStore {

    private List<ShopsResponse> shopsproductResponseList;
    private List<Product> productList;
    private List<Category> categoryList;
    private static HomeDataStore instance = null;


    public static HomeDataStore getInstance(){
        if (instance==null){
            instance = new HomeDataStore();
        }return instance;
    }
    private HomeDataStore(){

    }

    public List<ShopsResponse> getShopsproductResponseList() {
        return shopsproductResponseList;
    }

    public void setShopsproductResponseList(List<ShopsResponse> shopsproductResponseList) {
        this.shopsproductResponseList = shopsproductResponseList;
        if (shopsproductResponseList.size()>0){
            ShopsResponse heading = new ShopsResponse();
            heading.setName("Heading");
            this.shopsproductResponseList.add(0,heading);
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        if (productList.size()>0){
            Product heading = new Product();
            heading.setName("Heading");
            this.productList.add(0,heading);
        }
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
