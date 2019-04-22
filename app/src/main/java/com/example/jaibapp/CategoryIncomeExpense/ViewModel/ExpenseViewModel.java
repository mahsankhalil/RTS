package com.example.jaibapp.CategoryIncomeExpense.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.R;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpenseViewModel extends ViewModel {
    private List<CategoryItem> expenseList;



    public abstract MutableLiveData<List<CategoryItem>> getAllData();
}
