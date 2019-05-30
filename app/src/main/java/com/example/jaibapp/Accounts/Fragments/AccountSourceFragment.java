package com.example.jaibapp.Accounts.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaibapp.R;

public class AccountSourceFragment extends Fragment {
    public static AccountSourceFragment newInstance() {
        return new AccountSourceFragment();
    }
    String LocalCurrentCurrency = "PKR";
    TextView mLocaleCurrency;
    TextView mTotalBalance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_source,container,false);
        mLocaleCurrency = view.findViewById(R.id.account_source_currency);
        mTotalBalance = view.findViewById(R.id.account_source_total_balance);
        mLocaleCurrency.setText(LocalCurrentCurrency);
        mTotalBalance.setText("1000");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
