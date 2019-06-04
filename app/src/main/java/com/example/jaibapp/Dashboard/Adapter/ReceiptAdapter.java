package com.example.jaibapp.Dashboard.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.R;
import com.example.jaibapp.Receipt.DTO.ReceiptModel;

import java.util.List;
import java.util.StringTokenizer;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.Holder> {

    public final static int INCOME = 1;
    public final static int EXPENSE = 2;

    private final int CURRENT_MODE;

    Context mContext;
    List<ReceiptModel> mData;
    ReceiptCommunicator communicator;

    public ReceiptAdapter(Context mContext, List<ReceiptModel> mData, ReceiptCommunicator communicator,int Mode) {
        this.mContext = mContext;
        this.mData = mData;
        this.communicator = communicator;
        CURRENT_MODE = Mode;
    }


    public int getCURRENT_MODE() {
        return CURRENT_MODE;
    }

    public interface ReceiptCommunicator{
        CategoryItem getCategoryItem(String id,int mode);
        AccountListModel getAccountItem(String id);
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dashboard_receipt_list_item_view,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        String calender = mData.get(i).getDate();
        String day = "NOT";
        String month = "Available";
        if(!calender.isEmpty())
        {
            StringTokenizer tokenizer = new StringTokenizer(calender);
            day = tokenizer.nextToken("-");
            month = tokenizer.nextToken("-");
        }

        holder.mDay.setText(day);
        holder.mMonth.setText(month);
        CategoryItem categoryItem = communicator.getCategoryItem(mData.get(holder.getAdapterPosition()).getCategoryID(),getCURRENT_MODE());
        AccountListModel accountListModel = communicator.getAccountItem(mData.get(holder.getAdapterPosition()).getAccountID());
        holder.mAccountName.setText(accountListModel.getTitle());
        holder.mCategoryName.setText(categoryItem.getTitle());
        holder.mReceiptBalance.setText(mData.get(i).getReceiptAmount().toString());


    }

    public void setData(List<ReceiptModel> list)
    {
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class Holder extends RecyclerView.ViewHolder{

        TextView mMonth;
        TextView mDay;
        TextView mCategoryName;
        TextView mAccountName;
        TextView mReceiptBalance;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mMonth = itemView.findViewById(R.id.dashboard_receipt_list_item_month);
            mDay = itemView.findViewById(R.id.dashboard_receipt_list_item_day);
            mCategoryName = itemView.findViewById(R.id.dashboard_receipt_list_item_category_name);
            mAccountName = itemView.findViewById(R.id.dashboard_receipt_list_item_account_name);
            mReceiptBalance = itemView.findViewById(R.id.dashboard_receipt_list_item_receiptBalance);

        }
    }
}
