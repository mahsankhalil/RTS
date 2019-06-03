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

            accountItems.add(new AccountListModel(accountItem.get("title").toString(),(int)(long)accountItem.get("pictureId"), Double.parseDouble(accountItem.get("currentCurrency").toString()),accountItem.get("key").toString()));
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

        myRef.orderByChild("title").equalTo(accountListModel.getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("Firebase", "Already Exists");
                } else {
                    String key = myRef.push().getKey();;
                    accountListModel.setKey(key);
                    myRef.child(key)
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

        myRef.orderByChild("title").equalTo(accountListModel.getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("Firebase", "Already Exists");
                } else {
                    myRef.child(accountListModel.getKey()).setValue(accountListModel);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "Failed to read value.", databaseError.toException());
            }
        });


    }


}
