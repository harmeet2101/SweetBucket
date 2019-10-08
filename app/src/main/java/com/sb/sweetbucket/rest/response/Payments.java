package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by harmeet on 08-10-2019.
 */

public class Payments implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("payment_mode")
    @Expose
    private PaymentMode paymentMode;
    @SerializedName("payment_status")
    @Expose
    private PaymentStatus paymentStatus;
    @SerializedName("payment_received")
    @Expose
    private String paymentReceived;
    @SerializedName("payment_pending")
    @Expose
    private String paymentPending;
    @SerializedName("payment_description")
    @Expose
    private Object paymentDescription;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(String paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public String getPaymentPending() {
        return paymentPending;
    }

    public void setPaymentPending(String paymentPending) {
        this.paymentPending = paymentPending;
    }

    public Object getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(Object paymentDescription) {
        this.paymentDescription = paymentDescription;
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
        return "Payments{" +
                "id=" + id +
                ", paymentId='" + paymentId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", paymentMode=" + paymentMode +
                ", paymentStatus=" + paymentStatus +
                ", paymentReceived='" + paymentReceived + '\'' +
                ", paymentPending='" + paymentPending + '\'' +
                ", paymentDescription=" + paymentDescription +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
