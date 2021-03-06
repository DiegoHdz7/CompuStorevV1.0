package com.fiuady.android.compustorevv10;

/**
 * Created by Diego Hernandez on 06/04/2017.
 */

/*
CREATE TABLE [products](
    [id] INTEGER PRIMARY KEY,
    [category_id] INTEGER REFERENCES categories([id]),
    [description] TEXT NOT NULL,
    [price] INTEGER NOT NULL,
    [qty] INTEGER NOT NULL);
 */

public class Product {

    //hkg
    private int Id;
    private int categoryId;
    private String description;
    private double price;
    private String Cat_description;
    private int qty;

    public Product(int id, int CatId, String descripcion, double precio, int cantidad)
    {
        this.Id=id;
        this.categoryId = CatId;
        this.description = descripcion;
        this.price = precio;
        this.qty = cantidad;
    }
    public  Product(int id, int CatId, String descripcion, double precio, int cantidad,String cat_description)
    {
        this.Id=id;
        this.categoryId = CatId;
        this.description = descripcion;
        this.price = precio;
        this.qty = cantidad;
        this.Cat_description=cat_description;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return Id;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }
    public String getCat_description() {
        return Cat_description;
    }

    public void setCat_description(String cat_description) {
        Cat_description = cat_description;
    }

}
