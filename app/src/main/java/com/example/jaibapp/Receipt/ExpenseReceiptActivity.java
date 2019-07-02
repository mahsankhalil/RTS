package com.example.jaibapp.Receipt;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.Receipt.Adapter.AccountSpinnerAdapter;
import com.example.jaibapp.Receipt.Adapter.CategorySpinnerAdapter;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.Accounts.AccountRepository;
import com.example.jaibapp.Repository.CategoryIncomeExpense.CategoryExpenseRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseReceiptActivity extends AppCompatActivity  {



    String mLocalCurrencyStr = "PKR";

    AccountViewModel mAccountViewModel;
    IncomeExpenseViewModel mCategoryIncomeExpenseViewModel;


    int mCategoryPosition = 0;
    int mAccountPosition = 0;


    Context mContext;
    TextView mLocaleCurrency;
    EditText mExpenseAmount;
    EditText mExpenseDate;
    EditText mExpenseTags;
    EditText mDescription;

    Spinner mCategorySpinner;
    Spinner mAccountSpinner;

    AccountSpinnerAdapter mAccountSpinnerAdapter;
    CategorySpinnerAdapter mCategorySpinnerAdapter;


    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reciept);
        Toolbar toolbar = findViewById(R.id.add_receipt_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = getApplicationContext();

        mLocaleCurrency = findViewById(R.id.add_receipt_currency);
        mLocaleCurrency.setText(mLocalCurrencyStr);

        mExpenseAmount = findViewById(R.id.add_receipt_amount);
        mExpenseDate = findViewById(R.id.add_receipt_calender);
        mExpenseTags = findViewById(R.id.add_receipt_tags);
        mDescription = findViewById(R.id.add_receipt_description);

        mAccountSpinner = findViewById(R.id.add_receipt_account_spinner);
        mCategorySpinner = findViewById(R.id.add_receipt_category_spinner);

        mAccountViewModel = ViewModelProviders.of(this).get(AccountRepository.class);
        mCategoryIncomeExpenseViewModel = ViewModelProviders.of(this).get(CategoryExpenseRepository.class);

        List<CategoryItem> categoryItems = mCategoryIncomeExpenseViewModel.getAll().getValue();
        List<AccountListModel> accountListModels = mAccountViewModel.getAll().getValue();

        mAccountSpinnerAdapter = new AccountSpinnerAdapter(getApplicationContext(),accountListModels);
        mCategorySpinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(),categoryItems);

        mCategoryIncomeExpenseViewModel.getAll().observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(@Nullable List<CategoryItem> categoryItemList) {
                mCategorySpinnerAdapter.setData(categoryItemList);
                mCategorySpinnerAdapter.notifyDataSetChanged();
            }
        });

        mAccountViewModel.getAll().observe(this, new Observer<List<AccountListModel>>() {
            @Override
            public void onChanged(@Nullable List<AccountListModel> accountListModels) {
                mAccountSpinnerAdapter.setData(accountListModels);
                mAccountSpinnerAdapter.notifyDataSetChanged();
            }
        });



        mAccountSpinner.setAdapter(mAccountSpinnerAdapter);
        mCategorySpinner.setAdapter(mCategorySpinnerAdapter);

        mAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(),"ho "+position,Toast.LENGTH_SHORT).show();
                mAccountPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(view.getContext(),"ho "+position,Toast.LENGTH_SHORT).show();
                mCategoryPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };



        mExpenseDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ExpenseReceiptActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Date d = new Date();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mExpenseDate.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateDate() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mExpenseDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_receipt_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.add_receipt_menu_accept:
                Double expenseAmount = Double.parseDouble(mExpenseAmount.getText().toString());
                String selectedDate =  mExpenseDate.getText().toString();
                String tags =  mExpenseTags.getText().toString();
                String description = mDescription.getText().toString();
                AccountListModel accountModel = mAccountViewModel.getAt(mAccountPosition);
                CategoryItem categoryItem = mCategoryIncomeExpenseViewModel.getAt(mCategoryPosition);
                Log.e("Capital Lanat", "onOptionsItemSelected: " + categoryItem.getId() );
                String accountId = accountModel.getKey();
                String categoryId = categoryItem.getId();
                Intent intent = getIntent();
                if(intent.hasExtra("ID"))
                {
                    intent.putExtra("ID",intent.getStringExtra("ID"));
                }
                intent.putExtra("EXPENSE_AMOUNT",expenseAmount);
                intent.putExtra("SELECTED_DATE",selectedDate);
                intent.putExtra("TAGS",tags);
                intent.putExtra("DESCRIPTION",description);
                intent.putExtra("ACCOUNT_ID",accountId);
                intent.putExtra("CATEGORY_ID",categoryId);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
        return false;
    }



}
