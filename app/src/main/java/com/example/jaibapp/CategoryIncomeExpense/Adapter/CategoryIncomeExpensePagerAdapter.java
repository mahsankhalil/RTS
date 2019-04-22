package com.example.jaibapp.CategoryIncomeExpense.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jaibapp.CategoryIncomeExpense.TabFragments.ExpenseFragment;
import com.example.jaibapp.CategoryIncomeExpense.TabFragments.IncomeFragment;
import com.example.jaibapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class CategoryIncomeExpensePagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.income_title, R.string.expense_title};
    private final Context mContext;

    public CategoryIncomeExpensePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr;
        if(position==0)
        {
            fr = new IncomeFragment();
        }
        else
        {
            fr = new ExpenseFragment();
        }
        return fr;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}