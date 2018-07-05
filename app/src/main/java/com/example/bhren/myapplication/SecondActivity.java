package com.example.bhren.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button AddHarvest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        AddHarvest = findViewById(R.id.btnAddHarvest);

        AddHarvest.setOnClickListener((v)->{
            startActivity(new Intent(SecondActivity.this, AddHarvestActivity.class));
            finish();
                }
        );

        }

}
