package com.example.jaibapp.Repository.Receipt;

import android.arch.lifecycle.MutableLiveData;

import com.example.jaibapp.Receipt.DTO.ReceiptModel;
import com.example.jaibapp.Receipt.ViewModel.ReceiptViewModel;

import java.util.ArrayList;
import java.util.List;

public class IncomeReceiptRepository extends ReceiptViewModel {
    MutableLiveData<List<ReceiptModel>> data = null;

    @Override
    public MutableLiveData<List<ReceiptModel>> getAll() {
        if(data == null)
        {
            data = new MutableLiveData<>();
            List<ReceiptModel> list = new ArrayList<>();
            list.add(new ReceiptModel("1","1","1",3000.0,"25-jun-2019","food","Lovely"));
            list.add(new ReceiptModel("2","1","2",2000.0,"25-jun-2019","food","Lovely"));
            data.setValue(list);
        }
        return data;
    }
    @Override
    public void AddReceipt(ReceiptModel receiptModel) {
        if(data == null)
        {
            data = new MutableLiveData<>();
            List<ReceiptModel> list = new ArrayList<>();
            data.setValue(list);
        }
        List<ReceiptModel> list = data.getValue();
        list.add(receiptModel);
        data.setValue(list);
    }
}
