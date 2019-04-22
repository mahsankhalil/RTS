package com.example.jaibapp.Repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.ExpenseViewModel;
import com.example.jaibapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryExpenseRepository extends ExpenseViewModel {
    private MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
    @Override
    public MutableLiveData<List<CategoryItem>> getAllData() {

        List<CategoryItem> expenseList = new ArrayList<>();
        expenseList.add(new CategoryItem("Salary", R.drawable.ic_menu_dashboard));
        expenseList.add(new CategoryItem("Salary", R.drawable.ic_menu_dashboard));
        expenseList.add(new CategoryItem("Investment", R.drawable.ic_menu_dashboard));
        expenseList.add(new CategoryItem("Commission", R.drawable.ic_menu_dashboard));
        expenseList.add(new CategoryItem("Other Income", R.drawable.ic_menu_dashboard));
        expenseList.add(new CategoryItem("Pocket Money", R.drawable.ic_menu_dashboard));
        data.setValue(expenseList);
        return data;
    }
}
