package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;

public interface EditOrderContract {
    interface View {

        void showCurrentValuesView(TempOrderBill tempOrderBill);

        void setPriceEditable();

        void setHoneyPrice(int summaryHoneyPrice);

        void setMainPrice(String honeyPrice);

    }

    interface Presenter {

        void setView(View view);

        void viewCreated(TempOrderBill tempOrderBill);

        void editOrderSavePressed(TempOrderBill tempOrderBill);

        void priceSwitcherChecked(boolean isChecked);

        void switcherNotChecked(int honeySinglePrice, int honeyQuantity);
    }
}
