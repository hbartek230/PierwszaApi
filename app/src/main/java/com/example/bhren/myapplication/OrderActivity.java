package com.example.bhren.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhren.myapplication.Model.Amount;
import com.example.bhren.myapplication.Model.Harvest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_harvest = database.getReference("Harvest");
    final DatabaseReference table_amount = database.getReference("Amount");
    private TextView twBigGlass;
    private TextView twSmallGlass;
    private EditText twAmount;
    private TextView twSummary;
    private EditText twQuantity;
    private Spinner honeySpinner;
    private Switch switcherPrice;
    private int singlePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        twBigGlass = findViewById(R.id.textBigGlass);
        twSmallGlass = findViewById(R.id.textSmallGlass);
        honeySpinner = findViewById(R.id.orderHoneySpinner);
        twAmount = findViewById(R.id.textAmount);
        twQuantity = findViewById(R.id.textQuantity);
        switcherPrice = findViewById(R.id.switcherPrice);
        twSummary = findViewById(R.id.twSummary);

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
                }else{
                    twSummary.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                    Toast.makeText(OrderActivity.this, "Oups..Takiego miodu nie ma w bazie", Toast.LENGTH_SHORT).show();
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
            } else {
                twAmount.setEnabled(false);
                getAmount();
            }
        });
    }

}
