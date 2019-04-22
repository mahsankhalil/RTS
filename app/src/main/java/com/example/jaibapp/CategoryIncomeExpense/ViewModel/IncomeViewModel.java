package com.example.jaibapp.CategoryIncomeExpense.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.R;

import java.util.ArrayList;
import java.util.List;

public abstract class IncomeViewModel extends ViewModel {
    private List<CategoryItem> incomeList;
    public abstract MutableLiveData<List<CategoryItem>> getAllData();

}
