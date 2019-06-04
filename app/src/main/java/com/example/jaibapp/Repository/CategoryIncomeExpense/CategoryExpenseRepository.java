package com.example.jaibapp.Repository.CategoryIncomeExpense;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryExpenseRepository extends IncomeExpenseViewModel {
    private MutableLiveData<List<CategoryItem>> data = null;
    @Override
    public MutableLiveData<List<CategoryItem>> getAllData() {

        if(data == null)
        {
            data = new MutableLiveData<>();
            List<CategoryItem> expenseList = new ArrayList<>();
            expenseList.add(new CategoryItem("Salary", R.drawable.ic_menu_dashboard,"1"));
            expenseList.add(new CategoryItem("Salary", R.drawable.ic_menu_dashboard,"2"));
            expenseList.add(new CategoryItem("Investment", R.drawable.ic_menu_dashboard,"3"));
            expenseList.add(new CategoryItem("Commission", R.drawable.ic_menu_dashboard,"4"));
            expenseList.add(new CategoryItem("Other Income", R.drawable.ic_menu_dashboard,"5"));
            expenseList.add(new CategoryItem("Pocket Money", R.drawable.ic_menu_dashboard,"6"));
            data.setValue(expenseList);
        }

        return data;
    }

    @Override
    public void Insert(@NonNull CategoryItem categoryItem) {
        List<CategoryItem> temp = data.getValue() ;
        temp.add(categoryItem);
        data.setValue(temp);
    }

    @Override
    public CategoryItem getAt(int i) {
        if(data!=null && data.getValue()!=null && i>=0 && i<data.getValue().size())
            return data.getValue().get(i);
        return null;
    }

    @Override
    public CategoryItem getCategoryByID(String id) {
        this.getAllData();
        List<CategoryItem> list = data.getValue();
        for(int i =0;i<list.size();i++)
        {
            if(list.get(i).getId().compareTo(id)==0)
                return list.get(i);
        }
        return null;
    }
}
