package com.example.firstexemple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsFragment extends Fragment {
    private static final int TYPE_NONE = 0;
    public static final  int TYPE_INCOMES = 1;
    public static final  int TYPE_EXPENSES = 2;
    private static final String KEY_TYPE = "type";
    private int type = TYPE_NONE;

    private RecyclerView recycler;
    private ItemsAdapter adapter;

    public static ItemsFragment CreateItemFragment(int type){
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ItemsAdapter();
        Bundle bundle = getArguments();
        if(bundle != null)
            type = bundle.getInt(KEY_TYPE, TYPE_NONE);


        if (type == TYPE_NONE) {
            throw new IllegalArgumentException("None type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.list);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }
}
