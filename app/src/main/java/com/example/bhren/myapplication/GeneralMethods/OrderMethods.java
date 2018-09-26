package com.example.bhren.myapplication.GeneralMethods;

import android.support.annotation.NonNull;

import com.example.bhren.myapplication.Inteface.FirebaseCallback;
import com.example.bhren.myapplication.Model.Amount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderMethods {
    private final static String KEY_AMOUNT = "Amount";
    private final FirebaseDatabase database;
    private final DatabaseReference tableAmount;

    public OrderMethods() {
        database = FirebaseDatabase.getInstance();
        tableAmount = database.getReference(KEY_AMOUNT);
    }

    public void getHoneyAmount(FirebaseCallback firebaseCallback, String spinnerId) {
        tableAmount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(spinnerId).exists()) {
                    Amount amount = dataSnapshot.child(spinnerId).getValue(Amount.class);
                    firebaseCallback.onCallback(amount.getAmount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public int returnOrderPrice(int honeySinglePrice, int honeyQuantity) {

        int singlePrice = honeySinglePrice;
        int quantity = honeyQuantity;
        int summaryPrice;

        summaryPrice = singlePrice * quantity;

        return summaryPrice;
    }
}
