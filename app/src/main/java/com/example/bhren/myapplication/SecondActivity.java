package com.example.bhren.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button AddHarvest;
    private Button AddSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        AddHarvest = findViewById(R.id.btnAddHarvest);
        AddSell = findViewById(R.id.btnAddSell);

        AddHarvest.setOnClickListener((v)->{
            startActivity(new Intent(SecondActivity.this, AddHarvestActivity.class));
                }
        );

        AddSell.setOnClickListener((v -> {
            startActivity(new Intent(SecondActivity.this, tabbedTest.class));
        }));

        }

}
