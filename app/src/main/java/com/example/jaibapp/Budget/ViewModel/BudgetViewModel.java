package com.example.jaibapp.Budget.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Budget.DTO.BudgetModel;

import java.util.List;
import java.util.Map;

public abstract class BudgetViewModel extends ViewModel {

    public abstract MutableLiveData<List<BudgetModel>> getAll();
    public abstract void remove(BudgetModel budgetModel);
    public abstract void add(BudgetModel budgetModel);
    public abstract void update(BudgetModel budgetModel);
    public abstract BudgetModel parse(Map<String,Object> budgetItem);

}
