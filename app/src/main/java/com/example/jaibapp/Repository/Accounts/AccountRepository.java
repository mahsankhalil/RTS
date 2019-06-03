package com.example.jaibapp.Repository.Accounts;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountRepository extends AccountViewModel {
    private MutableLiveData<List<AccountListModel>> data = null;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("Accounts");

    @Override
    public MutableLiveData<List<AccountListModel>> getAll() {
        if(data == null)
        {
            data = new MutableLiveData<>();
            List<AccountListModel> list = new ArrayList<>();
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,1));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,2));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,3));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,4));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,5));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,6));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,7));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,8));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,9));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,10));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,11));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,12));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,13));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,14));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_budget,1200.0,15));
           // list.add(new AccountListModel("Cash", R.drawable.ic_menu_dashboard,300.0,16));
            data.setValue(list);
        }

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Firebase", "collectCategories: help");
                if(dataSnapshot.exists())
                    collectAccounts(((Map<String,Object>) dataSnapshot.getValue()));
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.d("Firebase", "Failed to read value.", databaseError.toException());
            }
        });


        return data;
    }
    private void collectAccounts(Map<String,Object> accounts) {

        ArrayList<AccountListModel> accountItems = new ArrayList<>();


        for (Map.Entry<String, Object> entry : accounts.entrySet()){

            Map accountItem = (Map) entry.getValue();

            accountItems.add(new AccountListModel(accountItem.get("title").toString(),(int)(long)accountItem.get("pictureId"), Double.parseDouble(accountItem.get("currentCurrency").toString()),-1));
        }
        data.setValue(accountItems);

    }



    @Override
    public MutableLiveData<List<AccountListModel>> RemoveByAccountObject(AccountListModel accountListModel) {
        data.getValue().remove(accountListModel);
        data.setValue(data.getValue());
        return data;
    }

    @Override
    public void AddAccount(final AccountListModel accountListModel) {
        /*if(data == null)
        {
            data = new MutableLiveData<>();
            List<AccountListModel> list = new ArrayList<>();
            data.setValue(list);
        }
        List<AccountListModel> list = data.getValue();
        list.add(accountListModel);
        data.setValue(list);*/

        myRef.orderByChild("title").equalTo(accountListModel.getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("Firebase", "Already Exists");
                } else {
                    String key = myRef.push().getKey();;
                    accountListModel.setRecordId(key);
                    dataSnapshot.getRef().child(key)
                            .setValue(accountListModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "Failed to read value.", databaseError.toException());
            }
        });

    }

    @Override
    public void EditAccount(final AccountListModel accountListModel, int position) {
        if(position>=0 && data !=null)
        {
            Log.d("Record", "EditAccount: " + accountListModel.getRecordId());
            return;
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    myRef.child(dataSnapshot.getKey());
//
//
//                    Log.d("id", "onDataChange: " + dataSnapshot);
//                    dataSnapshot.getRef().child(accountListModel.getRecordId())
//                            .setValue(accountListModel);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.d("error", "onCancelled: ERROR");
//                }
//            });
//
//            myRef.setValue(accountListModel);
//            List<AccountListModel> list = data.getValue();
//            myRef.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Log.d("Firebase", "collectCategories: help");
//                    if(dataSnapshot.exists())
//                        collectAccounts(((Map<String,Object>) dataSnapshot.getValue()));
//                }
//
//
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Failed to read value
//                    Log.d("Firebase", "Failed to read value.", databaseError.toException());
//                }
//            });
//
//            data.setValue(list);
        }
    }
}
