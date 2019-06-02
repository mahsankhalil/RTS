package com.example.jaibapp.Accounts.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.Adapter.AccountSourceRecyclerAdapter;
import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.ImageArray;
import com.example.jaibapp.Utilities.ImageGridAdapter;
import com.example.jaibapp.Accounts.AddAccount;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.Accounts.AccountRepository;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AccountSourceFragment extends Fragment implements AccountSourceRecyclerAdapter.CallbackInterface {
    private AccountViewModel mAccountViewModel;
    public static AccountSourceFragment newInstance() {
        return new AccountSourceFragment();
    }
    String LocalCurrentCurrency = "PKR ";
    TextView mLocaleCurrency;
    RecyclerView accountRecyclerView;
    AccountSourceRecyclerAdapter adapter;
    Context mContext;

    final int mRequestCode = 1;
    final int EditIntentRequestCode = 11;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == mRequestCode && data.hasExtra("title"))
        {
            String title = data.getStringExtra("title");
            int pictureId = data.getIntExtra("pictureId", ImageArray.mThumbIds[0]);
            Double currency = data.getDoubleExtra("currency",0);
            AccountListModel listModel = new AccountListModel(title,pictureId,currency,-1);
            mAccountViewModel.AddAccount(listModel);
        } else if(resultCode == RESULT_OK && requestCode == EditIntentRequestCode && data.hasExtra("ID")) {
            Toast.makeText(getContext(),"EDITED",Toast.LENGTH_SHORT).show();
            String title = data.getStringExtra("title");
            int pictureId = data.getIntExtra("pictureId", ImageArray.mThumbIds[0]);
            Double currency = data.getDoubleExtra("currency",0);
            int id = data.getIntExtra("ID",0);
            int position = data.getIntExtra("POSITION",0);
            AccountListModel listModel = new AccountListModel(title,pictureId,currency,id);
            mAccountViewModel.EditAccount(listModel,position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_source,container,false);
        mLocaleCurrency = view.findViewById(R.id.account_source_currency);
        accountRecyclerView = view.findViewById(R.id.account_source_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        accountRecyclerView.setLayoutManager(layoutManager);
        mAccountViewModel = ViewModelProviders.of(this).get(AccountRepository.class);



        adapter = new AccountSourceRecyclerAdapter(view.getContext(),mAccountViewModel,this);
        List<AccountListModel> acc = mAccountViewModel.getAll().getValue();
        adapter.setItemList(acc);
        accountRecyclerView.setAdapter(adapter);
        Double currentCurrency = 0.0;
        for(int i=0;i<acc.size();i++)
            currentCurrency+= acc.get(i).getCurrentCurrency();
        String cur =LocalCurrentCurrency + currentCurrency ;
        mLocaleCurrency.setText(cur);
        mAccountViewModel.getAll().observe(this, new Observer<List<AccountListModel>>() {
            @Override
            public void onChanged(@Nullable List<AccountListModel> list) {
                adapter.setItemList(list);
                Double currentCurrency = 0.0;
                for(int i=0;i<list.size();i++)
                    currentCurrency+= list.get(i).getCurrentCurrency();
                String cur =LocalCurrentCurrency + currentCurrency ;
                mLocaleCurrency.setText(cur);
            }
        });

        mContext = view.getContext();

        FloatingActionButton fab = view.findViewById(R.id.account_source_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAccount.class);
                startActivityForResult(intent,mRequestCode);
            }
        });


        return view;
    }


    @Override
    public void onHandleSelection(String title,Double currency,int position,int id) {
        Intent intent = new Intent(getContext(),AddAccount.class);
        intent.putExtra("ID",id);
        intent.putExtra("TITLE",title);
        intent.putExtra("POSITION",position);
        intent.putExtra("CURRENCY",currency);
        startActivityForResult(intent,EditIntentRequestCode);
    }
}
