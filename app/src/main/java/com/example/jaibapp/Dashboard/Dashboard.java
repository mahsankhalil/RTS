package com.example.jaibapp.Dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.example.jaibapp.Accounts.AddAccount;
import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.Fragments.AccountSourceFragment;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.Dashboard.Adapter.ReceiptAdapter;
import com.example.jaibapp.Receipt.ExpenseReceiptActivity;
import com.example.jaibapp.Receipt.DTO.ReceiptModel;
import com.example.jaibapp.Receipt.IncomeReceiptActivity;
import com.example.jaibapp.Receipt.ViewModel.ReceiptViewModel;
import com.example.jaibapp.Dashboard.Adapter.AccountRecyclerAdapter;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.Accounts.AccountRepository;
import com.example.jaibapp.Repository.CategoryIncomeExpense.CategoryExpenseRepository;
import com.example.jaibapp.Repository.CategoryIncomeExpense.CategoryIncomeRepository;
import com.example.jaibapp.Repository.Receipt.ExpenseReceiptRepository;
import com.example.jaibapp.Repository.Receipt.IncomeReceiptRepository;
import com.example.jaibapp.Utilities.FragmentCommunicator;
import com.example.jaibapp.Utilities.ImageArray;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Dashboard extends Fragment implements AccountRecyclerAdapter.CallBackInterface, ReceiptAdapter.ReceiptCommunicator {


    final int ADD_EXPENSE_REQUEST_CODE = 1;
    final int ADD_INCOME_REQUEST_CODE = 2;

    FloatingActionButton floatingActionButton;
    TextView mAccountCurrentBalance;
    int mAddAccountRequestCode;

    AccountRecyclerAdapter mAccountAdapter;
    ReceiptAdapter mIncomeReceiptAdapter;
    ReceiptAdapter mExpenseReceiptAdapter;


    TextView mIncomeTotalList;
    TextView mExpenseTotalList;

    AccountViewModel mAccountViewModel;
    TextView mAccountSeeMoreBtn;
    FragmentCommunicator communicator;

    TextView mIncomeTotal;
    TextView mExpenseTotal;

    Context mContext;



    ReceiptViewModel mExpenseReceiptViewModel;
    ReceiptViewModel mIncomeReceiptViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard,container,false);
        mAccountViewModel = ViewModelProviders.of(this).get(AccountRepository.class);
        final List<AccountListModel> list = mAccountViewModel.getAll().getValue();
        mAccountAdapter = new AccountRecyclerAdapter(view.getContext(),list,this);
        mAccountSeeMoreBtn = view.findViewById(R.id.dashboard_account_see_more_btn);
        mAccountCurrentBalance = view.findViewById(R.id.dashboard_Accounts_total_amount);

        mContext = getContext();

        mIncomeTotalList = view.findViewById(R.id.dashboard_Income_total_amount);
        mExpenseTotalList = view.findViewById(R.id.dashboard_Expense_total_amount);


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

        mIncomeTotal = view.findViewById(R.id.dashboard_income_amount);
        mIncomeTotal.setText("100");
        mExpenseTotal = view.findViewById(R.id.dashboard_expense_amount);



        mExpenseReceiptViewModel = ViewModelProviders.of(this).get(ExpenseReceiptRepository.class);
        mIncomeReceiptViewModel = ViewModelProviders.of(this).get(IncomeReceiptRepository.class);

        mIncomeReceiptAdapter = new ReceiptAdapter(mContext,mIncomeReceiptViewModel.getAll().getValue(),this,ReceiptAdapter.INCOME);
        mExpenseReceiptAdapter = new ReceiptAdapter(mContext,mExpenseReceiptViewModel.getAll().getValue(),this,ReceiptAdapter.EXPENSE);



        mExpenseReceiptViewModel.getAll().observe(this, new Observer<List<ReceiptModel>>() {
            @Override
            public void onChanged(@Nullable List<ReceiptModel> receiptModels) {
                Double sum = 0.0;
                for (int i=0;i<receiptModels.size();i++)
                    sum = sum + receiptModels.get(i).getReceiptAmount();
                mExpenseTotal.setText(sum.toString());
                mExpenseTotalList.setText(sum.toString());
                mExpenseReceiptAdapter.setData(receiptModels);
            }
        });

        mIncomeReceiptViewModel.getAll().observe(this, new Observer<List<ReceiptModel>>() {
            @Override
            public void onChanged(@Nullable List<ReceiptModel> receiptModels) {
                Double sum = 0.0;
                for (int i=0;i<receiptModels.size();i++)
                    sum = sum + receiptModels.get(i).getReceiptAmount();
                mIncomeTotal.setText(sum.toString());
                mIncomeTotalList.setText(sum.toString());
                mIncomeReceiptAdapter.setData(receiptModels);
            }
        });






        floatingActionButton = view.findViewById(R.id.dashboard_add_expense_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext,floatingActionButton);
                popupMenu.inflate(R.menu.dashboard_add_receipt_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.dashboard_add_receipt_income_btn)
                        {
                            Intent intent = new Intent(getActivity(), IncomeReceiptActivity.class);
                            startActivityForResult(intent,ADD_INCOME_REQUEST_CODE);
                            return true;
                        }
                        else if(item.getItemId() == R.id.dashboard_add_receipt_expense_btn)
                        {
                            Intent intent = new Intent(getActivity(), ExpenseReceiptActivity.class);
                            startActivityForResult(intent,ADD_EXPENSE_REQUEST_CODE);
                            return true;
                        }
                        return false;
                    }
                });
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



        RecyclerView mIncomeRecyclerView = view.findViewById(R.id.dashboard_income_list_recycler_view);
        RecyclerView mExpenseRecyclerView = view.findViewById(R.id.dashboard_Expense_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(view.getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mIncomeRecyclerView.setAdapter(mIncomeReceiptAdapter);
        mExpenseRecyclerView.setAdapter(mExpenseReceiptAdapter);

        mIncomeRecyclerView.setLayoutManager(layoutManager);
        mExpenseRecyclerView.setLayoutManager(layoutManager1);






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
            AccountListModel listModel = new AccountListModel(title,pictureId,currency,"-1");
            mAccountViewModel.AddAccount(listModel);
        }
        else if(resultCode == RESULT_OK && requestCode == ADD_EXPENSE_REQUEST_CODE)
        {
            if(data.hasExtra("ID"))
            {

            }
            else
            {
                String categoryId = data.getStringExtra("CATEGORY_ID");
                String accountId = data.getStringExtra("ACCOUNT_ID");
                Double expenseAmount = data.getDoubleExtra("EXPENSE_AMOUNT",0.0);
                String selectedDate = data.getStringExtra("SELECTED_DATE");
                String tags = data.getStringExtra("TAGS");
                String description = data.getStringExtra("DESCRIPTION");
                ReceiptModel receiptModel = new ReceiptModel("-1",categoryId,accountId,expenseAmount,selectedDate,tags,description);
                mExpenseReceiptViewModel.AddReceipt(receiptModel);

            }
        }
        else if(resultCode == RESULT_OK && requestCode == ADD_INCOME_REQUEST_CODE) {
            if(data.hasExtra("ID"))
            {

            }
            else
            {
                String categoryId = data.getStringExtra("CATEGORY_ID");
                String accountId = data.getStringExtra("ACCOUNT_ID");
                Double expenseAmount = data.getDoubleExtra("INCOME_AMOUNT",0.0);
                String selectedDate = data.getStringExtra("SELECTED_DATE");
                String tags = data.getStringExtra("TAGS");
                String description = data.getStringExtra("DESCRIPTION");
                ReceiptModel receiptModel = new ReceiptModel("-1",categoryId,accountId,expenseAmount,selectedDate,tags,description);
                mIncomeReceiptViewModel.AddReceipt(receiptModel);
            }

        }
    }

    @Override
    public CategoryItem getCategoryItem(String id,int Mode) {
        IncomeExpenseViewModel viewModel;
        if(Mode == ReceiptAdapter.INCOME)
            viewModel = ViewModelProviders.of(this).get(CategoryIncomeRepository.class);
        else
            viewModel = ViewModelProviders.of(this).get(CategoryExpenseRepository.class);
        return viewModel.getCategoryByID(id);
    }

    @Override
    public AccountListModel getAccountItem(String id) {
        AccountViewModel viewModel = ViewModelProviders.of(this).get(AccountRepository.class);
        return viewModel.getAccountByID(id);
    }
}
