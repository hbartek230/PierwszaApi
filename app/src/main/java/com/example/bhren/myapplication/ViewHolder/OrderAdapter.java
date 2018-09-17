package com.example.bhren.myapplication.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhren.myapplication.Model.TempOrder;
import com.example.bhren.myapplication.R;

import java.util.List;

class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView twOrderItem;
    TextView twOrderQuantity;
    TextView twOrderPrice;

    public OrderViewHolder(View itemView) {
        super(itemView);

        twOrderItem = (TextView) itemView.findViewById(R.id.twOrderItem);
        twOrderQuantity = (TextView) itemView.findViewById(R.id.twOrderQuantity);
        twOrderPrice = (TextView) itemView.findViewById(R.id.twOrderPrice);
    }
}

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder>{

    List<TempOrder> tempOrderList;

    public OrderAdapter(List<TempOrder> List) {
        this.tempOrderList = List;
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_view, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        TempOrder firebaseData = tempOrderList.get(position);

        holder.twOrderItem.setText(firebaseData.getKind());
        holder.twOrderQuantity.setText(firebaseData.getQuantity());
        holder.twOrderPrice.setText(firebaseData.getAmount());
    }

    @Override
    public int getItemCount() {
        return tempOrderList.size();
    }
}
