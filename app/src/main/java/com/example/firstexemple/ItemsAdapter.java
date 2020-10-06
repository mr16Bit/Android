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

    public ItemsAdapter() {
        createDate();
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

    private void createDate() {
        data.add(new Item("Молоко",56));
        data.add(new Item("Кетчуп",30));
        data.add(new Item("Сасиски",230));
        data.add(new Item("Банки",60));
        data.add(new Item("Травка",1000));
        data.add(new Item("Игрушки",1010));
        data.add(new Item("Дилда фыв фы вфыв фыв фыв фы ыпвва пыва прывары вапывапва",300));
        data.add(new Item("Итем1",12));
        data.add(new Item("Итем2",123));
        data.add(new Item("Итем3",124));
        data.add(new Item("Итем4",125));
        data.add(new Item("Итем5",126));
        data.add(new Item("Итем6",127));
        data.add(new Item("Итем7",128));
        data.add(new Item("Итем8",129));

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
            title.setText(item.getTitle());
            price.setText(String.format(Locale.US,"%d %s", item.getPrice(),symbol));
        }
    }
}
