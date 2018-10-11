package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;

public interface EditOrderContract {
    interface View {

        void showCurrentValuesView(TempOrderBill tempOrderBill);

        void setPriceEditableView();

        void setHoneyPriceView(int summaryHoneyPrice);

        void setMainPriceView(String honeyPrice);

    }

    interface Presenter {

        void setView(View view);

        void createFinalView(TempOrderBill tempOrderBill);

        void editOrderSavePressed(TempOrderBill tempOrderBill);

        void priceSwitcherChecked(boolean isChecked);

        void switcherNotChecked(int honeySinglePrice, int honeyQuantity);

        void newQuantityTypped(String honeyPrice, String quantity);
    }
}
