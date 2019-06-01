package com.example.jaibapp.Accounts.Fragments;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.Adapter.AccountSourceRecyclerAdapter;
import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.Accounts.AccountRepository;

import java.util.List;

public class AccountSourceFragment extends Fragment {
    private AccountViewModel mAccountViewModel;
    public static AccountSourceFragment newInstance() {
        return new AccountSourceFragment();
    }
    String LocalCurrentCurrency = "PKR 1000";
    TextView mLocaleCurrency;
    RecyclerView accountRecyclerView;
    AccountSourceRecyclerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_source,container,false);
        mLocaleCurrency = view.findViewById(R.id.account_source_currency);
        mLocaleCurrency.setText(LocalCurrentCurrency);
        accountRecyclerView = view.findViewById(R.id.account_source_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        accountRecyclerView.setLayoutManager(layoutManager);
        mAccountViewModel = ViewModelProviders.of(this).get(AccountRepository.class);


        adapter = new AccountSourceRecyclerAdapter(view.getContext(),mAccountViewModel);
        adapter.setItemList(mAccountViewModel.getAll().getValue());
        accountRecyclerView.setAdapter(adapter);



        FloatingActionButton fab = view.findViewById(R.id.account_source_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
