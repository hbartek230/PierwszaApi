package com.example.bhren.myapplication.Inteface;

public interface OrderClickListener {
    void onOrderDelete(String key);
    void onOrderEdit(String currentQuantity, String currentPrice);
}
