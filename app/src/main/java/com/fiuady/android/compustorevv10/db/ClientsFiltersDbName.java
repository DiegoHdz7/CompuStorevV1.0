package com.fiuady.android.compustorevv10.db;


public enum ClientsFiltersDbName {

    FIRST_NAME("first_name",1),
    LAST_NAME("last_name",0),
    ADDRESS("adress",2),
    PHONE("phone",3),
    E_MAIL("e_mail",4);

    private String stringValue;
    private int intValue;

    private ClientsFiltersDbName(String toString, int value)
    {
        stringValue = toString;
        intValue=value;
    }

    @Override
    public String toString() {
        return stringValue;
    }


}