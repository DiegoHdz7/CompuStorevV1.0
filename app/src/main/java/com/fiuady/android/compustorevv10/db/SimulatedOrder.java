package com.fiuady.android.compustorevv10.db;

import java.util.Date;

/**
 * Created by Aarón Madera on 22/04/2017.
 */

public class SimulatedOrder {
    private int id;
    private String status;
    private String lastName;
    private String firstName;
    private Date date;
    private double price;

    public SimulatedOrder(int id, String status, String lastName, String firstName, Date date, double price) {
        this.id = id;
        this.status = status;
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
        this.price = price; //
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
