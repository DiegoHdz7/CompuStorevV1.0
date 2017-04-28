package com.fiuady.android.compustorevv10;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Manuel on 26/04/2017.
 */
public class Order_Assembly implements Parcelable {//to store the objects
    private int Id;
    private int assembly_Id;
    private String assembly_Description;
    private int quantity;

    public Order_Assembly(int id, int assembly_Id, String assembly_Description, int quantity) {
        Id = id;
        this.assembly_Id = assembly_Id;
        this.assembly_Description = assembly_Description;
        this.quantity = quantity;
    }
    private Order_Assembly(Parcel in){
        Id = in.readInt();
        assembly_Id = in.readInt();
        assembly_Description = in.readString();
        quantity = in.readInt();


    }
    public int describeContents(){
        return 0;
    }
    public void writeToParcel (Parcel out, int flags){
        out.writeInt(Id);
        out.writeInt(assembly_Id);
        out.writeString(assembly_Description);
        out.writeInt(quantity);


    }
    public static final Creator<Order_Assembly> CREATOR = new Creator<Order_Assembly>(){
        public Order_Assembly createFromParcel(Parcel in){
            return new Order_Assembly(in);
        }
        public Order_Assembly[] newArray(int size){
            return new Order_Assembly[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAssembly_Id() {
        return assembly_Id;
    }

    public void setAssembly_Id(int assembly_Id) {
        this.assembly_Id = assembly_Id;
    }

    public String getAssembly_Description() {
        return assembly_Description;
    }

    public void setAssembly_Description(String assembly_Description) {
        this.assembly_Description = assembly_Description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
