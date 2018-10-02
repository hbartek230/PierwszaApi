package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;

public class EditOrderPresenter implements EditOrderContract.Presenter {

    public TempOrderBill tempOrderBill;
    private EditOrderContract.View view;

    public void setView(EditOrderContract.View view) {
        this.view = view;
    }

    public void viewCreated() {

    }

    public void getTempOrderBill(TempOrderBill tempOrderBill) {
        this.tempOrderBill = tempOrderBill;
        System.out.println("SPRAWDZAMY" + tempOrderBill);
        view.showCurrentValuesView(tempOrderBill);
    }

    @Override
    public void editOrderSavePressed(TempOrderBill tempOrderBill) {

    }
}
