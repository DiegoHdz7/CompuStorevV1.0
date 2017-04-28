package com.fiuady.android.compustorevv10;

/**
 * Created by Manuel on 19/04/2017.
 */
public class Customer {
    private int Id;
    private String First_Name;
    private String Last_Name;
    private String Address;
    private String Phone1;
    private String Phone2;
    private String Phone3;
    private String E_mail;

    public Customer(int id, String first_Name, String last_Name, String address, String phone1, String phone2, String phone3, String e_mail) {
        Id = id;
        First_Name = first_Name;
        Last_Name = last_Name;
        Address = address;
        Phone1 = phone1;
        Phone2 = phone2;
        Phone3 = phone3;
        E_mail = e_mail;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    public String getPhone3() {
        return Phone3;
    }

    public void setPhone3(String phone3) {
        Phone3 = phone3;
    }

    public String getE_mail() {
        return E_mail;
    }

    public void setE_mail(String e_mail) {
        E_mail = e_mail;
    }
}
