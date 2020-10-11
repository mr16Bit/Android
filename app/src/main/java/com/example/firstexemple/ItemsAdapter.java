package com.example.firstexemple;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.RecordViewHolder> {

    private List<Item> data = new ArrayList<>();
    private ItemListeners listeners = null;
    private SparseBooleanArray selected = new SparseBooleanArray();

    @NonNull
    @Override
    public ItemsAdapter.RecordViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemsAdapter.RecordViewHolder(view);
    }

    public void toggleSelection(int position) {
        if(selected.get(position, false)){
            selected.delete(position);
        }else{
            selected.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelected(){
        selected.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount(){
        return selected.size();
    }

    public List<Integer> getSelectedItems(){
        List<Integer> items = new ArrayList<>(getSelectedItemCount());
        for (int i = 0; i < selected.size(); i++ ){
            items.add(selected.keyAt(i));
        }
        return items;
    }

    public Item removeItem(int position){
        final Item item = data.remove(position);
        notifyItemRemoved(position);
        return item;
    }



    @Override
    public void onBindViewHolder(@NotNull ItemsAdapter.RecordViewHolder holder, int position) {
        Item item = data.get(position);
        holder.applyDate(item, position, listeners, selected.get(position, false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(Item item) {
        data.add(0,item);
        notifyItemInserted(0);
    }


    protected static class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);

        }
        public void applyDate(@NotNull Item item, int position, ItemListeners listeners, boolean selected){
            title.setText(item.name);
            price.setText(String.format("%s %s",item.price, itemView.getContext().getString(R.string.def_simbol)));
            createItemListener(item, position, listeners);
            itemView.setActivated(selected);
        }

        private void createItemListener(final Item item, final int position, final ItemListeners listeners){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listeners != null)
                        listeners.onClickListener(item, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listeners != null)
                        listeners.onClickLongListener(item, position);
                    return true;
                }
            });
        }
    }

    public void setData(List<Item> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListeners(ItemListeners listeners){
        this.listeners = listeners;
    }



}
