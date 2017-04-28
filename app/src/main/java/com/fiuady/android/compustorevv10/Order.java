package com.fiuady.android.compustorevv10;

/**
 * Created by Manuel on 23/04/2017.
 */
public class Order {
    private int Id;
    private int Status_Id;
    private String Status_Description;
    private int Costumer_Id;
    private String Costumer_FirstName;
    private String Costumer_LastName;
    private String Date;
    private String Change_Log;

    public Order(int id, int status_Id, String status_Description, int costumer_Id, String costumer_FirstName, String costumer_LastName, String date, String change_Log) {
        Id = id;
        Status_Id = status_Id;
        Status_Description = status_Description;
        Costumer_Id = costumer_Id;
        Costumer_FirstName = costumer_FirstName;
        Costumer_LastName = costumer_LastName;
        Date = date;
        Change_Log = change_Log;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStatus_Id() {
        return Status_Id;
    }

    public void setStatus_Id(int status_Id) {
        Status_Id = status_Id;
    }

    public String getStatus_Description() {
        return Status_Description;
    }

    public void setStatus_Description(String status_Description) {
        Status_Description = status_Description;
    }

    public int getCostumer_Id() {
        return Costumer_Id;
    }

    public void setCostumer_Id(int costumer_Id) {
        Costumer_Id = costumer_Id;
    }

    public String getCostumer_FirstName() {
        return Costumer_FirstName;
    }

    public void setCostumer_FirstName(String costumer_FirstName) {
        Costumer_FirstName = costumer_FirstName;
    }

    public String getCostumer_LastName() {
        return Costumer_LastName;
    }

    public void setCostumer_LastName(String costumer_LastName) {
        Costumer_LastName = costumer_LastName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getChange_Log() {
        return Change_Log;
    }

    public void setChange_Log(String change_Log) {
        Change_Log = change_Log;
    }
}
