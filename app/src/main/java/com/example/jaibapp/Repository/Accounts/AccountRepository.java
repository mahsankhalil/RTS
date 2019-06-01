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
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,1));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,2));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,3));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,4));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,5));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,6));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,7));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,8));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,9));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,10));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,11));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,12));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,13));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,14));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,15));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,16));
            data.setValue(list);
        }
        return data;
    }

    @Override
    public MutableLiveData<List<AccountListModel>> RemoveByAccountObject(AccountListModel accountListModel) {
        data.getValue().remove(accountListModel);
        data.setValue(data.getValue());
        return data;
    }
}
