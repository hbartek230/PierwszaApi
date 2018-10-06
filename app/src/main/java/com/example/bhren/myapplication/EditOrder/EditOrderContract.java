package com.example.bhren.myapplication.EditOrder;

import com.example.bhren.myapplication.Common.TempOrderBill;

public interface EditOrderContract {
    interface View {

        void showCurrentValuesView(String quantity, String price);

    }

    interface Presenter {

        void editOrderSavePressed(TempOrderBill tempOrderBill);
    }
}
