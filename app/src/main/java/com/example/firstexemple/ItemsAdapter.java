package com.example.firstexemple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.RecordViewHolder> {
    private List<Item> data = new ArrayList<>();

    public void setData(List<Item> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemsAdapter.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemsAdapter.RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.RecordViewHolder holder, int position) {
        Item item = data.get(position);
        holder.applyDate(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;
        private final String symbol;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            symbol = itemView.getContext().getString(R.string.def_simbol);
        }
        public void applyDate(Item item){
            title.setText(item.name);
            price.setText(String.format(Locale.US,"%d %s", item.price,symbol));
        }
    }
}
