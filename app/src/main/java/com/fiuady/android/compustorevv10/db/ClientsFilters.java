package com.fiuady.android.compustorevv10.db;


public enum ClientsFilters {
    FIRST_NAME("Nombre",1),
    LAST_NAME("Apellido",0),
    ADDRESS("Dirección",2),
    PHONE("Teléfono",3),
    E_MAIL("Correo",4);

    private String stringValue;
    private int intValue;

    ClientsFilters(String toString, int value)
    {
        stringValue = toString;
        intValue=value;
    }

    @Override
    public String toString() {
        return stringValue;
    }


}
