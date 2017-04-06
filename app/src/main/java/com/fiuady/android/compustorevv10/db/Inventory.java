package com.fiuady.android.compustorevv10.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public final class Inventory {
    private InventoryHelper inventoryHelper;
    private SQLiteDatabase db;

    private static ClientsFiltersDbName[] cfDb_Array = {ClientsFiltersDbName.LAST_NAME,ClientsFiltersDbName.FIRST_NAME, ClientsFiltersDbName.ADDRESS,
            ClientsFiltersDbName.PHONE,ClientsFiltersDbName.E_MAIL};


    public Inventory(Context contex){
        inventoryHelper = new InventoryHelper(contex);
        db = inventoryHelper.getWritableDatabase();
    }

    public List<Customers> searchCustomersByFilter(int intFilter, String criteria)  //Búsqueda unitaria
    {
        String query = null;
        ArrayList<Customers> list = new ArrayList<Customers>();
        Cursor cursor;

        if(criteria.isEmpty()){
            query = "SELECT * " +
                    "FROM customers c" +
                    " ORDER BY c." + cfDb_Array[0].toString() + ";";
            //Query para devolver los datos
            cursor = db.rawQuery(query,null);
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)).toLowerCase().contains(criteria.toLowerCase())
                        | criteria.toLowerCase().contains(cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)).toLowerCase())) {
                    list.add(new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
                            cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
                            cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)),
                            cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)),
                            cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)),
                            cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)),
                            cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)),
                            cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))
                    ));
                }
            }
            cursor.close();
            return list;
        }

        else {
            switch (intFilter) {
                case 0:
                    query = "SELECT * FROM customers c WHERE c."+ cfDb_Array[0].toString() + " = '" + criteria +"' ORDER BY c." + cfDb_Array[0].toString() + ";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)).toLowerCase().contains(criteria.toLowerCase())
                                | criteria.toLowerCase().contains(cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)).toLowerCase())) {
                            list.add(new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))
                            ));
                        }
                    }
                    cursor.close();
                    Log.i("searchedBy = " + cfDb_Array[0].toString(), query);
                    return list;

                case 1:
                    query = "SELECT * " +
                            "FROM customers c "+
                            "WHERE c."+ cfDb_Array[1].toString() + " = " + "'" + criteria +"'" +
                            " ORDER BY c." + cfDb_Array[1].toString() + ";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    Log.i("cursor_fName",cursor.toString());
                    while (cursor.moveToNext()) {
                        Log.i("if_fName = ", cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)).toLowerCase());
                        if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)).toLowerCase().contains(criteria.toLowerCase())
                                | criteria.toLowerCase().contains(cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)).toLowerCase())
                                )
                        {
                            list.add(new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))
                            ));
                        }
                    }
                    cursor.close();
                    Log.i("searchedBy = " + cfDb_Array[1].toString(), query + "Size: " + String.valueOf(list.size()));
                    return list;

                case 2:
                    query = "SELECT * " +
                            "FROM customers c "+
                            "WHERE c."+ cfDb_Array[2].toString() + " = " + "'" + criteria +"'" +
                            " ORDER BY c." + cfDb_Array[0].toString() + ";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)).toLowerCase().contains(criteria.toLowerCase())
                                ) {
                            list.add(new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))
                            ));
                        }
                    }
                    cursor.close();
                    Log.i("searchedBy = " + cfDb_Array[2].toString(), query);

                    return list;


                case 3:
                    query = "SELECT * " +
                            "FROM customers c "+
                            "WHERE c."+cfDb_Array[3].toString() +"1 OR c." +
                            cfDb_Array[3].toString() +"2 OR c."+
                            cfDb_Array[3].toString() +"3 "+
                            "= " + "'" + "'" + criteria +"'" +"'" +
                            " ORDER BY c." + cfDb_Array[0].toString() +";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)).toLowerCase().contains(criteria.toLowerCase())
                                | cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)).toLowerCase().contains(criteria.toLowerCase())
                                | cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)).toLowerCase().contains(criteria.toLowerCase())
                                ) {
                            list.add(new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))
                            ));
                        }
                    }
                    cursor.close();
                    Log.i("searchedBy = " + cfDb_Array[3].toString(), query);

                    return list;

                case 4:
                    query = "SELECT * " +
                            "FROM customers c "+
                            "WHERE c."+cfDb_Array[4].toString() + " = " + "'" + criteria +"'" +
                            " ORDER BY c." + cfDb_Array[0].toString() + ";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL)).toLowerCase().contains(criteria.toLowerCase())
                                | criteria.toLowerCase().contains(cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL)).toLowerCase())) {
                            list.add(new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)),
                                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))
                            ));
                        }
                    }
                    cursor.close();

                    Log.i("searchedBy = " + cfDb_Array[4].toString(), query);
                    return list;


                default:
                    throw new IllegalArgumentException("No se reconoce el valor del int");

            }
        }


        //query = null;
        //Log.i("searchFinished" + String.valueOf(list.size()),query);
        //return list;
    }

}
