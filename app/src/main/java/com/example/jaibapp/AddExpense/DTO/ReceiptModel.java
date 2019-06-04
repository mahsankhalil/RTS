package com.example.jaibapp.AddExpense.DTO;

public class ReceiptModel {
    private String ID;
    private String mCategoryID;
    private String mAccountID;
    private Double mExpenseAmount;
    private String mDate;
    private String mTags ;
    private String mDescription;
    public ReceiptModel(String ID, String mCategoryID, String mAccountID, Double mExpenseAmount, String mDate, String mTags, String mDescription) {
        this.ID = ID;
        this.mCategoryID = mCategoryID;
        this.mAccountID = mAccountID;
        this.mExpenseAmount = mExpenseAmount;
        this.mDate = mDate;
        this.mTags = mTags;
        this.mDescription = mDescription;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCategoryID() {
        return mCategoryID;
    }

    public void setCategoryID(String mCategoryID) {
        this.mCategoryID = mCategoryID;
    }

    public String getAccountID() {
        return mAccountID;
    }

    public void setAccountID(String mAccountID) {
        this.mAccountID = mAccountID;
    }

    public Double getExpenseAmount() {
        return mExpenseAmount;
    }

    public void setExpenseAmount(Double mExpenseAmount) {
        this.mExpenseAmount = mExpenseAmount;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTags() {
        return mTags;
    }

    public void setTags(String mTags) {
        this.mTags = mTags;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
