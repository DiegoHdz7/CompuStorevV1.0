package com.fiuady.android.compustorevv10.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.fiuady.android.compustorevv10.MissingProductsActivity;
import com.fiuady.android.compustorevv10.ProductoFaltante;

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
                case 0: //Apellido
                    query = "SELECT * FROM customers c ORDER BY c." + cfDb_Array[0].toString() + ";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    Log.i("searchBy_lN_befWhile",cursor.toString());
                    while (cursor.moveToNext()) {
                        Log.i("if_lName = ", cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)).toLowerCase());
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

                case 1: //Nombre
                    query = "SELECT * FROM customers c ORDER BY c." + cfDb_Array[1].toString() + ";";

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

                case 2://Dirección
                    query = "SELECT * FROM customers c ORDER BY c." + cfDb_Array[0].toString() + ";";

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


                case 3: //Teléfono
                    query = "SELECT * FROM customers c ORDER BY c." + cfDb_Array[0].toString() + ";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1))!= null) {
                            if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)).contains(criteria)) {
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
                        else if  (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2))!= null)
                        {
                            boolean inList = false;
                            int count = 1;
                            while (count<=list.size() & inList!=false)
                            {
                                if (list.get(count-1).getId() == cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)))
                                {
                                    inList=true;
                                }
                            }
                            if (!inList)
                            {
                                if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)).contains(criteria)) {
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
                        }
                        else if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3))!= null){
                            boolean inList = false;
                            int count = 1;
                            while (count<=list.size() & inList!=false)
                            {
                                if (list.get(count-1).getId() == cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)))
                                {
                                    inList=true;
                                }
                            }
                            if (!inList)
                            {
                                if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)).contains(criteria)) {
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
                        }
                    }
                    cursor.close();
                    Log.i("searchedBy = " + cfDb_Array[3].toString(), query);

                    return list;

                case 4: //Correo
                    query = "SELECT * FROM customers c ORDER BY c." + cfDb_Array[0].toString() + ";";

                    //Query para devolver los datos
                    cursor = db.rawQuery(query,null);
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))!= null) {
                            if (cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL)).toLowerCase().contains(criteria.toLowerCase())) {
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

    public void setNewClient(String lastN, String firstN, String adress, String phone1, String phone2, String phone3, String eMail)
    {
        Cursor cursor = db.rawQuery("SELECT MAX(c.id) AS mS FROM customers c;",null);
        cursor.moveToFirst();
        int max = cursor.getInt(0) + 1;
        String ph1 = "NULL";
        String ph2 = "NULL";
        String ph3 = "NULL";
        String eM = "NULL";

        if (phone1 != "NULL") {ph1 = "'" + phone1 + "'";}
        if (phone2 != "NULL") {ph2 = "'" + phone2 + "'";}
        if (phone3 != "NULL") {ph3 = "'" + phone3 + "'";}
        if(eMail != "NULL") {eM = "'" + eMail + "'";}

        String query = "INSERT INTO customers (id, first_name, last_name, address, phone1, phone2, phone3, e_mail)" +
                " VALUES (" + String.valueOf(max) + ", '" + firstN + "', '" + lastN + "', '" + adress + "', "
                + ph1 + ", " + ph2 + ", " + ph3 + ", " + eM + ");";
        Log.i("SetNewClient = ",query);
        db.execSQL(query);
    }


    public List<Integer> Orders ()
    {
        ArrayList<Integer> ordenes = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("select id from order_assemblies group by id", null);
        while (cursor.moveToNext()) {
            ordenes.add(cursor.getInt(0));
        }


        cursor.close();

        return ordenes;

    }

    public int CantidadDeEnsamblesPorOrden(int AssemblyID, int orderID)
    {
        int valor;
        String args[] = {String.valueOf(orderID),String.valueOf(AssemblyID)};
        Cursor cursor = db.rawQuery("select qty from order_assemblies where id =?  and assembly_id =? ",args);
        //select qty from order_assemblies where id=0  and assembly_id=0
        cursor.moveToFirst();
        valor=cursor.getInt(0);
        cursor.close();

        return valor;

    }

    public List<Integer> AssembliesIDbyOrder(int orderID)
    {
        String args[] = {String.valueOf(orderID)};
        ArrayList<Integer> AssembliesId = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("select assembly_id from order_assemblies where id =?", args);
        while (cursor.moveToNext()) {
            AssembliesId.add(cursor.getInt(0));

            //select assembly_id from order_assemblies where id=0
        }


        cursor.close();

        return AssembliesId;

    }

    public List<ProductoFaltante> GetMissingProducts (int OrderId, List<Integer>AssembliesIDs)
    {
     ArrayList<ProductoFaltante> MissingProducts = new ArrayList<ProductoFaltante>();
        //select *, qtyP-qtyAP*2 as residuo from CheckIfCanAssembly where idAP =0
        int NumeroDeEnsambles = AssembliesIDs.size()-1;
        int i=0;
        int NumEnsamblesPorOrden;

        for (i=0;i<=NumeroDeEnsambles;i++ )
        {
            NumEnsamblesPorOrden = CantidadDeEnsamblesPorOrden(AssembliesIDs.get(i),OrderId);
            String args[]={String.valueOf(NumEnsamblesPorOrden),String.valueOf(AssembliesIDs.get(i))};
            Cursor cursor = db.rawQuery("select *, qtyP-qtyAP*? as residuo from CheckIfCanAssembly where idAP =? ",args);
            while (cursor.moveToNext()) {
                MissingProducts.add(new ProductoFaltante(AssembliesIDs.get(i),cursor.getInt(1),cursor.getInt(5)));


            }


            cursor.close();


        }


        return MissingProducts;
    }



    public void CreateTemporaryTable()
    {
        db.rawQuery("drop table CheckIfCanAssembly",null);
        db.rawQuery("CREATE TEMPORARY TABLE CheckIfCanAssembly AS\n" +
                "WITH CheckIf AS (\n" +
                "select ap.id as idAP,product_id,\n" +
                " category_id,\n" +
                " ap.qty as qtyAP,\n" +
                " p.qty as qtyP \n" +
                " from assembly_products ap inner join products p  on p.id = ap.product_id \n" +
                "   \n" +
                ")\n" +
                "SELECT * FROM CheckIf;",null);
    }

    public void GetMissingProducts(List<Integer> ordenes)
    {


    }

}

