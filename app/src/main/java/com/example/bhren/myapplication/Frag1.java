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

import com.example.bhren.myapplication.Model.Amount;
import com.example.bhren.myapplication.Model.Harvest;
import com.example.bhren.myapplication.Model.TempOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Frag1 extends Fragment {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_harvest = database.getReference("Harvest");
    final DatabaseReference table_amount = database.getReference("Amount");
    final DatabaseReference tabel_temporder = database.getReference("TempOrder");

    private TextView twBigGlass;
    private TextView twSmallGlass;
    private TextView twSummary;
    private EditText twAmount;
    private EditText twQuantity;
    private EditText etCustName;
    private Spinner honeySpinner;
    private Switch switcherPrice;
    private Button btnAddToCart;
    private int singlePrice;
    private int currentId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);
        twBigGlass = view.findViewById(R.id.textBigGlass);
        twSmallGlass = view.findViewById(R.id.textSmallGlass);
        honeySpinner = view.findViewById(R.id.orderHoneySpinner);
        twAmount = view.findViewById(R.id.textAmount);
        twQuantity = view.findViewById(R.id.textQuantity);
        switcherPrice = view.findViewById(R.id.switcherPrice);
        twSummary = view.findViewById(R.id.twDefaultPrice);
        etCustName = view.findViewById(R.id.etCustName);
        btnAddToCart = view.findViewById(R.id.btnAddToCart);

        getGlasses();
        getAmount();
        customPrice();

        honeySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGlasses();
                twQuantity.setText("");
                twSummary.setText("0");
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
                setPrice();
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
                    setPrice();
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
        return view;

    }

    private void addToCart() {
        tabel_temporder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentId+=1;
                DatabaseReference addCartRef = tabel_temporder.child(Integer.toString(currentId).trim());
                if(twQuantity.toString().isEmpty()){
                    Toast.makeText(getActivity(), "Nie wprowadzono ilości produktu", Toast.LENGTH_SHORT).show();
                }else if(twAmount.toString().isEmpty()){
                    Toast.makeText(getActivity(), "Nie wprowadzono kwoty", Toast.LENGTH_SHORT).show();
                }else if(etCustName.toString().isEmpty()){
                    Toast.makeText(getActivity(), "Nie wprowadzono nazwy kupującego", Toast.LENGTH_SHORT).show();
                }else{
                    TempOrder setToCart = new TempOrder(twSummary.getText().toString(), honeySpinner.getSelectedItem().toString(), twQuantity.getText().toString());
                    addCartRef.setValue(setToCart);
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
        table_amount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                    Amount amount = dataSnapshot.child(honeySpinner.getSelectedItem().toString()).getValue(Amount.class);
                    twAmount.setText(amount.getAmount());
                    singlePrice = Integer.parseInt(amount.getAmount());
                } else {
                    Toast.makeText(getActivity(), "Oups..Takiego miodu nie ma w bazie", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setPrice() {
        if (twQuantity.getText().toString().isEmpty()) {
            if (switcherPrice.isChecked()) {
                twSummary.setText("0");
            } else {
                getAmount();
                twSummary.setText("0");
            }

        } else {
            returnPrice();
        }
    }

    public void returnPrice() {
        if (switcherPrice.isChecked()) {
            int quantity = Integer.parseInt(String.valueOf(twQuantity.getText().toString()));
            int singlePr = Integer.parseInt(String.valueOf(twAmount.getText().toString().trim()));
            int summaryPrice;
            summaryPrice = quantity * singlePr;
            twSummary.setText(Integer.toString(summaryPrice));
        } else {
            int quantity = Integer.parseInt(String.valueOf(twQuantity.getText().toString()));
            int singlePr = singlePrice;
            int summaryPrice;
            summaryPrice = quantity * singlePr;
            twSummary.setText(Integer.toString(summaryPrice));
        }
    }

    public void customPrice() {
        switcherPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                twAmount.setText("");
                twAmount.setEnabled(true);
                twSummary.setText("0");
            } else {
                twAmount.setEnabled(false);
                getAmount();
                twSummary.setText("0");
            }
        });
    }

}
