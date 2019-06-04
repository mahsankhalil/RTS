package com.example.jaibapp.AddExpense.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jaibapp.AddExpense.DTO.ReceiptModel;

import java.util.List;

public abstract class RecieptViewModel extends ViewModel {
    public abstract MutableLiveData<List<ReceiptModel>> getAll();
    public abstract void AddReceipt(ReceiptModel receiptModel);
}
