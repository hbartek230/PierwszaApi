package com.example.bhren.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bhren.myapplication.Model.Harvest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHarvestActivity extends AppCompatActivity {

    private EditText etKilo;
    private EditText etGlasses;
    private Button btnAddHarv;
    private Button btnChckHarv;
    private Spinner honeySpinner;
    private EditText summ;
    private EditText honeyKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_harvest);
        etKilo = findViewById(R.id.etKilos);
        etGlasses = findViewById(R.id.etGlasses);
        btnAddHarv = (Button) findViewById(R.id.addHarv);
        btnChckHarv = findViewById(R.id.chckHarv);
        honeySpinner = findViewById(R.id.honeySpinner);
        summ = findViewById(R.id.etSummary);
        honeyKind = findViewById(R.id.etHoneyKind);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_harvest = database.getReference("Harvest");

        btnAddHarv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_harvest.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                            if (etKilo.getText().toString().equals("") || etGlasses.getText().toString().equals("")) {
                                Toast.makeText(AddHarvestActivity.this, "Nie wprowadzono danych", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference addHarvRef = database.getReference("Harvest").child(honeySpinner.getSelectedItem().toString());
                                Harvest harvest = new Harvest(etKilo.getText().toString(), etGlasses.getText().toString());
                                addHarvRef.setValue(harvest);
                                Toast.makeText(AddHarvestActivity.this, "Zmieniono zbiór", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddHarvestActivity.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddHarvestActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnChckHarv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_harvest.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                            honeyKind.setText(honeySpinner.getSelectedItem().toString());
                            Harvest harvest = dataSnapshot.child(honeySpinner.getSelectedItem().toString()).getValue(Harvest.class);
                            summ.setText(harvest.getKilo().toString());
                        } else {
                            Toast.makeText(AddHarvestActivity.this, "Nie mamy takiego moidu w składzie", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
