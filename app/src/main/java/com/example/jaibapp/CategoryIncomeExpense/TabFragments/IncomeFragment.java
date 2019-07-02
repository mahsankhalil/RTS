package com.example.jaibapp.CategoryIncomeExpense.TabFragments;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.CategoryIncomeExpense.Adapter.CategoryIncomeExpenseAdapter;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.CategoryIncomeExpense.CategoryIncomeRepository;

import java.util.List;

public class IncomeFragment extends Fragment {

    String title;
    int pic;
    View view;
    private IncomeExpenseViewModel mViewModel;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }
    CategoryIncomeExpenseAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.income_fragment, container, false);
        recyclerView = view.findViewById(R.id.category_income_expense_fragment_recycler_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryIncomeExpenseAdapter(view.getContext());
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.category_income_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return view;
    }

    void openDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);




        final View view  = getActivity().getLayoutInflater().inflate(R.layout.category_expense_income_add_item_dailog, null);
        dialog.setContentView(view);
        TextView deleteButton = view.findViewById(R.id.category_expense_income_add_item_dialog_item_delete);
        TextView saveButton = view.findViewById(R.id.save_update_budget);
        TextView cancelButton = view.findViewById(R.id.cancel_budget_update);
        TextView title = view.findViewById(R.id.update_budget);

        deleteButton.setVisibility(View.GONE);

        title.setText("Add Income Item");


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText title = view.findViewById(R.id.budget_amount);
                String t = title.getText().toString();

                mViewModel.Insert(new CategoryItem(t,R.drawable.ic_menu_budget,"-1"));
                adapter.setValue(mViewModel.getAll().getValue());
                Toast.makeText(getActivity(),"Save",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Cancle",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CategoryIncomeRepository.class);
        // TODO: Use the ViewModel
        mViewModel.getAll().observe(this, new Observer<List<CategoryItem>>() {
            @Override
            public void onChanged(@Nullable List<CategoryItem> categoryItems) {
                adapter.setValue(categoryItems);
            }
        });
        adapter.setValue(mViewModel.getAll().getValue());



    }


}
