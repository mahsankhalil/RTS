package com.example.jaibapp.Accounts.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.R;

import java.util.List;

public class AccountSourceRecyclerAdapter  extends RecyclerView.Adapter<AccountSourceRecyclerAdapter.Holder> {

    private List<AccountListModel> ItemList;
    Context context;

    public AccountSourceRecyclerAdapter(Context context)
    {
        this.context = context;
    }

    public void setItemList(List<AccountListModel> itemList)
    {
        this.ItemList = itemList;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.account_source_list_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        AccountListModel model = ItemList.get(i);

        holder.mItemImage.setImageResource(model.getPictureId());
        holder.mItemTitle.setText(model.getTitle());
        holder.mCurrentMoney.setText(model.getCurrentCurrency().toString());
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        ImageView mItemImage;
        TextView mItemTitle;
        TextView mCurrentMoney;

        Holder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.account_source_list_item_image);
            mItemTitle = itemView.findViewById(R.id.account_source_list_item_title);
            mCurrentMoney = itemView.findViewById(R.id.account_source_list_item_currency);

        }
    }
}
