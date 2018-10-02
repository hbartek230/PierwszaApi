package com.example.bhren.myapplication.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.Inteface.OrderClickListener;
import com.example.bhren.myapplication.R;

import java.util.ArrayList;
import java.util.List;

class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView twOrderItem;
    TextView twOrderQuantity;
    TextView twOrderPrice;
    ImageView imageDelete;
    ImageView imageEdit;
    OrderClickListener listener;


    public OrderViewHolder(View itemView, OrderClickListener listener) {
        super(itemView);
        this.listener = listener;
        twOrderItem = (TextView) itemView.findViewById(R.id.twOrderItem);
        twOrderQuantity = (TextView) itemView.findViewById(R.id.twOrderQuantity);
        twOrderPrice = (TextView) itemView.findViewById(R.id.twOrderPrice);
        imageDelete = (ImageView) itemView.findViewById(R.id.imageDelete);
        imageEdit = (ImageView) itemView.findViewById(R.id.imageEdit);

    }
}

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private List<TempOrderBill> tempOrderList = new ArrayList<>();
    private OrderClickListener listener;

    public OrderAdapter(OrderClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<TempOrderBill> tempOrderList){
        this.tempOrderList = tempOrderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_view, parent, false);

        return new OrderViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        TempOrderBill tempOrderBill = tempOrderList.get(position);

        holder.twOrderItem.setText(tempOrderBill.getTempOrder().getKind());
        holder.twOrderQuantity.setText(tempOrderBill.getTempOrder().getQuantity());
        holder.twOrderPrice.setText(tempOrderBill.getTempOrder().getAmount());

        holder.imageDelete.setOnClickListener(v ->
                listener.onOrderDelete(tempOrderBill));

        holder.imageEdit.setOnClickListener(v ->
                listener.onOrderEdit(tempOrderBill));

    }

    @Override
    public int getItemCount() {
        return tempOrderList.size();
    }
}
