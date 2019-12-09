package com.example.theforloopapp.ModelClass;


public class getPaymentData {

    String vpaAddress;
    Double amount;
    Boolean status;
    String productName;

    public String getVpaAddress() {
        return vpaAddress;
    }

    public void setVpaAddress(String vpaAddress) {
        this.vpaAddress = vpaAddress;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}