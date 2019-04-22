package com.example.jaibapp.CategoryIncomeExpense.TabFragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaibapp.CategoryIncomeExpense.Adapter.CategoryIncomeExpenseAdapter;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.ExpenseViewModel;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.CategoryExpenseRepository;

import java.util.List;

public class ExpenseFragment extends Fragment {

    private ExpenseViewModel mViewModel;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CategoryIncomeExpenseAdapter adapter;
    public static ExpenseFragment newInstance() {
        return new ExpenseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expense_fragment, container, false);
        recyclerView = view.findViewById(R.id.category_income_expense_fragment_recycler_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryIncomeExpenseAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryExpenseRepository.class);
        MutableLiveData<List<CategoryItem>> list = mViewModel.getAllData();
        adapter.setValue(list.getValue());
    }

}
