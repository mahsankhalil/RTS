package com.example.jaibapp.Accounts;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaibapp.Accounts.DTO.AccountListModel;
import com.example.jaibapp.Accounts.ViewModel.AccountViewModel;
import com.example.jaibapp.Utilities.ImageArray;
import com.example.jaibapp.R;
import com.example.jaibapp.Repository.Accounts.AccountRepository;
import com.example.jaibapp.Utilities.ImageGridAdapter;

public class AddAccount extends AppCompatActivity {

    final int resultCode = 2;

    AccountViewModel mAccountViewModel;
    EditText mAccountName;
    EditText mOpeningBalance;
    ImageView mSelectedImage;
    TextView mCurrency;
    String mLocalCurrency = "PKR";
    Context mContext;
    int mSelectedImageId = ImageArray.mThumbIds[0];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);
        Toolbar toolbar = findViewById(R.id.add_account_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = getApplicationContext();
        mCurrency = findViewById(R.id.add_account_locale_currency);
        mCurrency.setText(mLocalCurrency);
        mAccountName = findViewById(R.id.add_account_account_name);
        mOpeningBalance = findViewById(R.id.add_account_account_balance);
        mSelectedImage = findViewById(R.id.add_account_selected_image);
        mSelectedImage.setImageResource(ImageArray.mThumbIds[0]);

        mAccountViewModel = ViewModelProviders.of(this).get(AccountRepository.class);
        mSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        Intent intent = getIntent();
        if(intent.hasExtra("ID"))
        {
            String title = intent.getStringExtra("TITLE");
            Double currency = intent.getDoubleExtra("CURRENCY",0);
            Integer id = intent.getIntExtra("ID",0);
            Integer position = intent.getIntExtra("POSITION",0);
            mAccountName.setText(title);
            mOpeningBalance.setText(currency.toString());
            mSelectedImage.setImageResource(ImageArray.mThumbIds[position]);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_account_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.add_account_menu_accept:
                String title = mAccountName.getText().toString();
                Double currency =  Double.parseDouble(mOpeningBalance.getText().toString());

                AccountListModel model = new AccountListModel(title,mSelectedImageId,currency,"-1");
                if(title.isEmpty() || currency<0)
                {
                    Toast.makeText(mContext,"Information is Not Complete",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = getIntent();
                    intent.putExtra("title", title);
                    intent.putExtra("currency", currency);
                    intent.putExtra("pictureId", mSelectedImageId);
                    if (intent.hasExtra("ID"))
                    {
                        String id = intent.getStringExtra("ID");
                        int Position = intent.getIntExtra("POSITION",0);
                        intent.putExtra("ID",id);
                        intent.putExtra("POSITION",Position);
                    }
                    setResult(RESULT_OK,intent);
                    super.finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showAlertDialog() {
        // Prepare grid view

        GridView gridView = new GridView(mContext);
        gridView.setPadding(100,60,10,80);
        ImageGridAdapter adapter = new ImageGridAdapter(mContext);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(3);


        // Set grid view to alertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddAccount.this);
        builder.setView(gridView);
        builder.setTitle("Select Image");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedImage.setImageResource(ImageArray.mThumbIds[position]);
                mSelectedImageId = ImageArray.mThumbIds[position];
                alertDialog.dismiss();
            }
        });

    }


}
