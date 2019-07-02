package com.example.jaibapp.Repository.Receipt;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Receipt.DTO.ReceiptModel;
import com.example.jaibapp.Receipt.ViewModel.ReceiptViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpenseReceiptRepository extends ReceiptViewModel {
    MutableLiveData<List<ReceiptModel>> data = null;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("Expenses");

    @Override
    public MutableLiveData<List<ReceiptModel>> getAll() {
        if(data == null)
        {
            data = new MutableLiveData<>();
            List<ReceiptModel> list = new ArrayList<>();
            data.setValue(list);
        }


        myRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("Firebase", "collectCategories: help");
                        if(dataSnapshot.exists())
                            collectExpenseReceipt(((Map<String,Object>) dataSnapshot.getValue()));
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Failed to read value
                        Log.d("Firebase", "Failed to read value.", databaseError.toException());
                    }
                });



        return data;
    }

    private void collectExpenseReceipt(Map<String,Object> expenseReceipt) {

        ArrayList<ReceiptModel> expenseReceiptItems = new ArrayList<>();


        for (Map.Entry<String, Object> entry : expenseReceipt.entrySet()){

            Map receiptItem = (Map) entry.getValue();
            ReceiptModel receiptModel =  new ReceiptModel(receiptItem.get("id").toString(),
                    String.valueOf(receiptItem.get("categoryID")),
                    receiptItem.get("accountID").toString(),
                    Double.parseDouble(receiptItem.get("receiptAmount").toString()),
                    receiptItem.get("date").toString(),
                    receiptItem.get("tags").toString(),
                    receiptItem.get("description").toString());
            expenseReceiptItems.add(receiptModel);

        }
        data.setValue(expenseReceiptItems);

    }

    @Override
    public void AddReceipt(ReceiptModel receiptModel) {
        String key = myRef.push().getKey();
        receiptModel.setID(key);
        receiptModel.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.child(key)
                .setValue(receiptModel);
    }
}
