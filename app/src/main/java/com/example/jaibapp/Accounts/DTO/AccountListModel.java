package com.example.jaibapp.Accounts.DTO;

public class AccountListModel {
    private String Title;
    private int PictureId;
    private Double CurrentCurrency;
    private String key;
    private String Uid;


    public AccountListModel(String title, int pictureId, Double currentCurrency, String key) {
        Title = title;
        PictureId = pictureId;
        CurrentCurrency = currentCurrency;
        this.key = key;

    }

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getPictureId() {
        return PictureId;
    }

    public void setPictureId(int pictureId) {
        PictureId = pictureId;
    }

    public Double getCurrentCurrency() {
        return CurrentCurrency;
    }

    public void setCurrentCurrency(Double currentCurrency) {
        CurrentCurrency = currentCurrency;
    }
    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

}
