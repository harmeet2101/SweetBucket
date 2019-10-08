package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 08-10-2019.
 */

public class OrderProduct implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("order_amount")
    @Expose
    private String orderAmount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("product_qty")
    @Expose
    private String productQty;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("vendor_remarks")
    @Expose
    private Object vendorRemarks;
    @SerializedName("vendor_accepted")
    @Expose
    private Object vendorAccepted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Object getVendorRemarks() {
        return vendorRemarks;
    }

    public void setVendorRemarks(Object vendorRemarks) {
        this.vendorRemarks = vendorRemarks;
    }

    public Object getVendorAccepted() {
        return vendorAccepted;
    }

    public void setVendorAccepted(Object vendorAccepted) {
        this.vendorAccepted = vendorAccepted;
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
        return "OrderProduct{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", discount='" + discount + '\'' +
                ", productQty='" + productQty + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", vendorRemarks=" + vendorRemarks +
                ", vendorAccepted=" + vendorAccepted +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
