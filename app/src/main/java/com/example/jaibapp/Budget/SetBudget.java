package com.example.jaibapp.Budget;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Budget.DTO.BudgetModel;
import com.example.jaibapp.Budget.Fragments.BudgetFragment;
import com.example.jaibapp.Budget.ViewModel.BudgetViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;
import com.example.jaibapp.Receipt.Adapter.CategorySpinnerAdapter;
import com.example.jaibapp.Repository.Accounts.AccountRepository;
import com.example.jaibapp.Repository.Budget.BudgetRepository;
import com.example.jaibapp.Repository.CategoryIncomeExpense.CategoryExpenseRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SetBudget extends AppCompatActivity {

    BudgetViewModel budgetViewModel;
    IncomeExpenseViewModel categoryIncomeExpenseViewModel;
    Context context;

    int categoryPosition = 0;


    TextView amount;
    Spinner categorySpinner;
    CategorySpinnerAdapter categorySpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_budget);
        Toolbar toolbar = findViewById(R.id.add_budget_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();

        budgetViewModel = ViewModelProviders.of(this).get(BudgetRepository.class);
        categoryIncomeExpenseViewModel = ViewModelProviders.of(this).get(CategoryExpenseRepository.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<CategoryItem> categoryItems = categoryIncomeExpenseViewModel.getAll().getValue();


        amount = findViewById(R.id.set_budget_amount);
        categorySpinner = findViewById(R.id.add_receipt_category_spinner);
        categorySpinnerAdapter = new CategorySpinnerAdapter(getApplicationContext(), categoryItems);

        categoryIncomeExpenseViewModel.getAll().observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(@Nullable List<CategoryItem> categoryItemList) {
                categorySpinnerAdapter.setData(categoryItemList);
                categorySpinnerAdapter.notifyDataSetChanged();
            }
        });

        categorySpinner.setAdapter(categorySpinnerAdapter);

        amount.setText(bundle.getString(BudgetFragment.EX_BUDGET_AMOUNT));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_budget_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.add_budget_menu_accept:
                Log.e("Kutta", "onOptionsItemSelected: "+ "add budget" );
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();

                Calendar calendar = Calendar.getInstance();

                BudgetModel budgetModel = new BudgetModel(
                        "",
                        String.valueOf(calendar.get(Calendar.MONTH)+1),
                        String.valueOf(calendar.get(Calendar.YEAR)),
                        Double.parseDouble(amount.getText().toString()),
                        categoryIncomeExpenseViewModel.getAt(categoryPosition).getId());
                budgetModel.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
                budgetViewModel.add(budgetModel);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
