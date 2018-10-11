package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.GeneralMethods.OrderMethods;

public class EditOrderPresenter implements EditOrderContract.Presenter {

    private final EditOrderRepository editOrderRepository;
    private EditOrderContract.View view;
    private OrderMethods controller;
    private String honeyPrice;

    public EditOrderPresenter(EditOrderRepository editOrderRepository) {
        this.editOrderRepository = editOrderRepository;
    }

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
    public void switcherNotChecked(int honeySinglePrice, int honeyQuantity) {
        calculatePrice(honeySinglePrice, honeyQuantity);
    }

    @Override
    public void newQuantityTypped(String honeyPrice, String quantity) {
        calculatePrice(Integer.parseInt(honeyPrice), Integer.parseInt(quantity));
    }

    @Override
    public void saveChangesButtonPressed(String key, String price, String kind, String quantity) {
        editOrderRepository.editOrderItem(key, price, kind, quantity);
        System.out.println("EditOrderPresenter");
    }

    public void calculatePrice(int singlePrice, int quantity) {
        OrderMethods controller = new OrderMethods();
        int summaryHoneyPrice = controller.returnOrderPrice(singlePrice, quantity);
        view.setHoneyPriceView(summaryHoneyPrice);
    }


}
