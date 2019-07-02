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

public class CategoryIncomeRepository extends IncomeExpenseViewModel {

    private MutableLiveData<List<CategoryItem>> data = null;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("IncomeCategories");

    @Override
    public MutableLiveData<List<CategoryItem>> getAll() {

        if(data==null)
        {
            data = new MutableLiveData<>();
            List<CategoryItem> incomeList = new ArrayList<>();

            data.setValue(incomeList);
        }

        myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Firebase", "collectCategories: help");
                if(dataSnapshot.exists())
                collectIncomeCategories(((Map<String,Object>) dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.d("Firebase", "Failed to read value.", databaseError.toException());
            }
        });


        return data;
    }
    private void collectIncomeCategories(Map<String,Object> categories) {

        ArrayList<CategoryItem> categoryItems = new ArrayList<>();

        for (Map.Entry<String, Object> entry : categories.entrySet()){

            Map categoryItem = (Map) entry.getValue();

            categoryItems.add(new CategoryItem(categoryItem.get("title").toString(),(int)(long)categoryItem.get("pictureId")));
        }
        data.setValue(categoryItems);

    }
    @Override
    public void Insert(final CategoryItem categoryItem) {
        myRef.orderByChild("title").equalTo(categoryItem.getTitle()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("Firebase", "Already Exists");
                } else {
                    String key = myRef.push().getKey();
                    categoryItem.setId(key);
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
    public Map<String, Object> getMap() {
        return null;
    }

    @Override
    public CategoryItem getCategoryByID(String id) {
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
    public CategoryItem parse(Map<String, Object> categoryItem) {
        return null;
    }
}
