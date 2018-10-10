package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.GeneralMethods.OrderMethods;

public class EditOrderPresenter implements EditOrderContract.Presenter {

    private EditOrderContract.View view;
    private OrderMethods controller;
    private String honeyPrice;

    @Override
    public void setView(EditOrderContract.View view) {
        this.view = view;
        controller = new OrderMethods();
    }

    @Override
    public void viewCreated(TempOrderBill tempOrderBill) {
        controller.getHoneyAmount(amount -> {
            honeyPrice = amount;
            view.setMainPrice(honeyPrice);
        }, tempOrderBill.getTempOrder().getKind());
        view.showCurrentValuesView(tempOrderBill);
    }

    @Override
    public void editOrderSavePressed(TempOrderBill tempOrderBill) {

    }

    @Override
    public void priceSwitcherChecked(boolean isChecked) {
        view.setPriceEditable();
    }

    @Override
    public void switcherNotChecked(int honeySinglePrice, int honeyQuantity) {
        OrderMethods controller = new OrderMethods();
        int summaryHoneyPrice = controller.returnOrderPrice(honeySinglePrice, honeyQuantity);
        view.setHoneyPrice(summaryHoneyPrice);
    }


}
