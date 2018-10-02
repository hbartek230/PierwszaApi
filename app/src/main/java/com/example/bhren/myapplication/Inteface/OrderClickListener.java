package com.example.bhren.myapplication.Inteface;

import com.example.bhren.myapplication.Common.TempOrderBill;

public interface OrderClickListener {
    void onOrderDelete(TempOrderBill tempOrderBill);
    void onOrderEdit(TempOrderBill tempOrderBill);
}
