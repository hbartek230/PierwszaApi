package com.example.bhren.myapplication.EditOrder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
    private String honeyPrice;


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
        etNewPrice.setText("0");
        editPresenter.createFinalView(tempOrderBill);
    }

    @Override
    public void showCurrentValuesView(TempOrderBill tempOrderBill) {
        twCurrentQuantity.setText(tempOrderBill.getTempOrder().getQuantity());
        twCurrentPrice.setText(tempOrderBill.getTempOrder().getAmount());
    }

    @Override
    public void setMainPriceView(String mainHoneyPrice) {
        honeyPrice = mainHoneyPrice;
        editPresenter.priceSwitcherChecked(priceSwitcher.isChecked());
        editTextQuantityTypped();
    }

    @Override
    public void setPriceEditableView() {
        priceSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etNewPrice.setText("");
                etNewPrice.setEnabled(true);
            } else {
                etNewPrice.setEnabled(false);
                editPresenter.switcherNotChecked(Integer.parseInt(honeyPrice), Integer.parseInt(etNewQuantity.getText().toString()));
            }
        });
    }

    @Override
    public void setHoneyPriceView(int summaryHoneyPrice) {
        etNewPrice.setText(Integer.toString(summaryHoneyPrice));
    }

    private void editTextQuantityTypped() {
        etNewQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etNewQuantity.getText().toString().trim().isEmpty() && !priceSwitcher.isChecked()) {
                    String quantity = etNewQuantity.getText().toString().trim();
                    editPresenter.newQuantityTypped(honeyPrice, quantity);
                } else {
                    etNewPrice.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
