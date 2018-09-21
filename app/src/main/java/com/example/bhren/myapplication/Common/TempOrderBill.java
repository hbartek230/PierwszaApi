package com.example.bhren.myapplication.Common;

import com.example.bhren.myapplication.Model.TempOrder;

public class TempOrderBill {
    private String key;
    private TempOrder tempOrder;

    public TempOrderBill() {
    }

    public TempOrderBill(String key, TempOrder tempOrder) {
        this.key = key;
        this.tempOrder = tempOrder;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TempOrder getTempOrder() {
        return tempOrder;
    }

    public void setTempOrder(TempOrder tempOrder) {
        this.tempOrder = tempOrder;
    }
}


