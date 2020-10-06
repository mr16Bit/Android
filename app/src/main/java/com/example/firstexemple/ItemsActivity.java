package com.example.firstexemple;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        adapter = new ItemsAdapter();

        recycler = findViewById(R.id.list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

//        recycler.addItemDecoration(new ListItemDecorations(30));

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
