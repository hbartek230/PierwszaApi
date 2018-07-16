package com.example.bhren.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
    private Spinner honeySpinner;
    private Switch kgSwitcher, glassSwitcher;
    private int currentKilo, oldKilo, summaryKilo, currentGlasses, oldGlasses, summaryGlasses;

    DatabaseReference table_harvest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_harvest);
        etKilo = findViewById(R.id.etKilos);
        etGlasses = findViewById(R.id.etGlasses);
        btnAddHarv = findViewById(R.id.addHarv);
        honeySpinner = findViewById(R.id.honeySpinner);
        kgSwitcher = findViewById(R.id.kgSwitcher);
        glassSwitcher = findViewById(R.id.glassSwitcher);

        kgSwitcher.setChecked(false);
        glassSwitcher.setChecked(false);
        etKilo.setEnabled(false);
        etGlasses.setEnabled(false);
        table_harvest = FirebaseDatabase.getInstance().getReference("Harvest");
        switchers();

        btnAddHarv.setOnClickListener(v -> {
            if (kgSwitcher.isChecked() && glassSwitcher.isChecked()) {
                setHoneyGlasses();
                setHoneyKilo();
            } else if (kgSwitcher.isChecked()) {
                setHoneyKilo();
            } else if (glassSwitcher.isChecked()) {
                setHoneyGlasses();
            }
        });
    }

    private void setHoneyKilo() {
        getHoneyKilo();
        getHoneyGlasses();
        table_harvest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                    if (etKilo.getText().toString().trim().isEmpty()) {
                        Toast.makeText(AddHarvestActivity.this, "Nie wprowadzono danych", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseReference addHarvRef = table_harvest.child(honeySpinner.getSelectedItem().toString());
                        currentKilo = Integer.parseInt(etKilo.getText().toString());
                        summaryKilo = oldKilo + currentKilo;
                        Harvest setHarvest = new Harvest(Integer.toString(summaryKilo), Integer.toString(oldGlasses));
                        addHarvRef.setValue(setHarvest);
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

    private void getHoneyKilo() {
        table_harvest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                    Harvest harvest = dataSnapshot.child(honeySpinner.getSelectedItem().toString()).getValue(Harvest.class);
                    oldKilo = Integer.parseInt(harvest.getKilo());
                } else {
                    Toast.makeText(AddHarvestActivity.this, "Nie mamy takiego moidu w składzie", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setHoneyGlasses() {
        getHoneyGlasses();
        getHoneyKilo();
        table_harvest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                    if (etGlasses.getText().toString().trim().isEmpty()) {
                        Toast.makeText(AddHarvestActivity.this, "Nie wprowadzono danych", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseReference addHarvRef = table_harvest.child(honeySpinner.getSelectedItem().toString());
                        currentGlasses = Integer.parseInt(etGlasses.getText().toString());
                        summaryGlasses = oldGlasses + currentGlasses;
                        Harvest setHarvest = new Harvest(Integer.toString(oldKilo), Integer.toString(summaryGlasses));
                        addHarvRef.setValue(setHarvest);
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

    private void getHoneyGlasses() {
        table_harvest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                    Harvest harvest = dataSnapshot.child(honeySpinner.getSelectedItem().toString()).getValue(Harvest.class);
                    oldGlasses = Integer.parseInt(harvest.getGlasses());
                } else {
                    Toast.makeText(AddHarvestActivity.this, "Nie mamy takiego moidu w składzie", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void switchers() {
        kgSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etKilo.setEnabled(true);
            } else {
                etKilo.setEnabled(false);
                etKilo.setText("");
            }
        });

        glassSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etGlasses.setEnabled(true);
            } else {
                etGlasses.setEnabled(false);
                etGlasses.setText("");
            }
        });
    }
}
