package com.example.jaibapp.Accounts.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jaibapp.Accounts.DTO.AccountListModel;

import java.util.List;

public abstract class AccountViewModel extends ViewModel {
    public abstract MutableLiveData<List<AccountListModel>> getAll();
    public abstract MutableLiveData<List<AccountListModel>> RemoveByAccountObject(AccountListModel accountListModel);
    public abstract void AddAccount(AccountListModel accountListModel);

}
