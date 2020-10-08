package com.example.firstexemple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemsFragment extends Fragment {
    public static final int ADD_RESULT = 951;
    public static final String KEY_TYPE = "type";
    private static final String TAG = "ItemsFragment";
    private String type;

    private RecyclerView recycler;
    private ItemsAdapter adapter;
    SwipeRefreshLayout refresh;

    private Api api;

    public static ItemsFragment CreateItemFragment(String type){
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ADD_RESULT && resultCode == Activity.RESULT_OK){
            Item item = Objects.requireNonNull(data).getParcelableExtra("result_item");
            if(Objects.requireNonNull(item).type.equals(type))
                adapter.addItem(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ItemsAdapter();
        Bundle bundle = getArguments();
        if(bundle != null)
            type = bundle.getString(KEY_TYPE, Item.TYPE_EXPENSES);

        if (type.equals(Item.TYPE_UNKNOWN)) {
            throw new IllegalArgumentException("None type");
        }



        api = ((App) Objects.requireNonNull(getActivity(),"Not be null").getApplication()).getApi();
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


        refresh = view.findViewById(R.id.refresh);
        refresh.setColorSchemeColors(Color.GREEN,Color.YELLOW,Color.CYAN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();

            }
        });
        loadItems();
    }

    private void loadItems() {
//        List<Item> list = new ArrayList<>();
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        list.add(new Item("item1","200","a"));
//        adapter.setData(list);
//        refresh.setRefreshing(false);

        Call<List<Item>> call = api.getItems(type);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NotNull Call<List<Item>> call, @NotNull Response<List<Item>> response) {
                adapter.setData(response.body());
                refresh.setRefreshing(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Item>> call, @NotNull Throwable t) {
                refresh.setRefreshing(false);
            }
        });
    }

//    private void loadItems() {
//        AsyncTask<Void,Void,List<Item>> task = new AsyncTask<Void,Void,List<Item>>(){
//            @Override
//            protected List<Item> doInBackground(Void... voids) {
//                Call<List<Item>> call = api.getItems(type);
//                try {
//                    List<Item> items = call.execute().body();
//                    return items;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(List<Item> items) {
//                if (items != null){
//                    adapter.setData(items);
//                }
//            }
//        };
//        task.execute();
//    }
}

//HANDLER LOAD
//    private void loadItems() {
//        new LoadItemsTask(new Handler(callback)).start();
//    }
//
//    private Handler.Callback callback = new Handler.Callback(){
//        @Override
//        public boolean handleMessage(@NonNull Message msg) {
//            if(msg.what == DATA_LOAD){
//                adapter.setData((List<Item>)msg.obj);
//            }
//            return true;
//        }
//    };
//
//    public static final int DATA_LOAD = 123;
//
//    private class LoadItemsTask implements Runnable {
//        private Thread thread;
//        private Handler handler;
//
//        public LoadItemsTask(Handler handler) {
//            thread = new Thread(this);
//            this.handler = handler;
//        }
//        public void start(){
//            thread.start();
//        }
//
//        @Override
//        public void run() {
//            Call<List<Item>> call = api.getItems(type);
//            try {
//                List<Item> items = call.execute().body();
//                handler.obtainMessage(DATA_LOAD,items).sendToTarget();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
