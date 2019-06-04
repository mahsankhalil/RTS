package com.example.jaibapp.Repository.CategoryIncomeExpense;

import android.arch.lifecycle.MutableLiveData;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryIncomeRepository extends IncomeExpenseViewModel {
    private MutableLiveData<List<CategoryItem>> data = null;




    @Override
    public MutableLiveData<List<CategoryItem>> getAllData() {

        if(data==null)
        {
            data = new MutableLiveData<>();
            List<CategoryItem> incomeList = new ArrayList<>();
            incomeList.add(new CategoryItem("Grocery", R.drawable.ic_menu_dashboard,"1"));
            incomeList.add(new CategoryItem("Eating-out", R.drawable.ic_menu_dashboard,"2"));
            incomeList.add(new CategoryItem("Personal", R.drawable.ic_menu_dashboard,"3"));
            incomeList.add(new CategoryItem("Utilities", R.drawable.ic_menu_dashboard,"4"));
            incomeList.add(new CategoryItem("Medical", R.drawable.ic_menu_dashboard,"5"));
            incomeList.add(new CategoryItem("Education", R.drawable.ic_menu_dashboard,"6"));
            incomeList.add(new CategoryItem("Savings", R.drawable.ic_menu_dashboard,"7"));
            incomeList.add(new CategoryItem("HouseHold", R.drawable.ic_menu_dashboard,"8"));
            incomeList.add(new CategoryItem("Entertainment", R.drawable.ic_menu_dashboard,"9"));
            incomeList.add(new CategoryItem("Fuel & Transport", R.drawable.ic_menu_dashboard,"10"));
            incomeList.add(new CategoryItem("Gifts", R.drawable.ic_menu_dashboard,"11"));
            incomeList.add(new CategoryItem("Family Expense", R.drawable.ic_menu_dashboard,"12"));
            incomeList.add(new CategoryItem("Other Expense", R.drawable.ic_menu_dashboard,"13"));
            incomeList.add(new CategoryItem("Hostel Rent", R.drawable.ic_menu_dashboard,"14"));
            incomeList.add(new CategoryItem("Grocery", R.drawable.ic_menu_dashboard,"15"));
            data.setValue(incomeList);
        }

        return data;
    }

    @Override
    public void Insert(CategoryItem categoryItem) {
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
