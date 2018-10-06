package com.example.bhren.myapplication.EditOrder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditOrderActivity extends AppCompatActivity implements EditOrderContract.View {

    @BindView(R.id.twCurrentQuantity)
    protected TextView twCurrentQuantity;

    @BindView(R.id.twCurrentPrice)
    protected TextView twCurrentPrice;

    @BindView(R.id.etNewQuantity)
    protected EditText etNewQuantity;

    @BindView(R.id.etNewPrice)
    protected EditText etNewPrice;

    @BindView(R.id.changePriceSwitcher)
    protected Switch priceSwitcher;

    private String quantity, price;
    private Unbinder unbinder;
    private EditOrderPresenter editPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        editPresenter = new EditOrderPresenter();
        editPresenter.setView(this);
        generateView();
    }

    private void generateView() {
        unbinder = ButterKnife.bind(this);
        quantity = getIntent().getExtras().getString("tempOrderBillQuantity");
        price = getIntent().getExtras().getString("tempOrderBillPrice");
        editPresenter.viewCreated(quantity, price);
    }

    @Override
    public void showCurrentValuesView(String quantity, String price) {
        twCurrentQuantity.setText(quantity);
        twCurrentPrice.setText(price);
        System.out.println("TEŚCIK");
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
