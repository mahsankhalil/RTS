package com.example.jaibapp.Repository.Accounts;

import android.arch.lifecycle.MutableLiveData;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.R;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository extends AccountViewModel {
    private MutableLiveData<List<AccountListModel>> data = null;
    @Override
    public MutableLiveData<List<AccountListModel>> getAll() {
        if(data == null)
        {
            data = new MutableLiveData<>();
            List<AccountListModel> list = new ArrayList<>();
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,1200.0));
            data.setValue(list);
        }
        return data;
    }
}
