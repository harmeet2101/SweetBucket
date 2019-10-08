package com.sb.sweetbucket.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harmeet on 08-10-2019.
 */

public class TransactionResponse implements Serializable {

    @SerializedName("payments")
    @Expose
    private List<Transaction> payments = null;

    public List<Transaction> getPayments() {
        return payments;
    }

    public void setPayments(List<Transaction> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "payments=" + payments +
                '}';
    }
}
