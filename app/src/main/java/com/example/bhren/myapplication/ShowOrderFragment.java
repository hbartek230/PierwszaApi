package com.example.bhren.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.EditOrder.EditOrderActivity;
import com.example.bhren.myapplication.Inteface.OrderClickListener;
import com.example.bhren.myapplication.Inteface.ShowOrderContract;
import com.example.bhren.myapplication.ViewHolder.OrderAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShowOrderFragment extends Fragment implements OrderClickListener, ShowOrderContract.View {

    @BindView(R.id.orderRecycler)
    protected RecyclerView order_recycler;
    @BindView(R.id.twPrice)
    protected TextView priceLabel;

    private OrderAdapter adapter;
    private ShowOrderPresenter showPresenter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPresenter = new ShowOrderPresenter(new FirebaseTempOrderRepository(new TempOrderService()));
        showPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_frag2, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        bindView();
        return fragmentView;
    }

    private void bindView() {
        order_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderAdapter(this);
        order_recycler.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showPresenter.viewCreated();
    }

    @Override
    public void onOrderDelete(TempOrderBill tempOrderBill) {
        showPresenter.orderDeletePressed(tempOrderBill);
    }

    @Override
    public void onOrderEdit(TempOrderBill tempOrderBill) {
        showPresenter.orderEditPressed(tempOrderBill);
    }

    @Override
    public void showAvailableTempOrdersView(List<TempOrderBill> tempOrderBills) {
        adapter.setItems(tempOrderBills);
    }

    @Override
    public void showUpdateTempOrderItemView(TempOrderBill tempOrderBill) {
        Intent editOrder = new Intent(getContext(), EditOrderActivity.class);
        Bundle passBundle = new Bundle();
        passBundle.putSerializable("tempOrderBill", tempOrderBill);
        editOrder.putExtras(passBundle);
        startActivity(editOrder);
    }

    @Override
    public void showBillSumarryView(int currentPrice) {
        priceLabel.setText(getString(R.string.act_order_bill_summary_currency, currentPrice));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
