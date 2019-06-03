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
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,1));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,2));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,3));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,4));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,1));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,2));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,3));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,4));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,1));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,2));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,3));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,4));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,1));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,2));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,3));
            list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,4));
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

    @Override
    public void AddAccount(AccountListModel accountListModel) {
        if(data == null)
        {
            data = new MutableLiveData<>();
            List<AccountListModel> list = new ArrayList<>();
            data.setValue(list);
        }
        List<AccountListModel> list = data.getValue();
        list.add(accountListModel);
        data.setValue(list);
    }

    @Override
    public void EditAccount(AccountListModel accountListModel, int position) {
        if(position>=0 && data !=null)
        {
            List<AccountListModel> list = data.getValue();
            AccountListModel acc = list.get(position);
            acc.setId(accountListModel.getId());
            acc.setPictureId(accountListModel.getPictureId());
            acc.setCurrentCurrency(accountListModel.getCurrentCurrency());
            acc.setTitle(accountListModel.getTitle());
            data.setValue(list);
        }
    }
}
