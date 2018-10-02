package com.example.bhren.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowMenuActivity extends AppCompatActivity {
    @BindView(R.id.btnAddHarvest)
    protected Button btnAddHarvest;

    @BindView(R.id.btnAddSell)
    protected Button btnAddSell;
    @BindView(R.id.btnViewHistory)
    protected Button btnViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnAddHarvest)
    public void onAddHarvestPressed() {
        startActivity(new Intent(ShowMenuActivity.this, AddHarvestActivity.class));
    }

    @OnClick(R.id.btnAddSell)
    public void onAddSellPressed() {
        startActivity(new Intent(ShowMenuActivity.this, OrderActivity.class));
    }

}
