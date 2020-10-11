package com.example.firstexemple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPagesAdapter adapter = new MainPagesAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        fab.setOnClickListener(fabClickListener);
        setSupportActionBar(toolbar);
    }


    /*=================ACTIVITY ACTION=========================*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    View.OnClickListener fabClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int currentPage = viewPager.getCurrentItem();
            String type = null;

            if(currentPage == MainPagesAdapter.PAGE_INCOMES){
                type = Item.TYPE_INCOMES;
            }else if (currentPage == MainPagesAdapter.PAGE_EXPENSES)
                type = Item.TYPE_EXPENSES;

            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            intent.putExtra(AddItemActivity.KEY_TYPE,type);
            startActivityForResult(intent, ItemsFragment.ADD_REQUEST_CODE);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(actionMode != null)
            actionMode.finish();
        fab.show();
        if(position == MainPagesAdapter.PAGE_BALANCE)
            fab.hide();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        fab.setEnabled(false);
        if(state == ViewPager.SCROLL_STATE_IDLE)
            fab.setEnabled(true);
    }

    @Override
    public void onSupportActionModeStarted(@NonNull androidx.appcompat.view.ActionMode mode) {
        super.onSupportActionModeStarted(mode);
        fab.hide();
        actionMode = mode;
    }

    @Override
    public void onSupportActionModeFinished(@NonNull androidx.appcompat.view.ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        fab.show();
        actionMode = null;
    }
}