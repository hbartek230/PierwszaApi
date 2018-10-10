package com.example.bhren.myapplication.EditOrder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    Bundle retrieveBundle;
    TempOrderBill tempOrderBill;
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
        retrieveBundle = getIntent().getExtras();
        tempOrderBill = (TempOrderBill) retrieveBundle.getSerializable("tempOrderBill");
        editPresenter.viewCreated(tempOrderBill);
    }

    @Override
    public void showCurrentValuesView(TempOrderBill tempOrderBill) {
        twCurrentQuantity.setText(tempOrderBill.getTempOrder().getQuantity());
        twCurrentPrice.setText(tempOrderBill.getTempOrder().getAmount());
        System.out.println("TEŚCIK");
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
