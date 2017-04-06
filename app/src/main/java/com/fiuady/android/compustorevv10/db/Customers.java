package com.fiuady.android.compustorevv10.db;

/**
 * Created by Aarón Madera on 05/04/2017.
 */

public class Customers {
    private int id; //Can't be null
    private String firstName; //Cannot be null
    private String lastName; //Cannot be null
    private String address; //Cannot be null
    private String phone1; //Can be null
    private String phone2; //Can be null
    private String phone3; //Can be null
    private String eMail; //Can be null

    public Customers(int id, String firstName, String lastName, String address, String phone1, String phone2, String phone3, String eMail) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.eMail = eMail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
