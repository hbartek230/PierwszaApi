package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;

public interface EditOrderContract {
    interface View {

        void showCurrentValuesView(TempOrderBill tempOrderBill);

    }

    interface Presenter {

        void viewCreated();

        void getTempOrderBill(TempOrderBill tempOrderBill);

        void editOrderSavePressed(TempOrderBill tempOrderBill);
    }
}
