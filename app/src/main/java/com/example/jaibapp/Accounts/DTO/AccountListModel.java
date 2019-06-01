package com.example.jaibapp.Accounts.DTO;

public class AccountListModel {
    private String Title;
    private int PictureId;
    private Double CurrentCurrency;
    private int Id;

    public AccountListModel(String title, int pictureId, Double currentCurrency, int Id) {
        Title = title;
        PictureId = pictureId;
        CurrentCurrency = currentCurrency;
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
}
