package com.example.bhren.myapplication;

import com.example.bhren.myapplication.Common.TempOrderBillWithState;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FirebaseTempOrderRepository implements TempOrderRepository {

    private final TempOrderService tempOrderService;

    @Override
    public Observable<TempOrderBillWithState> getTempOrders() {
        return tempOrderService.getTempOrderBiillsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void deleteTempOrderItem(String key) {
        tempOrderService.deleteItemFromFirebase(key);
    }
}
