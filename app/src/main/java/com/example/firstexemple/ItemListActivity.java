package com.example.firstexemple;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemListActivity extends AppCompatActivity {

    private RecyclerView list;
    private List<Record> date = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        createDate();
        list.setAdapter(new ListItemAdapter());
        list.addItemDecoration(new ListItemDecorations(30));

    }

    private void createDate() {
        date.add(new Record("Молоко",56));
        date.add(new Record("Кетчуп",30));
        date.add(new Record("Сасиски",230));
        date.add(new Record("Банки",60));
        date.add(new Record("Травка",1000));
        date.add(new Record("Игрушки",1010));
        date.add(new Record("Дилда фыв фы вфыв фыв фыв фы ыпвва пыва прывары вапывапва",300));
        date.add(new Record("Итем1",12));
        date.add(new Record("Итем2",123));
        date.add(new Record("Итем3",124));
        date.add(new Record("Итем4",125));
        date.add(new Record("Итем5",126));
        date.add(new Record("Итем6",127));
        date.add(new Record("Итем7",128));
        date.add(new Record("Итем8",129));

    }

    private class ListItemAdapter extends RecyclerView.Adapter<RecordViewHolder> {
        @NonNull
        @Override
        public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,parent,false);
            return new RecordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecordViewHolder holder, int position) {
            Record record = date.get(position);
            holder.applyDate(record);
        }

        @Override
        public int getItemCount() {
            return date.size();
        }
    }

    private static class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView price;
        private final String symbol;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            symbol = itemView.getContext().getString(R.string.def_simbol);
        }
        public void applyDate(Record record){
            title.setText(record.getTitle());
            price.setText(String.format(Locale.US,"%d %s",record.getPrice(),symbol));
        }
    }

    public class ListItemDecorations extends RecyclerView.ItemDecoration {
        private int mItemOffset;

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset,0, mItemOffset,0);
        }

        public ListItemDecorations(int itemOffset) {
            mItemOffset = itemOffset;
        }


    }
}
