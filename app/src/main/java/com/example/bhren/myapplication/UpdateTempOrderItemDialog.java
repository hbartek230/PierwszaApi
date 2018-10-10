/*package com.example.bhren.myapplication;

import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhren.myapplication.GeneralMethods.OrderMethods;
import com.example.bhren.myapplication.Inteface.FirebaseCallback;

import butterknife.BindView;

public class UpdateTempOrderItemDialog {

    @BindView(R.id.etNewQuantity)
    private EditText etNewQuantity;

    @BindView(R.id.etNewPrice)
    private EditText etNewPrice;

    @BindView(R.id.btnSave)
    private Button btnSave;

    @BindView(R.id.changePriceSwitcher)
    private Switch priceSwitcher;

    @BindView(R.id.twCurrentQuantity)
    private TextView twCurrentQuantity;

    @BindView(R.id.twCurrentPrice)
    private TextView twCurrentPrice;

    private OrderMethods controller;

    public void showUpdateDialog(String orderItem, String key, String currentQuantity, String currentPrice) {

        AlertDialog.Builder buildDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View showDialog = inflater.inflate(R.layout.update_dialog, null);
        buildDialog.setView(showDialog);

        twCurrentQuantity.setText(currentQuantity);
        twCurrentPrice.setText(currentPrice);
        firebaseKey = key;
        honeyName = orderItem;

        AlertDialog updateDialog = buildDialog.create();
        updateDialog.show();

        controller.getHoneyAmount(new FirebaseCallback() {
            @Override
            public void onGetHoneyNameCallback(String amount) {
                honeyAmount = amount;
            }
        }, honeyName);
        customPrice();
        etNewQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etNewQuantity.getText().toString().isEmpty()) {
                    setPrice();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSave.setOnClickListener(v -> {
            if (etNewQuantity.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Nie wprowadzono ilości", Toast.LENGTH_SHORT).show();
            } else if (priceSwitcher.isChecked() && etNewPrice.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Nie wprowadzono ceny", Toast.LENGTH_SHORT).show();
            } else {
                updateFirebaseData(honeyName, firebaseKey, etNewQuantity.getText().toString(), etNewPrice.getText().toString());
                updateDialog.dismiss();
            }
        });
    }

    private void customPrice() {
        priceSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etNewPrice.setText("");
                etNewPrice.setEnabled(true);
            } else {
                etNewPrice.setEnabled(false);
                etNewPrice.setText(Integer.toString(controller.returnOrderPrice(
                        Integer.parseInt(honeyAmount),
                        Integer.parseInt(etNewQuantity.getText().toString()))));
            }
        });
    }


    private void setPrice() {
        if (!priceSwitcher.isChecked()) {
            etNewPrice.setText(Integer.toString(controller.returnOrderPrice(
                    Integer.parseInt(honeyAmount),
                    Integer.parseInt(etNewQuantity.getText().toString()))));
        }
    }

}
*/