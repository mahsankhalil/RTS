package com.example.jaibapp.Repository.CategoryIncomeExpenseRepository;

import android.arch.lifecycle.MutableLiveData;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeViewModel;
import com.example.jaibapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryIncomeRepository extends IncomeViewModel {
    private MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();

    @Override
    public MutableLiveData<List<CategoryItem>> getAllData() {

        List<CategoryItem> incomeList = new ArrayList<>();
        incomeList.add(new CategoryItem("Grocery", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Eating-out", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Personal", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Utilities", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Medical", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Education", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Savings", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("HouseHold", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Entertainment", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Fuel & Transport", R.drawable.ic_menu_dashboard));        incomeList.add(new CategoryItem("Grocery", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Gifts", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Family Expense", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Other Expense", R.drawable.ic_menu_dashboard));
        incomeList.add(new CategoryItem("Hostel Rent", R.drawable.ic_menu_dashboard));
        data.setValue(incomeList);
        return data;
    }
}
