package com.example.theforloopapp.ModelClass;

public class setPaymentData {

    String vpaAddress;
    Double amount;
    Integer pin;
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

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}