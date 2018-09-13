package com.example.bhren.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhren.myapplication.Model.Harvest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHarvestActivity extends AppCompatActivity {

    private EditText etKilo;
    private EditText etBigGlasses;
    private EditText etSmallGlasses;
    private Button btnAddHarv;
    private Spinner honeySpinner;
    private Switch kgSwitcher;
    private Switch bgSwitcher;
    private Switch sgSwitcher;
    private Switch glassSwitcher;
    private int currentKilo = 0, oldKilo, summaryKilo, currentBigGlasses = 0, oldBigGlasses, summaryBigGlasses, currentSmallGlasses = 0, oldSmallGlasses, summarySmallGlasses;

    DatabaseReference table_harvest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_harvest);
        etKilo = findViewById(R.id.etKilos);
        kgSwitcher = findViewById(R.id.kgSwitcher);
        etBigGlasses = findViewById(R.id.bgEditText);
        bgSwitcher = findViewById(R.id.bgSwitcher);
        etSmallGlasses = findViewById(R.id.sgEditText);
        sgSwitcher = findViewById(R.id.sgSwitcher);
        btnAddHarv = findViewById(R.id.addHarv);
        honeySpinner = findViewById(R.id.honeySpinner);
        glassSwitcher = findViewById(R.id.glassSwitcher);

        kgSwitcher.setChecked(false);
        bgSwitcher.setChecked(false);
        sgSwitcher.setChecked(false);
        bgSwitcher.setEnabled(false);
        sgSwitcher.setEnabled(false);
        etKilo.setEnabled(false);
        etBigGlasses.setEnabled(false);
        etSmallGlasses.setEnabled(false);
        table_harvest = FirebaseDatabase.getInstance().getReference("Harvest");
        switchers();

        btnAddHarv.setOnClickListener(v -> {
            setHoney();
        });


        honeySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDefault();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Adding kilograms, small and/or big glasses to Firebase
    private void setHoney() {
        table_harvest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(honeySpinner.getSelectedItem().toString()).exists()) {
                    DatabaseReference addHarvRef = table_harvest.child(honeySpinner.getSelectedItem().toString());
                    Harvest harvest = dataSnapshot.child(honeySpinner.getSelectedItem().toString()).getValue(Harvest.class);
                    if (kgSwitcher.isChecked()) {
                        if (!etKilo.getText().toString().isEmpty()) {
                            currentKilo = Integer.parseInt(etKilo.getText().toString().trim());
                        } else {
                            Toast.makeText(AddHarvestActivity.this, "Nie wprowadzono ilości kilo", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (bgSwitcher.isChecked()) {
                        if (!etBigGlasses.getText().toString().isEmpty()) {
                            currentBigGlasses = Integer.parseInt(etBigGlasses.getText().toString().trim());
                        } else {
                            Toast.makeText(AddHarvestActivity.this, "Nie wprowadzono ilości dużych słoików", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (sgSwitcher.isChecked()) {
                        if (!etSmallGlasses.getText().toString().isEmpty()) {
                            currentSmallGlasses = Integer.parseInt(etSmallGlasses.getText().toString().trim());
                        } else {
                            Toast.makeText(AddHarvestActivity.this, "Nie wprowadzono ilości małych słoików", Toast.LENGTH_SHORT).show();
                        }
                    }
                    oldKilo = Integer.parseInt(harvest.getKilo());
                    oldBigGlasses = Integer.parseInt(harvest.getBigGlasses());
                    oldSmallGlasses = Integer.parseInt(harvest.getSmallGlasses());
                    summaryKilo = oldKilo + currentKilo;
                    summaryBigGlasses = oldBigGlasses + currentBigGlasses;
                    summarySmallGlasses = oldSmallGlasses + currentSmallGlasses;
                    Harvest setHarvest = new Harvest(Integer.toString(summaryKilo), Integer.toString(summaryBigGlasses), Integer.toString(summarySmallGlasses));
                    addHarvRef.setValue(setHarvest);
                    Toast.makeText(AddHarvestActivity.this, "Dodano do zbioru", Toast.LENGTH_SHORT).show();
                    setDefault();
                } else {
                    Toast.makeText(AddHarvestActivity.this, "Nie mamy takiego moidu w składzie", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setDefault(){
        etSmallGlasses.setText("0");
        etBigGlasses.setText("0");
        etKilo.setText("0");
    }

    private void switchers() {
        kgSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etKilo.setEnabled(true);
            } else {
                etKilo.setEnabled(false);
                etKilo.setText("");
                currentKilo = 0;
            }
        });

        glassSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bgSwitcher.setEnabled(true);
                sgSwitcher.setEnabled(true);
            } else {
                bgSwitcher.setEnabled(false);
                sgSwitcher.setEnabled(false);
                etBigGlasses.setEnabled(false);
                etBigGlasses.setText("");
                etSmallGlasses.setEnabled(false);
                etSmallGlasses.setText("");
                currentSmallGlasses = 0;
                currentBigGlasses = 0;
            }
        });

        bgSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etBigGlasses.setEnabled(true);
            } else {
                etBigGlasses.setEnabled(false);
                etBigGlasses.setText("");
                currentBigGlasses = 0;
            }
        });

        sgSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etSmallGlasses.setEnabled(true);
            } else {
                etSmallGlasses.setEnabled(false);
                etSmallGlasses.setText("");
                currentSmallGlasses = 0;
            }
        });
    }
}
