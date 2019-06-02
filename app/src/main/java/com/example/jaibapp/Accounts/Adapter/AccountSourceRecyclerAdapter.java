package com.example.jaibapp.Accounts.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.AddAccount;
import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.Fragments.AccountSourceFragment;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.R;

import java.net.InterfaceAddress;
import java.util.List;

public class AccountSourceRecyclerAdapter  extends RecyclerView.Adapter<AccountSourceRecyclerAdapter.Holder> {

    private CallbackInterface mCallback;
    private AccountViewModel mAccountViewModel;
    private List<AccountListModel> ItemList;
    private Context context;

    public interface CallbackInterface{


        void onHandleSelection(String title,Double currency,int position,int id);
    }


    public AccountSourceRecyclerAdapter(Context context, AccountViewModel accountViewModel, AccountSourceFragment accountSourceFragment)
    {
        this.context = context;
        mCallback = accountSourceFragment;
        mAccountViewModel = accountViewModel;
    }

    public void setItemList(List<AccountListModel> itemList)
    {
        this.ItemList = itemList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.account_source_list_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        AccountListModel model = ItemList.get(i);

        holder.mItemImage.setImageResource(model.getPictureId());
        holder.mItemTitle.setText(model.getTitle());
        holder.mCurrentMoney.setText(model.getCurrentCurrency().toString());
        holder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.menuButton);
                popupMenu.inflate(R.menu.account_menu_list);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.account_menu_list_edit:
                                Toast.makeText(context, "Edit"+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                                String title = holder.mItemTitle.getText().toString();
                                Double currency = Double.parseDouble(holder.mCurrentMoney.getText().toString());
                                int pictureId = ItemList.get(holder.getAdapterPosition()).getPictureId();
                                int id = ItemList.get(holder.getAdapterPosition()).getId();
                                mCallback.onHandleSelection(title,currency,holder.getAdapterPosition(),id);
                                return true;
                            case R.id.account_menu_list_unpin:
                                Toast.makeText(context, "Unpin", Toast.LENGTH_SHORT).show();
                                setItemList(mAccountViewModel.RemoveByAccountObject(ItemList.get(holder.getAdapterPosition())).getValue());
                                return true;
                            case R.id.account_menu_list_Activities:
                                Toast.makeText(context, "Activities", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        ImageView mItemImage;
        TextView mItemTitle;
        TextView mCurrentMoney;
        Button menuButton;
        Holder(@NonNull final View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.account_source_list_item_image);
            mItemTitle = itemView.findViewById(R.id.account_source_list_item_title);
            mCurrentMoney = itemView.findViewById(R.id.account_source_list_item_currency);
            menuButton = itemView.findViewById(R.id.account_source_list_item_menu_icon);




        }
    }


}
