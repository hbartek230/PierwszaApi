package com.example.bhren.myapplication.Inteface;

public interface OrderClickListener {
    void onOrderDelete(String key);
    void onOrderEdit(String honeyName, String key, String currentQuantity, String currentPrice);
}
