package com.fiuady.android.compustorevv10.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.DetailInfo;
import com.fiuady.android.compustorevv10.MissingProductsActivity;
import com.fiuady.android.compustorevv10.ProductoFaltante;
import com.fiuady.android.compustorevv10.SalesPerMounthActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<SimulatedOrder> SearchOrdersByCriteria(String criteria)
    {
        String query = null;
        ArrayList<SimulatedOrder> list = new ArrayList<SimulatedOrder>();
        Cursor cursor;

        if (!criteria.isEmpty())
        {
            switch (criteria)
            {
                case "Cliente":
                    query = "SELECT o.id AS order_id, os.description AS order_status, c.last_name AS customerLN, c.first_name AS customerFN, o.date AS order_date,\n" +
                            "tNew.order_price AS order_price\n" +
                            "FROM\n" +
                            "        (\n" +
                            "        SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                            "        FROM\n" +
                            "        (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty) AS tot_prodPrice\n" +
                            "        FROM orders o \n" +
                            "        INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                            "        INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id)\n" +
                            "        INNER JOIN products p ON(ap.product_id = p.id)\n" +
                            "        ORDER BY o.id, p.id) AS tabPrices \n" +
                            "        GROUP BY tabPrices.id_order\n" +
                            "        HAVING SUM(tabPrices.tot_prodPrice)\n" +
                            "        ) AS tNew\n" +
                            "INNER JOIN orders o ON (o.id = tNew.orderID)\n" +
                            "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                            "INNER JOIN order_status os ON (o.status_id = os.id)\n" +
                            "ORDER BY c.last_name, c.first_name,os.id DESC";

                    cursor=db.rawQuery(query,null);

                    while (cursor.moveToNext())
                    {
                     list.add(new SimulatedOrder(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                             Date.valueOf(cursor.getString(4)), (cursor.getDouble(5)/100)));
                    }
                    cursor.close();
                    return list;

                case "Fecha":

                    query = "SELECT o.id AS order_id, os.description AS order_status, c.last_name AS customerLN, c.first_name AS customerFN, o.date AS order_date,\n" +
                            "tNew.order_price AS order_price\n" +
                            "FROM\n" +
                            "        (\n" +
                            "        SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                            "        FROM\n" +
                            "        (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty) AS tot_prodPrice\n" +
                            "        FROM orders o \n" +
                            "        INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                            "        INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id)\n" +
                            "        INNER JOIN products p ON(ap.product_id = p.id)\n" +
                            "        ORDER BY o.id, p.id) AS tabPrices \n" +
                            "        GROUP BY tabPrices.id_order\n" +
                            "        HAVING SUM(tabPrices.tot_prodPrice)\n" +
                            "        ) AS tNew\n" +
                            "INNER JOIN orders o ON (o.id = tNew.orderID)\n" +
                            "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                            "INNER JOIN order_status os ON (o.status_id = os.id)\n" +
                            "ORDER BY o.date, c.last_name, c.first_name DESC";

                    cursor=db.rawQuery(query,null);

                    while (cursor.moveToNext())
                    {
                        list.add(new SimulatedOrder(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                                Date.valueOf(cursor.getString(4)), (cursor.getDouble(5)/100)));
                    }
                    cursor.close();
                    return list;

                case "Monto de Venta":
                    query = "SELECT o.id AS order_id, os.description AS order_status, c.last_name AS customerLN, c.first_name AS customerFN, o.date AS order_date,\n" +
                            "tNew.order_price AS order_price\n" +
                            "FROM\n" +
                            "        (\n" +
                            "        SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                            "        FROM\n" +
                            "        (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty) AS tot_prodPrice\n" +
                            "        FROM orders o \n" +
                            "        INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                            "        INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id)\n" +
                            "        INNER JOIN products p ON(ap.product_id = p.id)\n" +
                            "        ORDER BY o.id, p.id) AS tabPrices \n" +
                            "        GROUP BY tabPrices.id_order\n" +
                            "        HAVING SUM(tabPrices.tot_prodPrice)\n" +
                            "        ) AS tNew\n" +
                            "INNER JOIN orders o ON (o.id = tNew.orderID)\n" +
                            "INNER JOIN customers c ON (o.customer_id = c.id)\n" +
                            "INNER JOIN order_status os ON (o.status_id = os.id)\n" +
                            "ORDER BY tNew.order_price DESC";

                    cursor=db.rawQuery(query,null);

                    while (cursor.moveToNext())
                    {
                        list.add(new SimulatedOrder(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                                Date.valueOf(cursor.getString(4)), (cursor.getDouble(5)/100)));
                    }
                    cursor.close();
                    return list;

                default:
                    list.clear();
                    return list;
            }
        }
        else
        {
            list.clear();
            return list;
        }
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

    public List<Integer> AssembliesIDbyOrder(int orderID) // Ensambles que requiere la orden
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

    /*public List<ProductoFaltante> GetMissingProducts (int OrderId, List<Integer>AssembliesIDs, List<Integer> OrdenesList) {
        ArrayList<ProductoFaltante> MissingProducts = new ArrayList<ProductoFaltante>();
        //int NumeroDeEnsambles = AssembliesIDs.size() - 1;
        int i = 0;
        int multiplicador;


       // for (int x=0; x<=OrdenesList.size()-1; x++) {
         //   int orden = OrdenesList.get(x);


            int NumeroDeEnsambles = AssembliesIDbyOrder(OrderId).size() - 1;

            for (i = 0; i <= NumeroDeEnsambles; i++) {


                ArrayList<Integer> productsID = new ArrayList<Integer>();
                multiplicador = CantidadDeEnsamblesPorOrden(AssembliesIDs.get(i), OrderId);
                String args[] = {String.valueOf(multiplicador), String.valueOf(AssembliesIDs.get(i))};

                Cursor cursor = db.rawQuery("select *, ATable.qtyP-Atable.qtyAP*? as residuo from  (select ap.id as idAP,product_id,\n" +
                        " category_id,\n" +
                        " ap.qty as qtyAP,\n" +
                        " p.qty as qtyP \n" +
                        " from assembly_products ap inner join products p  on p.id = ap.product_id) as ATable where  ATable.idAP =?", args);
                while (cursor.moveToNext()) {

                    if(cursor.getInt(5)<0)
                    {
                        MissingProducts.add(new ProductoFaltante(AssembliesIDs.get(i), cursor.getInt(1), cursor.getInt(5)*-1));

                    }

                    else { MissingProducts.add(new ProductoFaltante(AssembliesIDs.get(i), cursor.getInt(1), 0));}


                    productsID.add(cursor.getInt(1));


                }

                cursor.close();

              /*  for (int id: productsID) {

                    String qty = String.valueOf(cursor.getInt(5));
                    String args2[] = {String.valueOf(cursor.getInt(1))};
                    ContentValues valores = new ContentValues();
                    valores.put("qty",qty);
                    db.update("products", valores, "id = ?",args2 );

                }*/




            //}


       // }



     /*   return MissingProducts;
    }*/



    public void CreateTemporaryTable()
    {
        //db.rawQuery("drop table CheckIfCanAssembly",null);
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

    public List<Integer> GetMissingProductsByAssemblyID (int multiplicador, int AssemblyProductId)
    {
        ArrayList<Integer> faltantes = new ArrayList<Integer>();
        String args [] = {String.valueOf(multiplicador),String.valueOf(AssemblyProductId)};

        Cursor cursor= db.rawQuery("select *, ATable.qtyP-Atable.qtyAP*? as residuo from  (select ap.id as idAP,product_id,\n" +
                " category_id,\n" +
                " ap.qty as qtyAP,\n" +
                " p.qty as qtyP \n" +
                " from assembly_products ap inner join products p  on p.id = ap.product_id) as ATable where  ATable.idAP =?",args);
        while (cursor.moveToNext()) {
            faltantes.add(cursor.getInt(5));


        }


        cursor.close();
        return faltantes;
    }

    public List<ProductoFaltante> GetMissingProducts ()
    {
        ArrayList<ProductoFaltante> faltantes = new ArrayList<ProductoFaltante>();
       Cursor cursor = db.rawQuery("select OrderId,product_id, assembly_id,qtytotal,qty as Productqty, qty-qtytotal as faltante\n" +
                "from  (select oa.id as OrderId,product_id, assembly_id, sum ( ap.qty*oa.qty) as qtytotal from assembly_products ap inner join order_assemblies oa on ap.id=oa.assembly_id \n" +
                "--where ap.product_id=8\n" +
                "group by product_id having sum ( ap.qty*oa.qty))\n" +
                "as ATable  inner join products p on ATable.product_id = p.id",null);

        while (cursor.moveToNext()) {

            if(cursor.getInt(5)<0)
            {
                faltantes.add(new ProductoFaltante(cursor.getInt(2),cursor.getInt(1),cursor.getInt(5)*-1));

            }

            else { faltantes.add(new ProductoFaltante(cursor.getInt(2),cursor.getInt(1),0));}



        }


        cursor.close();

        return faltantes;

    }

    //Si meses entre enero y noviembre
    public double GetTotalSalesPerMounth (int positionList, String año)
    {
        double precioTotal;

        ArrayList<String> meses = new ArrayList<String>();

        for (int i = 1 ; i<=12; i++ )
        {
            if(i<10){meses.add("0"+String.valueOf(i));}
            else meses.add(String.valueOf(i));
        }


        String fstMounth,scdMounth;


       Cursor cursor = db.rawQuery("select sum(order_price) from(\n" +
                "SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                " FROM\n" +
                " (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty) AS tot_prodPrice\n" +
                " FROM orders o \n" +
                " INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                " INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id)\n" +
                " INNER JOIN products p ON(ap.product_id = p.id) where o.date LIKE '___"+meses.get(positionList)+"-"+año+"'\n" +
                " ORDER BY o.id, p.id) AS tabPrices \n" +
                " GROUP BY tabPrices.id_order\n" +
                " HAVING SUM(tabPrices.tot_prodPrice)) as TotalTabPrices;", null);

     /*Cursor cursor = db.rawQuery("select * from (select o.id as OrderId, AssemblyId,c.id as Cliente,c.first_name as FirstName,c.last_name as LastName, c.address as Address,status_id, date,description , CostoTotal*qty as RealCostoTotal from (select ap.id as AssemblyId,p.id as ProductId, sum(ap.qty * p.price) as CostoTotal\n" +
              "from assembly_products ap inner join products p on ap.product_id = p.id\n" +
              "group by AssemblyId) as TablaCosto inner join order_assemblies oa  on TablaCosto.AssemblyId = oa.assembly_id\n" +
              "inner join orders o on oa.id = o.id\n" +
              "inner join customers c on o.customer_id = c.id\n" +
              "inner join order_status os on os.id=o.status_id where status_id = 2 or status_id=4) AS GralTable where GralTable.date like '___"+meses.get(positionList)+"-"+año+"%' ", null);
            */
        cursor.moveToFirst();

        if (cursor.isNull(0)) {
            cursor.close();
            return 0;

        } else {
            precioTotal = cursor.getDouble(0)/100;
            cursor.close();
            return precioTotal;
        }





    }








    public boolean comprobacion(int positionList)
    {
        String fstMounth,scdMounth;
        fstMounth= String.valueOf(positionList +1);
        scdMounth =String.valueOf(positionList +2);

        String args[] = {fstMounth};

        Cursor cursor = db.rawQuery("select sum(order_price) from (\n" +
                "SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                " FROM\n" +
                " (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty) AS tot_prodPrice\n" +
                " FROM orders o \n" +
                " INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                " INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id)\n" +
                " INNER JOIN products p ON(ap.product_id = p.id) where o.date >= '01-"+fstMounth+"-2016' \n" +" and o.date < '01-"+scdMounth+"-2016'"+
                " ORDER BY o.id, p.id) AS tabPrices \n" +
                " GROUP BY tabPrices.id_order\n" +
                " HAVING SUM(tabPrices.tot_prodPrice)) as TotalTabPrices;",null);
        cursor.moveToFirst();

        if (cursor.isNull(0))
        {
            cursor.close();
            return false;
        }

        else
        {
            cursor.close();
            return true;}

    }


    public List<DetailInfo> GetDetailInfo (int mes, String año)
    {
        ArrayList<DetailInfo> dInfo = new ArrayList<DetailInfo>();


        ArrayList<String> meses = new ArrayList<String>();

        for (int i = 1 ; i<=12; i++ )
        {
            if(i<10){meses.add("0"+String.valueOf(i));}
            else meses.add(String.valueOf(i));
        }


     /*  Cursor cursor = db.rawQuery("select * from (select o.id as OrderId, AssemblyId,c.id as Cliente,c.first_name as FirstName,c.last_name as LastName, c.address as Address,status_id, date,description , CostoTotal*qty as RealCostoTotal from (select ap.id as AssemblyId,p.id as ProductId, sum(ap.qty * p.price) as CostoTotal\n" +
                "from assembly_products ap inner join products p on ap.product_id = p.id\n" +
                "group by AssemblyId) as TablaCosto inner join order_assemblies oa  on TablaCosto.AssemblyId = oa.assembly_id\n" +
                "inner join orders o on oa.id = o.id\n" +
                "inner join customers c on o.customer_id = c.id\n" +
                "inner join order_status os on os.id=o.status_id where status_id = 2 or status_id=4) AS GralTable where GralTable.date like '___"+meses.get(mes)+"-"+año+"%' ",null);
*/

     Cursor cursor = db.rawQuery("select * from (select o.id as OrderId, AssemblyId,c.id as Cliente,c.first_name as FirstName,c.last_name as LastName, c.address as Address,status_id, date,description , CostoTotal*qty as RealCostoTotal from (select ap.id as AssemblyId,p.id as ProductId, sum(ap.qty * p.price) as CostoTotal\n" +
             "from assembly_products ap inner join products p on ap.product_id = p.id\n" +
             "group by AssemblyId) as TablaCosto inner join order_assemblies oa  on TablaCosto.AssemblyId = oa.assembly_id\n" +
             "inner join orders o on oa.id = o.id\n" +
             "inner join customers c on o.customer_id = c.id\n" +
             "inner join order_status os on os.id=o.status_id where status_id = 2 or status_id=3 or status_id=4) AS GralTable where GralTable.date like '___"+meses.get(mes)+"-"+año+"%' ",null);




            while (cursor.moveToNext()) {
                dInfo.add(new DetailInfo(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        Date.valueOf(cursor.getString(7)),
                        cursor.getString(6),
                        cursor.getDouble(9) / 100));


            }

            cursor.close();



            return dInfo;



    }

    public List<DetailInfo> GetOrderInfobyMounth(int mes,String año)
    {
        ArrayList<DetailInfo> dInfo = new ArrayList<DetailInfo>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();

        ArrayList<String> meses = new ArrayList<String>();

        for (int i = 1 ; i<=12; i++ )
        {
            if(i<10){meses.add("0"+String.valueOf(i));}
            else meses.add(String.valueOf(i));
        }

        Cursor cursor = db.rawQuery("select *,sum(RealCostoTotal) from (select o.id as OrderId, AssemblyId,c.id as Cliente,c.first_name as FirstName,c.last_name as LastName, c.address as Address,status_id, date,description , CostoTotal*qty as RealCostoTotal from (select ap.id as AssemblyId,p.id as ProductId, sum(ap.qty * p.price) as CostoTotal\n" +
                "from assembly_products ap inner join products p on ap.product_id = p.id\n" +
                "group by AssemblyId) as TablaCosto inner join order_assemblies oa  on TablaCosto.AssemblyId = oa.assembly_id\n" +
                "inner join orders o on oa.id = o.id\n" +
                "inner join customers c on o.customer_id = c.id\n" +
                "inner join order_status os on os.id=o.status_id where status_id = 2 or status_id=3 or status_id=4) AS GralTable where GralTable.date like '___"+meses.get(mes)+"-"+año+"%' \n" +
                "group by OrderId",null);



        while (cursor.moveToNext()) {
            String aux = cursor.getString(7);
            String aux1 = cursor.getString(7);

            try {date=dateFormat.parse(cursor.getString(7)); }
            catch (ParseException e) {e.printStackTrace();}


            //date=dateFormat.parse(cursor.getString(7));

            dInfo.add(new DetailInfo(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                          date,//Date.valueOf(cursor.getString(7)),
                    cursor.getString(6),
                    cursor.getDouble(9) / 100));


        }

        cursor.close();



        return dInfo;

    }







}

