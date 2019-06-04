package com.example.jaibapp.Receipt.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jaibapp.Receipt.DTO.ReceiptModel;

import java.util.List;

public abstract class ReceiptViewModel extends ViewModel {
    public abstract MutableLiveData<List<ReceiptModel>> getAll();
    public abstract void AddReceipt(ReceiptModel receiptModel);

}
