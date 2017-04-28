package com.fiuady.android.compustorevv10;

/**
 * Created by Manuel on 04/04/2017.
 */
public class Product_Categories{
    private int id;
    private String description;


    public Product_Categories(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
