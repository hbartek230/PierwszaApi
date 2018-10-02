package com.example.bhren.myapplication;

import com.example.bhren.myapplication.Common.TempOrderBillWithState;

import io.reactivex.Observable;

public interface TempOrderRepository {

    Observable<TempOrderBillWithState> getTempOrders();

    void deleteTempOrderItem(String key);
}
