package com.example.jaibapp.CategoryIncomeExpense.DTO;

public class CategoryItem {
    private String title;
    private int pictureId;
    private String id;
    private String Uid;

    public CategoryItem(String title, int pictureId) {
        this.title = title;
        this.pictureId = pictureId;
        this.id = id;
    }

    public CategoryItem(String title, int pictureId,String id) {
        this.title = title;
        this.pictureId = pictureId;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

}
