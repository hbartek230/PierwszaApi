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
    List<TempOrderBill> tempOrderList;
    private OrderClickListener listener;

    public OrderAdapter(List<TempOrderBill> tempOrderList, OrderClickListener listener) {
        this.tempOrderList = tempOrderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_view, parent, false);

        return new OrderViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        TempOrderBill firebaseData = tempOrderList.get(position);

        holder.twOrderItem.setText(firebaseData.getTempOrder().getKind());
        holder.twOrderQuantity.setText(firebaseData.getTempOrder().getQuantity());
        holder.twOrderPrice.setText(firebaseData.getTempOrder().getAmount());

        holder.imageDelete.setOnClickListener(v -> listener.onOrderDelete(firebaseData.getKey()));

        holder.imageEdit.setOnClickListener(v -> listener.onOrderEdit(holder.twOrderItem.getText().toString(), firebaseData.getKey(), holder.twOrderQuantity.getText().toString(), holder.twOrderPrice.getText().toString()));

    }

    @Override
    public int getItemCount() {
        return tempOrderList.size();
    }
}
