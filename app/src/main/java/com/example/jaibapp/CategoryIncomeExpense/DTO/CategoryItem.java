package com.example.jaibapp.CategoryIncomeExpense.DTO;

public class CategoryItem {
    private String title;
    private int pictureId;

    public CategoryItem(String title, int pictureId) {
        this.title = title;
        this.pictureId = pictureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
