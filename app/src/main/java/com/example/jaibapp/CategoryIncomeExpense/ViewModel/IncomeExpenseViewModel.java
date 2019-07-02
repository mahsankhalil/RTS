package com.example.jaibapp.CategoryIncomeExpense.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import java.util.List;
import java.util.Map;

public abstract class IncomeExpenseViewModel extends ViewModel {
    public abstract MutableLiveData<List<CategoryItem>> getAll();
    public abstract void Insert(CategoryItem categoryItem);
    public abstract CategoryItem getAt(int i);
    public abstract Map<String,Object> getMap();
    public abstract CategoryItem getCategoryByID(String id) throws Exception;
    public abstract CategoryItem parse(Map<String, Object> categoryItem);

    }
