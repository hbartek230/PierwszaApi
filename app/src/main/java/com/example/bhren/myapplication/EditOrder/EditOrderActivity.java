package com.example.bhren.myapplication.EditOrder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.R;

import butterknife.BindView;

public class EditOrderActivity extends AppCompatActivity implements EditOrderContract.View{

    @BindView(R.id.twCurrentQuantity)
    protected TextView twCurrentQuantity;

    @BindView(R.id.twCurrentPrice)
    protected TextView twCurrentPrice;

    private EditOrderPresenter editPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        editPresenter = new EditOrderPresenter();
        editPresenter.setView(this);
        //editPresenter.viewCreated();
    }

    @Override
    public void showCurrentValuesView(TempOrderBill tempOrderBill) {
        //twCurrentQuantity.setText(tempOrderBill.getTempOrder().getQuantity());
        //twCurrentPrice.setText("60");
        System.out.println("TEŚCIK");
    }
}
