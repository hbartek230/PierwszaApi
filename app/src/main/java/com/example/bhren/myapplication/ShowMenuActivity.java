package com.example.bhren.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ShowMenuActivity extends AppCompatActivity {
    private Button btnAddHarvest;
    private Button btnAddSell;
    private Button btnViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btnAddHarvest = findViewById(R.id.btnAddHarvest);
        btnAddSell = findViewById(R.id.btnAddSell);
        btnViewHistory = findViewById(R.id.btnViewHistory);


        btnAddHarvest.setOnClickListener((v)->{
            startActivity(new Intent(ShowMenuActivity.this, AddHarvestActivity.class));
                }
        );

        btnAddSell.setOnClickListener((v -> {
            startActivity(new Intent(ShowMenuActivity.this, OrderActivity.class));
        }));

        }

}
