package com.fiuady.android.compustorevv10;

import java.util.Date;

/**
 * Created by Diego Hernandez on 23/04/2017.
 */

public class DetailInfo {
    private int OrderId;
    private int AssemblyId;
    private int ClientId;
    private String FirstName;
    private String LastName;
    private String Address;
    private Date date;
    private String Status_Description;
    private double TotalPrice;

    public DetailInfo(int orderId, int assemblyId, int clientId, String firstName, String lastName, String address, Date date, String status_Description, double totalPrice) {
        this.OrderId = orderId;
       this.AssemblyId = assemblyId;
        this.ClientId = clientId;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Address = address;
        this.date = date;
        this.Status_Description = status_Description;
        this.TotalPrice = totalPrice;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getAssemblyId() {
        return AssemblyId;
    }

    public void setAssemblyId(int assemblyId) {
        AssemblyId = assemblyId;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus_Description() {
        return Status_Description;
    }

    public void setStatus_Description(String status_Description) {
        Status_Description = status_Description;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }
}
