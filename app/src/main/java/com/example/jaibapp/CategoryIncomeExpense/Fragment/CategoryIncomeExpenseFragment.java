package com.example.jaibapp.CategoryIncomeExpense.Fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.jaibapp.CategoryIncomeExpense.Adapter.CategoryIncomeExpensePagerAdapter;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.CategoryIncomeExpenseViewModel;
import com.example.jaibapp.R;

public class CategoryIncomeExpenseFragment extends Fragment {

    private CategoryIncomeExpenseViewModel mViewModel;





    public static CategoryIncomeExpenseFragment newInstance() {
        return new CategoryIncomeExpenseFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_category_income_expense,container,false);
        CategoryIncomeExpensePagerAdapter categoryIncomeExpensePagerAdapter = new CategoryIncomeExpensePagerAdapter(view.getContext(),getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(categoryIncomeExpensePagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryIncomeExpenseViewModel.class);
        // TODO: Use the ViewModel






    }
}
