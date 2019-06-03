package com.example.jaibapp.Dashboard.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.Adapter.AccountSourceRecyclerAdapter;
import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Dashboard.Dashboard;
import com.example.jaibapp.R;

import java.util.List;

public class AccountRecyclerAdapter extends RecyclerView.Adapter<AccountRecyclerAdapter.Holder> {
    private List<AccountListModel> mData;
    private Context mContext;
    private CallBackInterface mCallBackInterface;


    public AccountRecyclerAdapter(Context mContext, List<AccountListModel> mData, Dashboard dashboard) {
        this.mData = mData;
        this.mContext = mContext;
        mCallBackInterface = dashboard;
    }

    public  interface CallBackInterface{
        void AddNewAccount();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dashboard_account_list_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        if(i==mData.size())
        {

            holder.mItemTitle.setClickable(true);
            holder.mItemTitle.setFocusable(true);
            holder.mItemTitle.setText("New Account");
            holder.mItemTitle.setTextSize(15);
            holder.mCurrentMoney.setText("+");
            holder.mCurrentMoney.setTextSize(23);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Hell",Toast.LENGTH_SHORT).show();
                    mCallBackInterface.AddNewAccount();
                }
            });
        }
        else
        {
            AccountListModel model = mData.get(i);
            holder.mCurrentMoney.setText(model.getCurrentCurrency().toString());
            holder.mItemTitle.setText(model.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    public void setList(List<AccountListModel> mData)
    {
        this.mData = mData;
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder{
        TextView mItemTitle;
        TextView mCurrentMoney;
        CardView cardView;
        Holder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.dashboard_account_list_item_cardview);
            mItemTitle = itemView.findViewById(R.id.dashboard_account_list_item_title);
            mCurrentMoney = itemView.findViewById(R.id.dashboard_account_list_item_currency);
        }
    }
}
