
package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable{

    private Integer id;
    @SerializedName("product_code")
    private String productCode;
    private String name;
    @SerializedName("cat1_id")
    private String cat1Id;
    private Object cat2Id;
    private Object cat3Id;
    @SerializedName("vendor_id")
    private String vendorId;
    private String info;
    private String tags;
    @SerializedName("image_url")
    private String imageUrl;
    private Object brandId;
    @SerializedName("base_price")
    private String basePrice;
    @SerializedName("deal_price")
    private String dealPrice;
    @SerializedName("sale_price")
    private String salePrice;
    private String discount;
    private String unit;
    @SerializedName("stock_qty")
    private String stockQty;
    private String approved;
    private Object approvedAt;
    private Object deletedAt;
    private String createdAt;
    private String updatedAt;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat1Id() {
        return cat1Id;
    }

    public void setCat1Id(String cat1Id) {
        this.cat1Id = cat1Id;
    }

    public Object getCat2Id() {
        return cat2Id;
    }

    public void setCat2Id(Object cat2Id) {
        this.cat2Id = cat2Id;
    }

    public Object getCat3Id() {
        return cat3Id;
    }

    public void setCat3Id(Object cat3Id) {
        this.cat3Id = cat3Id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getBrandId() {
        return brandId;
    }

    public void setBrandId(Object brandId) {
        this.brandId = brandId;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStockQty() {
        return stockQty;
    }

    public void setStockQty(String stockQty) {
        this.stockQty = stockQty;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Object getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Object approvedAt) {
        this.approvedAt = approvedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", cat1Id='" + cat1Id + '\'' +
                ", cat2Id=" + cat2Id +
                ", cat3Id=" + cat3Id +
                ", vendorId='" + vendorId + '\'' +
                ", info='" + info + '\'' +
                ", tags='" + tags + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", brandId=" + brandId +
                ", basePrice='" + basePrice + '\'' +
                ", dealPrice='" + dealPrice + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", discount='" + discount + '\'' +
                ", unit='" + unit + '\'' +
                ", stockQty='" + stockQty + '\'' +
                ", approved='" + approved + '\'' +
                ", approvedAt=" + approvedAt +
                ", deletedAt=" + deletedAt +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
