package com.example.jaibapp.AddExpense;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.AddExpense.Adapter.AccountSpinnerAdapter;
import com.example.jaibapp.AddExpense.Adapter.CategorySpinnerAdapter;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.CategoryIncomeExpenseViewModel;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.Accounts.AccountRepository;
import com.example.jaibapp.Repository.CategoryIncomeExpense.CategoryExpenseRepository;

import java.util.List;

public class AddExpenseActivity extends AppCompatActivity  {

    String mLocalCurrencyStr = "PKR";

    AccountViewModel mAccountViewModel;
    IncomeExpenseViewModel mCategoryIncomeExpenseViewModel;


    Context mContext;
    TextView mLocaleCurrency;
    EditText mExpenseAmount;
    EditText mExpenseDate;
    EditText mExpenseTags;
    EditText mDescription;
    TextView mImageExpense;
    Spinner mCategorySpinner;
    Spinner mAccountSpinner;

    AccountSpinnerAdapter mAccountSpinnerAdapter;
    CategorySpinnerAdapter mCategorySpinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);
        Toolbar toolbar = findViewById(R.id.add_expense_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = getApplicationContext();

        mLocaleCurrency = findViewById(R.id.add_expense_currency);
        mLocaleCurrency.setText(mLocalCurrencyStr);

        mExpenseAmount = findViewById(R.id.add_expense_amount);
        mExpenseDate = findViewById(R.id.add_expense_calender);
        mExpenseTags = findViewById(R.id.add_expense_tags);
        mDescription = findViewById(R.id.add_expense_description);
        mImageExpense = findViewById(R.id.add_expense_attach_image);
        mAccountSpinner = findViewById(R.id.add_expense_account_spinner);
        mCategorySpinner = findViewById(R.id.add_expense_category_spinner);

        mAccountViewModel = ViewModelProviders.of(this).get(AccountRepository.class);
        mCategoryIncomeExpenseViewModel = ViewModelProviders.of(this).get(CategoryExpenseRepository.class);

        List<CategoryItem> categoryItems = mCategoryIncomeExpenseViewModel.getAllData().getValue();
        List<AccountListModel> accountListModels = mAccountViewModel.getAll().getValue();

        mAccountSpinnerAdapter = new AccountSpinnerAdapter(getApplicationContext(),accountListModels);
        mCategorySpinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(),categoryItems);

        mAccountSpinner.setAdapter(mAccountSpinnerAdapter);
        mCategorySpinner.setAdapter(mCategorySpinnerAdapter);



        mImageExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Hi",Toast.LENGTH_SHORT).show();
            }
        });








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_account_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.add_account_menu_accept:
                Toast.makeText(mContext,"Hell",Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }



}
