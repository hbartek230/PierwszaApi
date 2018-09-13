package com.example.bhren.myapplication.Model;

public class TempOrder {

    private String amount;
    private String kind;
    private String quantity;

    public TempOrder() {
    }

    public TempOrder(String amount, String kind, String quantity) {
        this.amount = amount;
        this.kind = kind;
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
