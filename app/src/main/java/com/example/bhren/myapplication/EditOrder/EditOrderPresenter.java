package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;

public class EditOrderPresenter implements EditOrderContract.Presenter {

    public TempOrderBill tempOrderBill;
    private EditOrderContract.View view;
    private String editedQuantity;

    public void setView(EditOrderContract.View view) {
        this.view = view;
    }

    public void viewCreated(String quantity, String price){
        //this.editedQuantity = quantity;
        view.showCurrentValuesView(quantity, price);
    }

    @Override
    public void editOrderSavePressed(TempOrderBill tempOrderBill) {

    }
}
