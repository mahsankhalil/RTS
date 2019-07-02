package com.example.jaibapp.Budget.Fragments;


import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.AddAccount;
import com.example.jaibapp.Budget.DTO.BudgetModel;
import com.example.jaibapp.Budget.SetBudget;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.Repository.Budget.BudgetRepository;

import com.example.jaibapp.Budget.Adapter.BudgetAdapter;
import com.example.jaibapp.Budget.ViewModel.BudgetViewModel;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.CategoryIncomeExpense.CategoryExpenseRepository;

import java.util.ArrayList;
import java.util.List;


public class BudgetFragment extends Fragment {

    private boolean isCategoryDataLoaded = false;
    private boolean isBudgetDataLoaded = false;

    private final static String TAG = BudgetFragment.class.getSimpleName();

    //Intent Keys
    public final static String EX_CATEGORY_ID = "category_id";
    public final static String EX_CATEGORY_TITLE = "title";
    public final static String EX_CATEGORY_IMAGE_ID = "imageId";
    public final static String EX_BUDGET_ID = "budget_id";
    public final static String EX_BUDGET_MONTH = "month";
    public final static String EX_BUDGET_YEAR = "year";
    public final static String EX_BUDGET_AMOUNT = "amount";

    // vars
    private BudgetViewModel budgetViewModel;
    private IncomeExpenseViewModel expenseCategoryViewModel;

    private Context context;

    // UI Elements
    RecyclerView recyclerView;
    BudgetAdapter budgetAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.e(TAG, "onCreateView: aaa gya");

        View view = inflater.inflate(R.layout.monthly_budget,container,false);

        recyclerView = view.findViewById(R.id.monthly_budget_recycler_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        budgetViewModel = ViewModelProviders.of(this).get(BudgetRepository.class);
        expenseCategoryViewModel = ViewModelProviders.of(this).get(CategoryExpenseRepository.class);

        budgetAdapter = new BudgetAdapter(view.getContext(),budgetViewModel,expenseCategoryViewModel);
        budgetAdapter.setBudgetList(new ArrayList<BudgetModel>());
        budgetAdapter.setCategoryList(new ArrayList<CategoryItem>());
        budgetViewModel.getAll().observe(this, new Observer<List<BudgetModel>>() {
            @Override
            public void onChanged(@Nullable List<BudgetModel> budgetModels) {
                budgetAdapter.setBudgetList(budgetModels);
                budgetAdapter.notifyDataSetChanged();
                if(isCategoryDataLoaded){
                    recyclerView.setAdapter(budgetAdapter);
                }
                isBudgetDataLoaded = true;
                isCategoryDataLoaded = false;
                Log.e(TAG, "onChanged: 1 "+ budgetModels.size() );
            }
        });
        expenseCategoryViewModel.getAll().observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(@Nullable List<CategoryItem> categoryItemList) {
                budgetAdapter.setCategoryList(categoryItemList);
                budgetAdapter.notifyDataSetChanged();
                if(isBudgetDataLoaded){
                    recyclerView.setAdapter(budgetAdapter);
                }
                isCategoryDataLoaded = true;
                isBudgetDataLoaded = false;
                Log.e(TAG, "onChanged: 2 "+ categoryItemList.size() );

            }
        });

        context = view.getContext();

        FloatingActionButton fab = view.findViewById(R.id.budget_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetBudget.class);
                intent.putExtra(BudgetFragment.EX_CATEGORY_ID, "");
                intent.putExtra(BudgetFragment.EX_CATEGORY_IMAGE_ID,"");
                intent.putExtra(BudgetFragment.EX_CATEGORY_TITLE,"");
                intent.putExtra(BudgetFragment.EX_BUDGET_ID,"");
                intent.putExtra(BudgetFragment.EX_BUDGET_MONTH,"");
                intent.putExtra(BudgetFragment.EX_BUDGET_YEAR,"");
                intent.putExtra(BudgetFragment.EX_BUDGET_AMOUNT,0);
                startActivity(intent);
            }
        });


       return view;
    }

}


