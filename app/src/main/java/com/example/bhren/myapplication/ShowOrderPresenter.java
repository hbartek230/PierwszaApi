package com.example.bhren.myapplication;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.Common.TempOrderBillWithState;
import com.example.bhren.myapplication.EmailSender.SendMail;
import com.example.bhren.myapplication.Inteface.ShowOrderContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShowOrderPresenter implements ShowOrderContract.Presenter {

    private final TempOrderRepository tempOrderRepository;
    private final List<TempOrderBill> tempOrderBills;
    private ShowOrderContract.View view;
    private List<String>[] messageTable;
    String message;

    public ShowOrderPresenter(TempOrderRepository tempOrderRepository) {
        this.tempOrderRepository = tempOrderRepository;
        tempOrderBills = new ArrayList<>();
        messageTable = new List[3];
    }

    @Override
    public void viewCreated() {
        getFirebaseData();
    }

    private void getFirebaseData() {
        tempOrderRepository.getTempOrders()
                .subscribe(new Observer<TempOrderBillWithState>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //
                    }

                    @Override
                    public void onNext(TempOrderBillWithState tempOrderBillWithState) {
                        handleNext(tempOrderBillWithState);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //
                    }
                });

    }

    private void handleNext(TempOrderBillWithState tempOrderBillWithState) {
        switch (tempOrderBillWithState.getItemState()) {
            case ADDED:
                addTempOrder(tempOrderBillWithState.getTempOrderBill());
                break;

            case CHANGED:
                changeTempOrder(tempOrderBillWithState.getTempOrderBill());
                break;

            case DELETED:
                deleteTempOrder(tempOrderBillWithState.getTempOrderBill());
                break;
        }
    }

    private void changeTempOrder(TempOrderBill tempOrderBill) {
        int index = tempOrderBills.indexOf(tempOrderBill);
        tempOrderBills.set(index, tempOrderBill);
        refreshBill();
    }

    private void deleteTempOrder(TempOrderBill tempOrderBill) {
        tempOrderBills.remove(tempOrderBill);
        refreshBill();
    }

    private void addTempOrder(TempOrderBill tempOrderBill) {
        tempOrderBills.add(tempOrderBill);
        refreshBill();
    }

    public void refreshBill() {
        view.showAvailableTempOrdersView(tempOrderBills);
        calculateBillSummary();
    }

    private void calculateBillSummary() {
        int currentPrice = 0;
        for (TempOrderBill tempOrderBill : tempOrderBills) {
            currentPrice += Integer.parseInt(tempOrderBill.getTempOrder().getAmount());
        }
        view.showBillSumarryView(currentPrice);
    }

    public void setEmailMessage(){
        setMessage();
    }

    private void setMessage(){
        String kind = null, quantity = null, price = null;
        //List<String> listOfKinds = new ArrayList<>();
        //List<String> listOfQuantites = new ArrayList<>();
        //List<String> listOfPrices = new ArrayList<>();
        for(TempOrderBill tempOrderBill : tempOrderBills) {
            kind = tempOrderBill.getTempOrder().getKind();
            quantity = tempOrderBill.getTempOrder().getQuantity();
            price = tempOrderBill.getTempOrder().getAmount();
            System.out.println("Rodzaj: " + kind);
            //listOfKinds.add(kind);
            //listOfQuantites.add(quantity);
            //listOfPrices.add(price);
        }
            //messageTable[0] = listOfKinds;
            //messageTable[1] = listOfQuantites;
            //messageTable[2] = listOfPrices;


        view.sendEmail(kind, quantity, price);
    }

    @Override
    public void setView(ShowOrderContract.View view) {
        this.view = view;
    }

    @Override
    public void orderDeletePressed(TempOrderBill tempOrderBill) {
        tempOrderRepository.deleteTempOrderItem(tempOrderBill.getKey());
    }

    @Override
    public void orderEditPressed(TempOrderBill tempOrderBill) {
        view.showUpdateTempOrderItemView(tempOrderBill);
    }
}
