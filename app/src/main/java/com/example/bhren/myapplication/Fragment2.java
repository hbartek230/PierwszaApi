package com.example.bhren.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.Inteface.OrderClickListener;
import com.example.bhren.myapplication.Model.TempOrder;
import com.example.bhren.myapplication.ViewHolder.OrderAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment implements OrderClickListener {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference recyclerRef = database.getReference("TempOrder");
    OrderAdapter adapter;
    List<TempOrderBill> tempOrderData;
    Context c;
    private int index;
    private RecyclerView order_recycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_frag2, container, false);
        order_recycler = fragmentView.findViewById(R.id.orderRecyclerTest);
        order_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager rlm = new LinearLayoutManager(getContext());
        order_recycler.setLayoutManager(rlm);
        order_recycler.setItemAnimator(new DefaultItemAnimator());
        order_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        tempOrderData = new ArrayList<>();
        adapter = new OrderAdapter(tempOrderData, this);
        getFirebaseData();

        return fragmentView;
    }


    public void getFirebaseData() {
        recyclerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TempOrder tempOrderItem = dataSnapshot.getValue(TempOrder.class);
                tempOrderData.add(new TempOrderBill(dataSnapshot.getKey(), tempOrderItem));
                order_recycler.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TempOrder tempOrderItem = dataSnapshot.getValue(TempOrder.class);
                tempOrderData.set(index, (new TempOrderBill(dataSnapshot.getKey(), tempOrderItem)));
                adapter.notifyDataSetChanged();
                order_recycler.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                findChildKey(dataSnapshot.getKey());
                System.out.println("Index: " + index);
                if (index != -1) {
                    tempOrderData.remove(index);
                    adapter.notifyDataSetChanged();
                    order_recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private int findChildKey(String key) {
        for (int i = 0; i < tempOrderData.size(); i++) {
            if (tempOrderData.get(i).getKey().equals(key))
                index = i;

        }
        return index;
    }

    public void deleteFromFirebase(String key) {
        recyclerRef.child(key).removeValue();

    }

    @Override
    public void onOrderDelete(String key) {
        deleteFromFirebase(key);
    }

    public void showUpdateDialog(String currentQuantity, String currentPrice) {

        AlertDialog.Builder buildDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View showDialog = inflater.inflate(R.layout.update_dialog, null);
        buildDialog.setView(showDialog);

        final TextView twCurrentQuantity = (TextView) showDialog.findViewById(R.id.twCurrentQuantity);
        final TextView twCurrentPrice = (TextView) showDialog.findViewById(R.id.twCurrentPrice);
        final Button btnSave = (Button) showDialog.findViewById(R.id.btnSave);

        twCurrentQuantity.setText(currentQuantity);
        twCurrentPrice.setText(currentPrice);

        AlertDialog updateDialog = buildDialog.create();
        updateDialog.show();

    }

    public void onOrderEdit(String currentQuantity, String currentPrice){
        showUpdateDialog(currentQuantity, currentPrice);
    }
}
