package com.example.bhren.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhren.myapplication.GeneralMethods.OrderMethods;
import com.example.bhren.myapplication.Model.Harvest;
import com.example.bhren.myapplication.Model.TempOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment1 extends Fragment {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference table_harvest = database.getReference("Harvest");
    private final DatabaseReference table_temporder = database.getReference("TempOrder");
    OrderMethods controller = new OrderMethods();
    private Spinner honeySpinner;
    private TextView twBigGlass;
    private TextView twSmallGlass;
    private EditText twAmount;
    private TextView twSummary;
    private EditText twQuantity;
    private Switch switcherPrice;
    private Button btnAddToCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_frag1, container, false);
        twBigGlass = fragmentView.findViewById(R.id.textBigGlass);
        twSmallGlass = fragmentView.findViewById(R.id.textSmallGlass);
        honeySpinner = fragmentView.findViewById(R.id.orderHoneySpinner);
        twAmount = fragmentView.findViewById(R.id.textAmount);
        twQuantity = fragmentView.findViewById(R.id.textQuantity);
        switcherPrice = fragmentView.findViewById(R.id.switcherPrice);
        twSummary = fragmentView.findViewById(R.id.twDefaultPrice);
        btnAddToCart = fragmentView.findViewById(R.id.btnAddToCart);

        getGlasses();
        getAmount();
        customPrice();

        honeySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGlasses();
                twQuantity.setText("");
                getAmount();
                switcherPrice.setChecked(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        twQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!twAmount.getText().toString().isEmpty()) {
                    setSummaryPrice();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        twAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!twAmount.getText().toString().isEmpty()) {
                    setSummaryPrice();
                } else {
                    twSummary.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAddToCart.setOnClickListener(v -> {
            addToCart();
        });

        return fragmentView;
    }

    private void addToCart() {
        table_temporder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference addCartRef = table_temporder.push();
                if (twQuantity.getText().toString().isEmpty() || twQuantity.getText().toString().equals("0")) {
                    Toast.makeText(getActivity(), "Nie wprowadzono ilości", Toast.LENGTH_SHORT).show();
                }
                if (twAmount.getText().toString().isEmpty() || twAmount.getText().toString().equals("0")) {
                    Toast.makeText(getActivity(), "Nie wprowadzono kwoty", Toast.LENGTH_SHORT).show();
                } else {
                    TempOrder setToCart = new TempOrder(twSummary.getText().toString(), honeySpinner.getSelectedItem().toString(), twQuantity.getText().toString());
                    addCartRef.setValue(setToCart);
                    Toast.makeText(getContext(), "Dodano do zamównienia", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getGlasses() {
        table_harvest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                    Harvest harvest = dataSnapshot.child(honeySpinner.getSelectedItem().toString()).getValue(Harvest.class);
                    twBigGlass.setText(harvest.getBigGlasses());
                    twSmallGlass.setText(harvest.getSmallGlasses());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAmount() {
        controller.getHoneyAmount(amount -> twAmount.setText(amount), honeySpinner.getSelectedItem().toString());
    }

    public void setSummaryPrice() {
        if (twQuantity.getText().toString().isEmpty()) {
            if (switcherPrice.isChecked()) {
                twSummary.setText("0");
            } else {
                getAmount();
                twSummary.setText("0");
            }

        } else {
            twSummary.setText(Integer.toString(controller
                    .returnOrderPrice(Integer.parseInt(String.valueOf(twAmount.getText().toString()))
                            , Integer.parseInt(String.valueOf(twQuantity.getText().toString())))));
        }
    }


    public void customPrice() {
        switcherPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                twAmount.setText("");
                twAmount.setEnabled(true);
            } else {
                twAmount.setEnabled(false);
                getAmount();
            }
        });
    }
}

