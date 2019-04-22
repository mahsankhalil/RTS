package com.example.jaibapp.CategoryIncomeExpense.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.R;

import java.util.List;

public class CategoryIncomeExpenseAdapter extends RecyclerView.Adapter<CategoryIncomeExpenseAdapter.Holder> {

    List<CategoryItem> ItemList;

    Context context;

    public CategoryIncomeExpenseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.category_income_expense_list_item,viewGroup,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.title.setText(ItemList.get(i).getTitle());
        holder.image.setImageResource(ItemList.get(i).getPictureId());
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public void setValue(List<CategoryItem> categoryItems)
    {
        ItemList = categoryItems;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;

        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_income_expense_list_item_image_view);
            title = itemView.findViewById(R.id.category_income_expense_list_item_title);
        }

    }
}
