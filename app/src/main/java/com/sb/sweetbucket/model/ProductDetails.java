package com.sb.sweetbucket.model;

import java.io.Serializable;

/**
 * Created by harmeet on 24-08-2019.
 */

public class ProductDetails implements Serializable
{
    private Integer id;
    private String productCode;
    private String name;
    private String catName;
    private String vendorName;
    private String info;
    private String tags;
    private String imageUrl;
    private String basePrice;
    private String dealPrice;
    private String salePrice;
    private String discount;
    private String unit;
    private String stockQty;

    public ProductDetails(Integer id, String productCode, String name, String catName,
                          String vendorName, String info, String tags, String imageUrl,
                          String basePrice, String dealPrice, String salePrice, String discount, String unit, String stockQty) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.catName = catName;
        this.vendorName = vendorName;
        this.info = info;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.basePrice = basePrice;
        this.dealPrice = dealPrice;
        this.salePrice = salePrice;
        this.discount = discount;
        this.unit = unit;
        this.stockQty = stockQty;
    }

    public Integer getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public String getCatName() {
        return catName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getInfo() {
        return info;
    }

    public String getTags() {
        return tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public String getDealPrice() {
        return dealPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public String getUnit() {
        return unit;
    }

    public String getStockQty() {
        return stockQty;
    }
}
