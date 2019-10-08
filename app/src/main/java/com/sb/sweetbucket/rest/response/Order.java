package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet on 08-10-2019.
 */

public class Order implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ordered_qty")
    @Expose
    private String orderedQty;
    @SerializedName("coupon")
    @Expose
    private Object coupon;
    @SerializedName("coupon_amount")
    @Expose
    private Object couponAmount;
    @SerializedName("referral")
    @Expose
    private Object referral;
    @SerializedName("referral_amount")
    @Expose
    private Object referralAmount;
    @SerializedName("order_total")
    @Expose
    private String orderTotal;
    @SerializedName("order_base_total")
    @Expose
    private String orderBaseTotal;
    @SerializedName("order_discount")
    @Expose
    private String orderDiscount;
    @SerializedName("order_tax")
    @Expose
    private String orderTax;
    @SerializedName("order_shipping")
    @Expose
    private String orderShipping;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("order_product")
    @Expose
    private List<OrderProduct> orderProduct = null;
    @SerializedName("order_status")
    @Expose
    private OrderStatus orderStatus;
    @SerializedName("address")
    @Expose
    private DeliveryAddress address;
    @SerializedName("payments")
    @Expose
    private Payments payments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(String orderedQty) {
        this.orderedQty = orderedQty;
    }

    public Object getCoupon() {
        return coupon;
    }

    public void setCoupon(Object coupon) {
        this.coupon = coupon;
    }

    public Object getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Object couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Object getReferral() {
        return referral;
    }

    public void setReferral(Object referral) {
        this.referral = referral;
    }

    public Object getReferralAmount() {
        return referralAmount;
    }

    public void setReferralAmount(Object referralAmount) {
        this.referralAmount = referralAmount;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderBaseTotal() {
        return orderBaseTotal;
    }

    public void setOrderBaseTotal(String orderBaseTotal) {
        this.orderBaseTotal = orderBaseTotal;
    }

    public String getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(String orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public String getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(String orderTax) {
        this.orderTax = orderTax;
    }

    public String getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(String orderShipping) {
        this.orderShipping = orderShipping;
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

    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public DeliveryAddress getAddress() {
        return address;
    }

    public Payments getPayments() {
        return payments;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", orderedQty='" + orderedQty + '\'' +
                ", coupon=" + coupon +
                ", couponAmount=" + couponAmount +
                ", referral=" + referral +
                ", referralAmount=" + referralAmount +
                ", orderTotal='" + orderTotal + '\'' +
                ", orderBaseTotal='" + orderBaseTotal + '\'' +
                ", orderDiscount='" + orderDiscount + '\'' +
                ", orderTax='" + orderTax + '\'' +
                ", orderShipping='" + orderShipping + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", orderProduct=" + orderProduct +
                ", orderStatus=" + orderStatus +
                ", address=" + address +
                ", payments=" + payments +
                '}';
    }
}
