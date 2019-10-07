package com.sb.sweetbucket.model;

import com.sb.sweetbucket.rest.response.CartDetailsResponse;
import com.sb.sweetbucket.rest.response.Category;
import com.sb.sweetbucket.rest.response.Product;
import com.sb.sweetbucket.rest.response.Shop;
import com.sb.sweetbucket.rest.response.ShopsResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harmeet on 01-09-2019.
 */

public class HomeDataStore {

    private List<ShopsResponse> shopsproductResponseList;
    private List<Product> productList;
    private List<Category> categoryList;

    public CartDetailsResponse getCartDetailsResponse() {
        return cartDetailsResponse;
    }

    public void setCartDetailsResponse(CartDetailsResponse cartDetailsResponse) {
        this.cartDetailsResponse = cartDetailsResponse;
    }

    private CartDetailsResponse cartDetailsResponse;

    public Map<String, Shop> getShopsResponseMap() {
        return shopsResponseMap;
    }

    public void setShopsResponseMap(Map<String, Shop> shopsResponseMap) {
        this.shopsResponseMap = shopsResponseMap;
    }

    private Map<String,Shop> shopsResponseMap = new LinkedHashMap<>();
    private Map<Integer,String> categoryNameMap = new HashMap<>();
    private Map<String,String> vendorNameMap = new HashMap<>();

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Product> productMap) {
        this.productMap = productMap;
    }

    private Map<String,Product> productMap = new LinkedHashMap<>();
    public List<Shop> getAllShopList() {
        return allShopList;
    }

    public void setAllShopList(List<Shop> allShopList) {
        this.allShopList = allShopList;
        for (int i=0;i<allShopList.size();i++){
            shopsResponseMap.put(allShopList.get(i).getStoreName(),
                    allShopList.get(i));
        }
        for(Shop shop:allShopList){

                vendorNameMap.put(shop.getVendorId(),shop.getStoreName());
        }
    }

    private List<Shop> allShopList;
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
            for (int i=1;i<productList.size();i++){
                productMap.put(productList.get(i).getName(),
                        productList.get(i));
            }
        }
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        for(Category category:categoryList){
            categoryNameMap.put(category.getId(),category.getName());
        }
    }



    public Map<String, String> getVendorNameMap() {
        return vendorNameMap;
    }

    public Map<Integer, String> getCategoryNameMap() {
        return categoryNameMap;
    }
}
