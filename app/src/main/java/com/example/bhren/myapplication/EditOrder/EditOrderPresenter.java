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
    public void createFinalView(TempOrderBill tempOrderBill) {
        controller.getHoneyAmount(amount -> {
            honeyPrice = amount;
            view.setMainPriceView(honeyPrice);
        }, tempOrderBill.getTempOrder().getKind());
        view.showCurrentValuesView(tempOrderBill);
    }

    @Override
    public void editOrderSavePressed(TempOrderBill tempOrderBill) {

    }

    @Override
    public void priceSwitcherChecked(boolean isChecked) {
        view.setPriceEditableView();
    }

    @Override
    public void switcherNotChecked(int honeySinglePrice, int honeyQuantity) {
        calculatePrice(honeySinglePrice, honeyQuantity);
    }

    @Override
    public void newQuantityTypped(String honeyPrice, String quantity) {
        calculatePrice(Integer.parseInt(honeyPrice), Integer.parseInt(quantity));
    }

    public void calculatePrice(int singlePrice, int quantity) {
        OrderMethods controller = new OrderMethods();
        int summaryHoneyPrice = controller.returnOrderPrice(singlePrice, quantity);
        view.setHoneyPriceView(summaryHoneyPrice);
    }


}
