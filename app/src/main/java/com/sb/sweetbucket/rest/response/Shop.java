
package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Shop implements Serializable{

    private Integer id;
    @SerializedName("vendor_id")
    private String vendorId;
    @SerializedName("vendor_code")
    private String vendorCode;
    @SerializedName("store_name")
    private String storeName;
    private String address1;
    private String address2;
    private String country;
    private String state;
    private String pin;
    private String city;
    private String street;
    private String description;
    @SerializedName("store_front")
    private String storeFront;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreFront() {
        return storeFront;
    }

    public void setStoreFront(String storeFront) {
        this.storeFront = storeFront;
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
        return "Shop{" +
                "id=" + id +
                ", vendorId='" + vendorId + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", storeName='" + storeName + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", pin='" + pin + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", description='" + description + '\'' +
                ", storeFront='" + storeFront + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
