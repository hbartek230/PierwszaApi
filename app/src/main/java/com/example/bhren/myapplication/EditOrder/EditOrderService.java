package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Model.TempOrder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditOrderService {

    private static final String REFERENCE_KEY = "TempOrder";
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference(REFERENCE_KEY);

    public void editOrderInFirebase(String key, String price, String kind, String quantity){
        TempOrder updatedData = new TempOrder(price, kind, quantity);
        database.child(key).setValue(updatedData);
        System.out.println("EditOrderService");
    }

}
