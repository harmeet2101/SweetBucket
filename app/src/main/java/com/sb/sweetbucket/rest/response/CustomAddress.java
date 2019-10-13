package com.sb.sweetbucket.rest.response;

import java.io.Serializable;

/**
 * Created by harmeet on 13-10-2019.
 */

public class CustomAddress implements Serializable {

    private Address address;
    private boolean selected;

    public CustomAddress(Address address, boolean selected) {
        this.address = address;
        this.selected = selected;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "CustomAddress{" +
                "address=" + address +
                ", selected=" + selected +
                '}';
    }
}
