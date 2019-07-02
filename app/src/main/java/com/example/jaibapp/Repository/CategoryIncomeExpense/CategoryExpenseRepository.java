package com.example.jaibapp.Repository.CategoryIncomeExpense;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryExpenseRepository extends IncomeExpenseViewModel {

    private Map<String, Object> map;
    private MutableLiveData<List<CategoryItem>> data = null;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("ExpenseCategories");

    @Override
    public MutableLiveData<List<CategoryItem>> getAll() {

        if(data == null)
        {
            data = new MutableLiveData<>();
            List<CategoryItem> expenseList = new ArrayList<>();

            data.setValue(expenseList);
        }

        myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("Firebase", "collectCategories: help");
                    if(dataSnapshot.exists()){
                        collectExpenseCategories(((Map<String,Object>) dataSnapshot.getValue()));
                        map = (Map<String,Object>) dataSnapshot.getValue();
                    }
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
            categoryItems.add(parse(categoryItem));
        }
        data.setValue(categoryItems);

    }

    @Override
    public CategoryItem parse(Map<String, Object> categoryItem){

        CategoryItem categoryItem1 = new CategoryItem(
                categoryItem.get("title").toString(),
                (int)(long)categoryItem.get("pictureId"),
                categoryItem.get("id").toString());
        return categoryItem1;

    }
    @Override
    public void Insert(@NonNull final CategoryItem categoryItem) {
        myRef.orderByChild("title").equalTo(categoryItem.getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("Firebase", "Already Exists");
                } else {
                    String key = myRef.push().getKey();
                    categoryItem.setId(key);
                    categoryItem.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    myRef.child(key)
                            .setValue(categoryItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "Failed to read value.", databaseError.toException());
            }
        });

    }

    @Override
    public CategoryItem getAt(int i) {
        if(data!=null && data.getValue()!=null && i>=0 && i<data.getValue().size())
            return data.getValue().get(i);
        return null;
    }

    @Override
    public CategoryItem getCategoryByID(String id) throws Exception {
        this.getAll();
        List<CategoryItem> list = data.getValue();

        for(int i =0;i<list.size();i++)
        {
            if(list.get(i).getId().compareTo(id)==0)
                return list.get(i);
        }
        return null;
    }
    @Override
    public Map<String, Object> getMap() {
        return map;
    }
}
