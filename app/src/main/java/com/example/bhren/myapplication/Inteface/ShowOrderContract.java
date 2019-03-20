package com.example.bhren.myapplication.Inteface;

import com.example.bhren.myapplication.Common.TempOrderBill;

import java.util.List;

public interface ShowOrderContract {
    interface View {

        void showAvailableTempOrdersView(List<TempOrderBill> tempOrderBills);

        void showUpdateTempOrderItemView(TempOrderBill tempOrderBill);

        void showBillSumarryView(int currentPrice);

        void sendEmail(String kind, String quantity, String price);
    }

    interface Presenter {

        void setView(View view);

        void viewCreated();

        void setEmailMessage();

        void orderDeletePressed(TempOrderBill tempOrderBill);

        void orderEditPressed(TempOrderBill tempOrderBill);
    }
}
