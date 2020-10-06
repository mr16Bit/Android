package com.example.firstexemple;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagesAdapter extends FragmentPagerAdapter {
    public static final int PAGE_INCOMES = 0;
    public static final int PAGE_EXPENSES = 1;
    public static final int PAGE_BALANCE = 2;

    private String[] tab_title;

    public MainPagesAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        tab_title = context.getResources().getStringArray(R.array.tabs_title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PAGE_INCOMES:{
                return ItemsFragment.CreateItemFragment(ItemsFragment.TYPE_INCOMES);
            }
            case PAGE_EXPENSES:
                return ItemsFragment.CreateItemFragment(ItemsFragment.TYPE_EXPENSES);
            case PAGE_BALANCE:{
                return new BalanceFragment();
            }
            default:
                break;
        }
        return ItemsFragment.CreateItemFragment(ItemsFragment.TYPE_INCOMES);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab_title[position];
    }
}
