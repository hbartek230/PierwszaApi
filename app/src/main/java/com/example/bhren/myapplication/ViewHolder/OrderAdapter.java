package com.example.bhren.myapplication.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhren.myapplication.Common.TempOrderBill;
import com.example.bhren.myapplication.Inteface.OrderClickListener;
import com.example.bhren.myapplication.R;

import java.util.List;

class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {
    TextView twOrderItem;
    TextView twOrderQuantity;
    TextView twOrderPrice;
    ImageView imageDelete;
    OrderClickListener listener;


    public OrderViewHolder(View itemView, OrderClickListener listener) {
        super(itemView);
        this.listener = listener;
        twOrderItem = (TextView) itemView.findViewById(R.id.twOrderItem);
        twOrderQuantity = (TextView) itemView.findViewById(R.id.twOrderQuantity);
        twOrderPrice = (TextView) itemView.findViewById(R.id.twOrderPrice);
        imageDelete = (ImageView) itemView.findViewById(R.id.imageDelete);
        itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        PopupMenu item_menu = new PopupMenu(v.getContext(), v);
        item_menu.getMenuInflater().inflate(R.menu.bill_context_menu, item_menu.getMenu());
        item_menu.setOnMenuItemClickListener(this);
        item_menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.bill_item_delete) {
            Toast.makeText(itemView.getContext(), "Usuwanie", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(itemView.getContext(), "Edycja", Toast.LENGTH_SHORT).show();

        }
        return true;
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

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderDelete(firebaseData.getKey());
            }
        });

    }

    @Override
    public int getItemCount() {
        return tempOrderList.size();
    }
}
