package com.example.jaibapp.Receipt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaibapp.CategoryIncomeExpense.DTO.CategoryItem;
import com.example.jaibapp.R;
import com.example.jaibapp.Utilities.ImageArray;

import java.util.List;

public class CategorySpinnerAdapter  extends BaseAdapter {

    private Context mContext;
    private List<CategoryItem> mData;
    private LayoutInflater mLayoutInflator;

    public CategorySpinnerAdapter(Context mContext, List<CategoryItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mLayoutInflator = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view = mLayoutInflator.inflate(R.layout.add_receipt_catogory_item_list,viewGroup,false);
        ImageView img = view.findViewById(R.id.add_receipt_category_list_item_image);
        TextView txt = view.findViewById(R.id.add_receipt_category_list_item_title);

        img.setImageResource(ImageArray.mThumbIds[i]);
        txt.setText(mData.get(i).getTitle());
        return view;
    }
}
