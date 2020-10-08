package com.example.firstexemple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "MainActivity";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.uchet));
        }
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int currentPage = viewPager.getCurrentItem();
                String type = null;
                if (currentPage == MainPagesAdapter.PAGE_INCOMES)
                    type = Item.TYPE_INCOMES;
                else if (currentPage == MainPagesAdapter.PAGE_EXPENSES)
                    type = Item.TYPE_EXPENSES;
                Intent intent = new Intent(MainActivity.this,AddItemActivity.class);
                intent.putExtra(ItemsFragment.KEY_TYPE,type);
                startActivityForResult(intent,ItemsFragment.ADD_RESULT);
            }
        });



        MainPagesAdapter adapter = new MainPagesAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case MainPagesAdapter.PAGE_INCOMES:
            case MainPagesAdapter.PAGE_EXPENSES:
                fab.show();
                break;
            default:
                fab.hide();
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state){
            case ViewPager.SCROLL_STATE_IDLE:
                fab.setEnabled(true);
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
            case ViewPager.SCROLL_STATE_SETTLING:
                fab.setEnabled(false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}