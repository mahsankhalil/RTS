package com.example.jaibapp.Repository.Budget;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jaibapp.Budget.DTO.BudgetModel;
import com.example.jaibapp.Budget.ViewModel.BudgetViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BudgetRepository extends BudgetViewModel {

    private MutableLiveData<List<BudgetModel>> data = null;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference myRef = database.getReference().child("Budgets");


    private void retriveData(){

        myRef.orderByChild("uid").equalTo(user.getUid())
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            Map<String,Object> map = ((Map<String,Object>) dataSnapshot.getValue());
                            collectBudgets(map);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Failed to read value
                        Log.d("Firebase", "Failed to read value.", databaseError.toException());
                    }
                });
    }


    @Override
    public MutableLiveData<List<BudgetModel>> getAll() {
        if(data == null) {
            data = new MutableLiveData<List<BudgetModel>>();
            data.setValue(new ArrayList<BudgetModel>());
            retriveData();
        }
        return data;
    }

    private void collectBudgets(Map<String,Object> budgets) {

        ArrayList<BudgetModel> budgetsItems = new ArrayList<>();

        for (Map.Entry<String, Object> entry : budgets.entrySet()){
            Map budgetItem = (Map) entry.getValue();
            budgetsItems.add(parse(budgetItem));
        }
        data.setValue(budgetsItems);

    }

    @Override
    public BudgetModel parse(Map<String,Object> budgetItem) {

        Date date = new Date();
        BudgetModel budgetModel = new BudgetModel(budgetItem.get("id").toString(),
                budgetItem.get("month").toString(),
                budgetItem.get("year").toString(),
                Double.parseDouble(budgetItem.get("amount").toString()),
                budgetItem.get("categoryID").toString());
        budgetModel.setUid(budgetItem.get("uid").toString());
        return budgetModel;
    }

    @Override
    public void remove(BudgetModel budgetModel) {

        myRef.child(budgetModel.getId()).removeValue();

    }

    @Override
    public void add(BudgetModel budgetModel) {

        String key = myRef.push().getKey();
        budgetModel.setId(key);
        myRef.child(key).setValue(budgetModel);

    }

    @Override
    public void update(BudgetModel budgetModel) {

        myRef.child(budgetModel.getId()).setValue(budgetModel);

    }
}
