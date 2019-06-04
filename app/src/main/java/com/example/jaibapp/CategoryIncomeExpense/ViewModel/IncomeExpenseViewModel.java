package com.example.jaibapp.CategoryIncomeExpense.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import java.util.List;

public abstract class IncomeExpenseViewModel extends ViewModel {
    public abstract MutableLiveData<List<CategoryItem>> getAllData();
    public abstract void Insert(CategoryItem categoryItem);
    public abstract CategoryItem getAt(int i);
    public abstract CategoryItem getCategoryByID(String id);
}
