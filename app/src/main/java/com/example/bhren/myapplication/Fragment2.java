package com.example.bhren.myapplication;

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

import com.example.bhren.myapplication.Model.TempOrder;
import com.example.bhren.myapplication.ViewHolder.OrderAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference recyclerRef = database.getReference("TempOrder");
    OrderAdapter adapter;
    List<TempOrder> tempOrderData;
    ArrayList<String> indexList;
    private RecyclerView order_recycler;
    Context c;

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
        indexList = new ArrayList<String>();
        adapter = new OrderAdapter(tempOrderData);
        GetFirebaseData();

        return fragmentView;
    }


    public void GetFirebaseData() {
        recyclerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TempOrder getData = dataSnapshot.getValue(TempOrder.class);
                tempOrderData.add(getData);
                indexList.add(dataSnapshot.getKey());
                order_recycler.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TempOrder getData = dataSnapshot.getValue(TempOrder.class);
                String key = dataSnapshot.getKey();
                int index = indexList.indexOf(key);
                tempOrderData.set(index, getData);
                adapter.notifyDataSetChanged();
                order_recycler.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = indexList.indexOf(key);
                if (index != -1) {
                    tempOrderData.remove(index);
                    indexList.remove(index);
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

    public void refreshData(DataSnapshot firebaseSnapshot) {
        if (firebaseSnapshot.exists()) {
            for (DataSnapshot childSnapshot : firebaseSnapshot.getChildren()) {
                TempOrder data = childSnapshot.getValue(TempOrder.class);
                tempOrderData.add(data);
            }
        }
    }
}
