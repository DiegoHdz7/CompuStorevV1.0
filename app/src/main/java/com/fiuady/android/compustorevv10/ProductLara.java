package com.fiuady.android.compustorevv10;

/**
 * Created by Manuel on 28/04/2017.
 */

public class ProductLara {private int id;
    private int category_id;
    private String description;
    private int price;
    private int qty;

    public ProductLara(int id, int category_id, String description, int price, int qty) {
        this.id = id;
        this.category_id = category_id;
        this.description = description;
        this.price = price;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
