package com.example.jaibapp.Receipt.DTO;

public class ReceiptModel {
    private String ID;
    private String mCategoryID;
    private String mAccountID;
    private Double mReceiptAmount;
    private String mDate;
    private String mTags ;
    private String mDescription;



    private String Uid;
    public ReceiptModel(String ID, String mCategoryID, String mAccountID, Double mReceiptAmount, String mDate, String mTags, String mDescription) {
        this.ID = ID;
        this.mCategoryID = mCategoryID;
        this.mAccountID = mAccountID;
        this.mReceiptAmount = mReceiptAmount;
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

    public Double getReceiptAmount() {
        return mReceiptAmount;
    }

    public void setReceiptAmount(Double mReceiptAmount) {
        this.mReceiptAmount = mReceiptAmount;
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

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
