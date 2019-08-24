
package com.sb.sweetbucket.rest.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopsResponse implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vendor_details")
    @Expose
    private VendorDetails vendorDetails;
    private final static long serialVersionUID = 7898663670066719910L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VendorDetails getVendorDetails() {
        return vendorDetails;
    }

    public void setVendorDetails(VendorDetails vendorDetails) {
        this.vendorDetails = vendorDetails;
    }

    @Override
    public String toString() {
        return "ShopsResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vendorDetails=" + vendorDetails +
                '}';
    }
}
