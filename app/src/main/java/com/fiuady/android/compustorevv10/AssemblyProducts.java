package com.fiuady.android.compustorevv10;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Manuel on 11/04/2017.
 */
public class AssemblyProducts implements Parcelable{
    private int assemblyId;
    private int productId;
    private int quantity;
    private int categoryId;
    private String description;
    private int price;

    public AssemblyProducts(int assemblyId, int productId, int quantity, int categoryId, String description, int price) {
        this.assemblyId = assemblyId;
        this.productId = productId;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
    }
    private AssemblyProducts(Parcel in){
        assemblyId = in.readInt();
        productId = in.readInt();
        quantity = in.readInt();
        categoryId = in.readInt();
        description = in.readString();
        price = in.readInt();
    }
    public int describeContents(){
        return 0;
    }
    public void writeToParcel (Parcel out, int flags){
        out.writeInt(assemblyId);
        out.writeInt(productId);
        out.writeInt(quantity);
        out.writeInt(categoryId);
        out.writeString(description);
        out.writeInt(price);
    }
    public static final Creator<AssemblyProducts> CREATOR = new Creator<AssemblyProducts>(){
        public AssemblyProducts createFromParcel(Parcel in){
            return new AssemblyProducts(in);
        }
        public AssemblyProducts[] newArray(int size){
            return new AssemblyProducts[size];
        }
    };

    public int getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(int assemblyId) {
        this.assemblyId = assemblyId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
