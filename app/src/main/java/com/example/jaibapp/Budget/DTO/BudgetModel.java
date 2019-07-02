package com.example.jaibapp.Budget.DTO;

public class BudgetModel {
    private String id;
    private String month;
    private String year;
    private Double amount;
    private String categoryID;
    private String uid;

    public BudgetModel(String id, String month, String year, Double amount, String categoryID) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.amount = amount;
        this.categoryID = categoryID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
