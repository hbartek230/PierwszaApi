package com.example.bhren.myapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.bhren.myapplication.Common.ItemState;
import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.Common.TempOrderBillWithState;
import com.example.bhren.myapplication.Model.TempOrder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.Observable;

public class TempOrderService {
    private static final String REFERENCE_KEY = "TempOrder";

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference(REFERENCE_KEY);

    public Observable<TempOrderBillWithState> getTempOrderBiillsList() {
        return Observable.create(emitter -> database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                emitter.onNext(TempOrderBillWithState.builder()
                        .itemState(ItemState.ADDED)
                        .tempOrderBill(TempOrderBill.builder()
                                .tempOrder(dataSnapshot.getValue(TempOrder.class))
                                .key(dataSnapshot.getKey())
                                .build())
                        .build());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                emitter.onNext(TempOrderBillWithState.builder()
                        .itemState(ItemState.CHANGED)
                        .tempOrderBill(TempOrderBill.builder()
                                .tempOrder(dataSnapshot.getValue(TempOrder.class))
                                .key(dataSnapshot.getKey())
                                .build())
                        .build());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                emitter.onNext(TempOrderBillWithState.builder()
                        .itemState(ItemState.DELETED)
                        .tempOrderBill(TempOrderBill.builder()
                                .tempOrder(dataSnapshot.getValue(TempOrder.class))
                                .key(dataSnapshot.getKey())
                                .build())
                        .build());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //NOPE
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                emitter.onError(databaseError.toException());
            }
        }));
    }

    public void deleteItemFromFirebase(String key) {
        database.child(key).removeValue();
    }
}
