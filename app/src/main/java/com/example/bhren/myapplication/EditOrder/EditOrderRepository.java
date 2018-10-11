package com.example.bhren.myapplication.EditOrder;

public interface EditOrderRepository {

    void editOrderItem(String key, String price, String kind, String quantity);
}
