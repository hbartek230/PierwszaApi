package com.example.bhren.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.GeneralMethods.OrderMethods;
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

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference recyclerRef = database.getReference("TempOrder");
    OrderAdapter adapter;
    List<TempOrderBill> tempOrderData;
    private OrderMethods controller = new OrderMethods();
    private int index;
    private RecyclerView order_recycler;
    private TextView twCurrentQuantity;
    private TextView twCurrentPrice;
    private EditText etNewQuantity;
    private EditText etNewPrice;
    private Button btnSave;
    private String firebaseKey;
    private String honeyName;
    private String honeyAmount;
    private Switch priceSwitcher;


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
        order_recycler.setAdapter(adapter);
        getFirebaseData();

        return fragmentView;
    }


    public void getFirebaseData() {
        recyclerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TempOrder tempOrderItem = dataSnapshot.getValue(TempOrder.class);
                tempOrderData.add(new TempOrderBill(dataSnapshot.getKey(), tempOrderItem));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                findChildKey(dataSnapshot.getKey());
                TempOrder tempOrderItem = dataSnapshot.getValue(TempOrder.class);
                tempOrderData.set(index, (new TempOrderBill(dataSnapshot.getKey(), tempOrderItem)));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                findChildKey(dataSnapshot.getKey());
                if (index != -1) {
                    tempOrderData.remove(index);
                    adapter.notifyDataSetChanged();
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
            if (tempOrderData.get(i).getKey().equals(key)) {
                index = i;
            }
        }
        return index;
    }

    public void deleteFromFirebase(String key) {
        recyclerRef.child(key).removeValue();

    }

    public void showUpdateDialog(String orderItem, String key, String currentQuantity, String currentPrice) {

        AlertDialog.Builder buildDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View showDialog = inflater.inflate(R.layout.update_dialog, null);
        buildDialog.setView(showDialog);

        twCurrentQuantity = (TextView) showDialog.findViewById(R.id.twCurrentQuantity);
        twCurrentPrice = (TextView) showDialog.findViewById(R.id.twCurrentPrice);
        etNewQuantity = (EditText) showDialog.findViewById(R.id.etNewQuantity);
        etNewPrice = (EditText) showDialog.findViewById(R.id.etNewPrice);
        btnSave = (Button) showDialog.findViewById(R.id.btnSave);
        priceSwitcher = (Switch) showDialog.findViewById(R.id.changePriceSwitcher);

        twCurrentQuantity.setText(currentQuantity);
        twCurrentPrice.setText(currentPrice);
        firebaseKey = key;
        honeyName = orderItem;

        AlertDialog updateDialog = buildDialog.create();
        updateDialog.show();

        controller.getHoneyAmount(amount -> honeyAmount = amount, honeyName);
        customPrice();
        etNewQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etNewQuantity.getText().toString().isEmpty()) {
                    setPrice();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(v -> {
            if (etNewQuantity.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Nie wprowadzono ilości", Toast.LENGTH_SHORT).show();
            } else if (priceSwitcher.isChecked() && etNewPrice.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Nie wprowadzono ceny", Toast.LENGTH_SHORT).show();
            } else {
                updateFirebaseData(honeyName, firebaseKey, etNewQuantity.getText().toString(), etNewPrice.getText().toString());
                updateDialog.dismiss();
            }
        });
    }

    private void customPrice() {
        priceSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etNewPrice.setText("");
                etNewPrice.setEnabled(true);
            } else {
                etNewPrice.setEnabled(false);
                etNewPrice.setText(Integer.toString(controller.returnOrderPrice(
                        Integer.parseInt(honeyAmount),
                        Integer.parseInt(etNewQuantity.getText().toString()))));
            }
        });
    }

    private void setPrice() {
        if (!priceSwitcher.isChecked()) {
            etNewPrice.setText(Integer.toString(controller.returnOrderPrice(
                    Integer.parseInt(honeyAmount),
                    Integer.parseInt(etNewQuantity.getText().toString()))));
        }
    }

    public void updateFirebaseData(String honeyName, String key, String newQuantiity, String newPrice) {
        TempOrder updatedOrder = new TempOrder(newPrice, honeyName, newQuantiity);
        recyclerRef.child(key).setValue(updatedOrder);
    }

    @Override
    public void onOrderDelete(String key) {
        deleteFromFirebase(key);
    }

    @Override
    public void onOrderEdit(String honeyName, String key, String currentQuantity, String currentPrice) {
        showUpdateDialog(honeyName, key, currentQuantity, currentPrice);
    }
}
