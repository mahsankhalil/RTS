package com.example.jaibapp.Budget.Adapter;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Budget.DTO.BudgetModel;
import com.example.jaibapp.Budget.Fragments.BudgetFragment;
import com.example.jaibapp.Budget.SetBudget;
import com.example.jaibapp.Budget.ViewModel.BudgetViewModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.CategoryIncomeExpense.ViewModel.IncomeExpenseViewModel;
import com.example.jaibapp.R;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.Holder>{

    private BudgetViewModel budgetViewModel;
    private IncomeExpenseViewModel expenseCategoryViewModel;
    private List<BudgetModel> budgetList;
    private List<CategoryItem> categoryList;
    private Context context;



    public BudgetAdapter(Context context,BudgetViewModel budgetViewModel, IncomeExpenseViewModel expenseCategoryViewModel){
        this.context = context;
        this.budgetViewModel = budgetViewModel;
        this.expenseCategoryViewModel = expenseCategoryViewModel;
    }

    public void setBudgetList(List<BudgetModel> budgetList) {
        this.budgetList = budgetList;
    }

    public void setCategoryList(List<CategoryItem> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.monthly_budget_list_item, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull Holder holder,final int i) {
        Log.e("bind", "onBindViewHolder: "+ "inside binder" );
        final BudgetModel budgetModel = budgetList.get(i);
        Map categoryMap = (Map) expenseCategoryViewModel.getMap().get(budgetModel.getCategoryID());
        final CategoryItem categoryItem = expenseCategoryViewModel.parse(categoryMap);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = categoryItem.toString();
                int imageId = categoryItem.getPictureId();
                String id = budgetModel.getId();
                String month = budgetModel.getMonth();
                String year =budgetModel.getYear();
                String categoryId = budgetModel.getCategoryID();
                Double amount = budgetModel.getAmount();

                Intent intent = new Intent(context, SetBudget.class);
                intent.putExtra(BudgetFragment.EX_CATEGORY_ID, categoryId);
                intent.putExtra(BudgetFragment.EX_CATEGORY_IMAGE_ID,imageId);
                intent.putExtra(BudgetFragment.EX_CATEGORY_TITLE,title);
                intent.putExtra(BudgetFragment.EX_BUDGET_ID,id);
                intent.putExtra(BudgetFragment.EX_BUDGET_MONTH,month);
                intent.putExtra(BudgetFragment.EX_BUDGET_YEAR,year);
                intent.putExtra(BudgetFragment.EX_BUDGET_AMOUNT,amount);
                context.startActivity(intent);

            }
        });

        holder.budgetImage.setImageResource(categoryItem.getPictureId());
        holder.budgetTitle.setText(categoryItem.getTitle());
        holder.bugetAmount.setText(budgetModel.getAmount().toString());
        holder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.menuButton);
                popupMenu.inflate(R.menu.budget_menu_list);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.budget_menu_list_edit:

                                openDialog(budgetModel);

                                return true;
                            case R.id.budget_menu_list_unpin:
                                budgetViewModel.remove(budgetList.get(holder.getAdapterPosition()));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });

    }


    private void openDialog(final BudgetModel budgetModel) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);


        final View view  = LayoutInflater.from(context).inflate(R.layout.update_budget, null);
        dialog.setContentView(view);
        TextView saveButton = view.findViewById(R.id.save_update_budget);
        TextView cancelButton = view.findViewById(R.id.cancel_budget_update);
        TextView title = view.findViewById(R.id.update_budget);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText amount = view.findViewById(R.id.set_budget_amount);

                budgetModel.setAmount(Double.parseDouble(amount.getText().toString()));

                budgetViewModel.update(budgetModel);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView budgetImage;
        TextView budgetTitle;
        CardView cardView;
        TextView bugetAmount;
        Button menuButton;
        public Holder(@NonNull View itemView) {
            super(itemView);
            budgetImage = itemView.findViewById(R.id.monthly_budget_list_item_image);
            budgetTitle = itemView.findViewById(R.id.account_source_list_item_title);
            cardView = itemView.findViewById(R.id.monthly_budget_list_item_card_view);
            menuButton = itemView.findViewById(R.id.monthly_budget_list_item_menu_icon);
            bugetAmount = itemView.findViewById(R.id.budget_amount);
        }
    }

}
