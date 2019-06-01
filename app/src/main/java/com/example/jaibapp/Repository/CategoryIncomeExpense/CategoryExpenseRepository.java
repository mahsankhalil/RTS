package com.example.jaibapp.Repository.CategoryIncomeExpense;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryExpenseRepository extends IncomeExpenseViewModel {

    private MutableLiveData<List<CategoryItem>> data = null;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("ExpenseCategories");

    @Override
    public MutableLiveData<List<CategoryItem>> getAllData() {

        if(data == null)
        {
            data = new MutableLiveData<>();
            List<CategoryItem> expenseList = new ArrayList<>();

            data.setValue(expenseList);
        }

            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("Firebase", "collectCategories: help");
                    if(dataSnapshot.exists())
                        collectExpenseCategories(((Map<String,Object>) dataSnapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Failed to read value
                    Log.d("Firebase", "Failed to read value.", databaseError.toException());
                }
            });


        return data;
    }

    private void collectExpenseCategories(Map<String,Object> categories) {

        ArrayList<CategoryItem> categoryItems = new ArrayList<>();


        for (Map.Entry<String, Object> entry : categories.entrySet()){

            Map categoryItem = (Map) entry.getValue();

            categoryItems.add(new CategoryItem(categoryItem.get("title").toString(),(int)(long)categoryItem.get("pictureId")));
        }
        data.setValue(categoryItems);

    }

    @Override
    public void Insert(@NonNull final CategoryItem categoryItem) {
        myRef.orderByChild("title").equalTo(categoryItem.getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("Firebase", "Already Exists");
                } else {
                    dataSnapshot.getRef().push()
                            .setValue(categoryItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "Failed to read value.", databaseError.toException());
            }
        });

    }
}
