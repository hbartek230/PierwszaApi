package com.example.bhren.myapplication.EditOrder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FirebaseEditOrderRepository implements EditOrderRepository {

    private final EditOrderService editOrderService;

    @Override
    public void editOrderItem(String key, String price, String kind, String quantity) {
        editOrderService.editOrderInFirebase(key, price, kind, quantity);
        System.out.println("FirebaseEditOrderRepository");
    }
}
