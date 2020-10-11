package com.example.firstexemple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
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

    public static final int ADD_REQUEST_CODE = 951;
    private Api api;
    private String type;
    private RecyclerView recycler;
    private ItemsAdapter adapter;
    private ActionMode actionMode = null;
    private SwipeRefreshLayout refresher;

    public static ItemsFragment CreateItemFragment(String type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AddItemActivity.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ItemsAdapter();
        adapter.setListeners(new ItemsAddapterListener());
        Bundle bundle = getArguments();
        if (bundle != null)
            type = bundle.getString(AddItemActivity.KEY_TYPE, Item.TYPE_INCOMES);
        if (type.equals(Item.TYPE_UNKNOWN))
            throw new IllegalArgumentException("None type");
        api = ((App) Objects.requireNonNull(getActivity(), "Not be null").getApplication()).getApi();

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

        refresher = view.findViewById(R.id.refresher);
        refresher.setColorSchemeColors(Color.RED, Color.CYAN, Color.BLUE);
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });
        refresher.setRefreshing(true);
        loadItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ItemsFragment.ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            assert data != null;
            Item item = data.getParcelableExtra("item");
            assert item != null;
            if(item.type.equals(type)){
                addItem(item);
            }
        }
    }

    private void addItem(Item item) {
        adapter.addItem(item);
    }

    private void loadItems() {
//        List<Item> list = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            list.add(new Item("Test - " + i, ""+i, "type"));
//        }
//        adapter.setData(list);
        Call<List<Item>> call = api.getItems(type);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NotNull Call<List<Item>> call, @NotNull Response<List<Item>> response) {
                adapter.setData(response.body());
                refresher.setRefreshing(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Item>> call, @NotNull Throwable t) {
                refresher.setRefreshing(false);
            }
        });
    }

    private void removeSelectedItems(){
        List<Integer> tmp = adapter.getSelectedItems();
        for(int i = adapter.getSelectedItemCount() - 1; 0 <= i; i--){
            adapter.removeItem(tmp.get(i));
        }
        actionMode.finish();
    }

    /*======================ACTION METHOD==============*/

    private class ItemsAddapterListener implements ItemListeners{
        @Override
        public void onClickListener(Item item, int position) {
            if(isInActionMode()){
                toggleSelection(position);
            }
        }

        @Override
        public void onClickLongListener(Item item, int position) {
            if(isInActionMode())
                return;

            actionMode =  ((AppCompatActivity) Objects.requireNonNull(getActivity())).startSupportActionMode(actionModeCallback);
            toggleSelection(position);
        }

        private void toggleSelection(int position) {
            adapter.toggleSelection(position);
        }

        private boolean isInActionMode() {
            return actionMode != null;
        }

    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.remove) {
                showDialog();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelected();
            actionMode = null;
        }
    };


    public void showDialog(){
        ConfirmDialog dlg = new ConfirmDialog();
        assert getFragmentManager() != null;
        dlg.show(getFragmentManager(),"ConfirmDlg");
        dlg.setDialogLiseteners(new ConfirmDialogListeners() {
            @Override
            public void onPositiveClick() {
                removeSelectedItems();
            }

            @Override
            public void onNegativeClick() {
                actionMode.finish();
            }
        });
    }
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
//}

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
