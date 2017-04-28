package com.fiuady.android.compustorevv10.Productos;

/**
 * Created by Diego Hernandez on 26/04/2017.
 */

public class Proudct_Category {
    int Category_Id;
    String description;

    public Proudct_Category(int category_Id, String description) {
        this.Category_Id = category_Id;
        this.description = description;
    }

    public int getCategory_Id() {
        return Category_Id;
    }

    public void setCategory_Id(int category_Id) {
        Category_Id = category_Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
