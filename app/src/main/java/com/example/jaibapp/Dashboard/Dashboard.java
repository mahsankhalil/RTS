package com.example.jaibapp.Dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.TextView;


import com.example.jaibapp.Accounts.AddAccount;
import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.Fragments.AccountSourceFragment;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.AddExpense.AddExpenseActivity;
import com.example.jaibapp.Dashboard.Adapter.AccountRecyclerAdapter;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.Accounts.AccountRepository;
import com.example.jaibapp.Utilities.FragmentCommunicator;
import com.example.jaibapp.Utilities.ImageArray;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Dashboard extends Fragment implements AccountRecyclerAdapter.CallBackInterface {


    FloatingActionButton floatingActionButton;
    TextView mAccountCurrentBalance;
    int mAddAccountRequestCode;
    AccountRecyclerAdapter mAccountAdapter;
    AccountViewModel mAccountViewModel;
    TextView mAccountSeeMoreBtn;
    FragmentCommunicator communicator;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard,container,false);
        mAccountViewModel = ViewModelProviders.of(this).get(AccountRepository.class);
        List<AccountListModel> list = mAccountViewModel.getAll().getValue();
        mAccountAdapter = new AccountRecyclerAdapter(view.getContext(),list,this);
        mAccountSeeMoreBtn = view.findViewById(R.id.dashboard_account_see_more_btn);
        mAccountCurrentBalance = view.findViewById(R.id.dashboard_Accounts_total_amount);

        Double val = 0.0;
        for(int i =0;i<list.size();i++)
            val += list.get(i).getCurrentCurrency();

        mAccountCurrentBalance.setText(val.toString());

        mAccountSeeMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.ChangeFragmentCallback(new AccountSourceFragment());
            }
        });


        floatingActionButton = view.findViewById(R.id.dashboard_add_expense_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
                startActivityForResult(intent,123);
            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.dashboard_account_recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAccountAdapter);

        mAccountViewModel.getAll().observe(this, new Observer<List<AccountListModel>>() {
            @Override
            public void onChanged(@Nullable List<AccountListModel> list) {
                mAccountAdapter.setList(list);
                Double val = 0.0;
                for(int i =0;i<list.size();i++)
                    val += list.get(i).getCurrentCurrency();
                mAccountCurrentBalance.setText(val.toString());
            }
        });

        return view;
    }


    public void setFragmentCommunicator(FragmentCommunicator fragmentCommunicator)
    {
        communicator = fragmentCommunicator;
    }


    @Override
    public void AddNewAccount() {
        Intent intent = new Intent(getActivity(), AddAccount.class);
        startActivityForResult(intent,mAddAccountRequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == mAddAccountRequestCode && data.hasExtra("title"))
        {
            String title = data.getStringExtra("title");
            int pictureId = data.getIntExtra("pictureId", ImageArray.mThumbIds[0]);
            Double currency = data.getDoubleExtra("currency",0);
            AccountListModel listModel = new AccountListModel(title,pictureId,currency,-1);
            mAccountViewModel.AddAccount(listModel);
        }
    }
}
