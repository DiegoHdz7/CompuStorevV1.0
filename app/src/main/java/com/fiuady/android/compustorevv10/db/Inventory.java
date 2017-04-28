package com.fiuady.android.compustorevv10.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.DetailInfo;
import com.fiuady.android.compustorevv10.MissingProductsActivity;
import com.fiuady.android.compustorevv10.Product;
import com.fiuady.android.compustorevv10.ProductoFaltante;
import com.fiuady.android.compustorevv10.SalesPerMounthActivity;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Inventory {
    private InventoryHelper inventoryHelper;
    private SQLiteDatabase db;

    private static ClientsFiltersDbName[] cfDb_Array = {ClientsFiltersDbName.LAST_NAME,ClientsFiltersDbName.FIRST_NAME, ClientsFiltersDbName.ADDRESS,
            ClientsFiltersDbName.PHONE,ClientsFiltersDbName.E_MAIL};


    public Inventory(Context contex){
        inventoryHelper = new InventoryHelper(contex);
        db = inventoryHelper.getWritableDatabase();
    }

    public void SetVirtualTableOfProducts()
    {
        //En esta función se extraen todas las cantidades actuales de los productos en stock
        //Se creará una nueva tabla en donde sólo importan los p_id, p_description, p_qty;

        String query = null;
        ArrayList<Product> listOrigProducts = new ArrayList<Product>();
        Cursor cursor ;

        query = "SELECT *\n" +
                "FROM products p;";
        cursor = db.rawQuery(query,null);

        //cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
        while (cursor.moveToNext()){
            listOrigProducts.add(new Product( cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.CAEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.PRICE))/100,
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.QUANTITY))
                    ));
        }
        cursor.close();

        query = "DROP TABLE IF EXISTS [simulated_products];";//Primero reiniciar la tabla temporal
        db.execSQL(query);

        query = null;

        //Crear la nueva tabla temporal de productos
        query = "CREATE TABLE [simulated_products](\n" +
                "[p_id] INTEGER PRIMARY KEY,\n" +
                "[p_categ_id] INTEGER,\n" +
                "[p_description] TEXT,\n" +
                "[p_price] INTEGER,\n" +
                "[p_qty] INTEGER\n" +
                ");";

        db.execSQL(query);

        //Agregar los productos actuales a la nueva tabla temporal
        for (Product product: listOrigProducts)
        {
            query = null;
            query = "INSERT INTO simulated_products(p_id, p_categ_id, p_description, p_price, p_qty) VALUES\n" +
                    "(" + String.valueOf(product.getId()) +", " + String.valueOf(product.getCategoryId()) +
                    ", 'nuevo producto'," + String.valueOf((int)product.getPrice()*100) + ", " +
                    String.valueOf(product.getQty()) +" );";

            db.execSQL(query);
        }
    }

    public void SetNeededProductsInSimulation(String critreria)
    {
        //Esta función tiene como meta el crear una tabla temporal needed_products,
        // en la cual se almacena los productos faltantes, sus cantidades faltantes
        // y las respectivas id de las órdenes

        //La tabla de los productos actuales en el stock se llama:
        //simulated_products

        //La tabla en donde se encuentran las órdenes que se van a simular:
        //simulated_orders_temporary

        String query = null;
        ArrayList<SimulatedOrder> listSimOrders =  SearchTemporaryOrdersByCriteria(critreria);
        ArrayList<Product> listProdPerOrder;
        Cursor cursor;

        query = "DROP TABLE IF EXISTS [prod_needed];";
        db.execSQL(query);

        query=null;


        query = "CREATE TABLE [prod_needed](" +
                "[ord_id] " +
                "INTEGER," +
                "[prod_id] INTEGER,"+
                "[prod_qty_needed] INTEGER"+
                ");";
        db.execSQL(query);

        for(SimulatedOrder simOrd : listSimOrders){
            int ordIdToGetHisProducts = simOrd.getId();

            listProdPerOrder = getAllProductsOfOrder(ordIdToGetHisProducts); //Tengo los productos de cada order
            //1.¿El producto de la orden se encuentra en la tabla "prod_needed"?
            //¿Sí? Obtener el valor más negativo de la cantidad de ese producto y a esa cantidad
            //restarle la cantidad de ese producto necesitado por el ensamble

            for (Product pOrd: listProdPerOrder) {
                int productOfSelectedOrder_id = pOrd.getId();   //Id del producto que se compara.
                int productOfSelectedOrder_qty = pOrd.getQty(); //Qty del producto que se compara.

                query = null;
                query = "SELECT *" +
                        " FROM prod_needed pn" +
                        " WHERE pn.prod_id = " + String.valueOf(productOfSelectedOrder_id) + ";";
                cursor = db.rawQuery(query, null);
                if (cursor.getCount() > 0) { //Verificar si la tabla no esta vacía
                    query = null;
                    //Verificar si el id del producto a buscar ya se encuentra en la tabla

                    query = "SELECT * " +
                            " FROM prod_needed pn" +
                            " WHERE pn.ord_id =" + String.valueOf(productOfSelectedOrder_id) + ";";
                    cursor = db.rawQuery(query, null);

                    if (cursor.getCount() > 0) {

                        query = "SELECT MIN(pn.prod_qty_needed), pn.ord_id" +
                                " FROM prod_needed pn" +
                                " WHERE pn.prod_id = " + String.valueOf(productOfSelectedOrder_id) + ";";
                        cursor = db.rawQuery(query, null);
                        cursor.moveToFirst(); //cursor.moveToNext(); //Probar con moveToFirst
                        int minQtyInProductsNeededcursor = cursor.getInt(0);
                        int idOfOrderOfMinQuantity = cursor.getInt(1);  //Si es igual al Id del que se está
                                                                        //analizando sumar el pn.prod_qty_needed

                        int newMinQty = minQtyInProductsNeededcursor - productOfSelectedOrder_qty;

                        query = null;

                        if (idOfOrderOfMinQuantity == ordIdToGetHisProducts) {
                         query = "UPDATE prod_needed SET prod_qty_needed = " + String.valueOf(newMinQty) + " WHERE prod_id = " + String.valueOf(productOfSelectedOrder_id)  + ";";
                            db.execSQL(query);
                        }else {

                            query = "INSERT INTO prod_needed (ord_id, prod_id, prod_qty_needed)" +
                                    " VALUES(" + String.valueOf(ordIdToGetHisProducts) + ", " + String.valueOf(productOfSelectedOrder_id) +
                                    ", " + String.valueOf(newMinQty) +
                                    ");";

                            db.execSQL(query);
                        }
                    }
                    else {  //Si no se encuentra en la tabla de prod_needed comparar los datos de la tabla del stock
                        //Hacer la resta en la tabla de los productos del stock

                        //Obtener el valor de la cantidad del producto que se busca
                        query = null;
                        query = "SELECT sp.p_qty" +
                                " FROM simulated_products sp" +
                                " WHERE sp.p_id = " + String.valueOf(productOfSelectedOrder_id)
                                + ";";

                        cursor = db.rawQuery(query,null);
                        cursor.moveToFirst();
                        int stockQty = cursor.getInt(0);
                        //Falta restarle el qty del producto de la orden.
                        int newStockQty = stockQty - productOfSelectedOrder_qty;

                        if (newStockQty < 0)
                        {
                            //Agregarlo de una vez a prod_needed

                            query = null;
                            query = "INSERT INTO prod_needed (ord_id, prod_id, prod_qty_needed)" +
                                    " VALUES(" + String.valueOf(ordIdToGetHisProducts) + ", " + String.valueOf(productOfSelectedOrder_id) +
                                    ", " + String.valueOf(newStockQty) +
                                    ");";
                            db.execSQL(query);

                            query = null; //Modificar también el stock
                            //UPDATE empleados SET ventas = 0 WHERE oficina = 12;
                            query = "UPDATE simulated_products SET p_qty = " + String.valueOf(newStockQty) +
                                    " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";" ;

                            db.execSQL(query);
                        }

                        else{
                            query = "UPDATE simulated_products SET p_qty = " + String.valueOf(newStockQty) +
                                    " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";" ;

                            db.execSQL(query);
                        }

                    }
                }

                else { //Si la tabla está vacía
                    //Obtener el valor de la cantidad del producto que se busca
                    query = null;
                    query = "SELECT sp.p_qty" +
                            " FROM simulated_products sp" +
                            " WHERE sp.p_id = " + String.valueOf(productOfSelectedOrder_id)
                            + ";";

                    cursor = db.rawQuery(query,null);
                    cursor.moveToFirst();
                    int stockQty = cursor.getInt(0);
                    //Falta restarle el qty del producto de la orden.
                    int newStockQty = stockQty - productOfSelectedOrder_qty;

                    if (newStockQty < 0)
                    {
                        //Agregarlo de una vez a prod_needed

                        query = null;
                        query = "INSERT INTO prod_needed (ord_id, prod_id, prod_qty_needed)" +
                                " VALUES(" + String.valueOf(ordIdToGetHisProducts) + ", " + String.valueOf(productOfSelectedOrder_id) +
                                ", " + String.valueOf(newStockQty) +
                                ");";
                        db.execSQL(query);

                        query = null; //Modificar también el stock
                        //UPDATE empleados SET ventas = 0 WHERE oficina = 12;
                        query = "UPDATE simulated_products SET p_qty = " + String.valueOf(newStockQty) +
                                " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";" ;

                        db.execSQL(query);
                    }

                    else{
                        query = "UPDATE simulated_products SET p_qty = " + String.valueOf(newStockQty) +
                                " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";" ;

                        db.execSQL(query);
                    }

                }
            }


        }

        //Sacar el query para determinar si la orden se encuentra en la tabla de product_needed
        //y verificar si su número de productos = o < que los productos con el id de la orden
        //en la tabla, para determinar el color del itemView (editar el canBeTaken)
        if(listSimOrders.isEmpty() != true ) {
            query = null;
            for (SimulatedOrder simulatedOrder : listSimOrders) {
                //Contar la variedad de productos que se tiene en determinada orden
                query = "SELECT COUNT(p.id)\n" +
                        "        FROM orders o \n" +
                        "        INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                        "        INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id)\n" +
                        "        INNER JOIN products p ON(ap.product_id = p.id)\n" +
                        "        WHERE o.id=" + String.valueOf(simulatedOrder.getId()) +
                        "        ORDER BY p.id;";

                cursor = db.rawQuery(query, null);
                cursor.moveToFirst();
                int numOfProductInOrder = cursor.getInt(0); //num de  variedad de productos usados en una orden

                query = "SELECT COUNT(pn.prod_id)" +
                        " FROM prod_needed pn" +
                        " WHERE pn.ord_id = " + String.valueOf(simulatedOrder.getId())
                        + ";";
                cursor = db.rawQuery(query, null);
                cursor.moveToFirst();
                int numOfNeededProductsOfOrder = cursor.getInt(0);

                int difBetweenNumOfProductQty = numOfProductInOrder - numOfNeededProductsOfOrder;
                if (difBetweenNumOfProductQty == 0) {   //La variedad de productos para la orden no están disponibles
                    //es decir, le faltan todos los productos
                    query = "UPDATE simulated_orders_temporary SET ord_can_be_taken = 0 WHERE ord_id = " +
                            String.valueOf(simulatedOrder.getId()) + ";";
                    db.execSQL(query);
                } else if (difBetweenNumOfProductQty > 0) {  //Hacen falta productos para poder Confirmar esta orden
                    //Pero no hacen falta todos los prductos
                    query = "UPDATE simulated_orders_temporary SET ord_can_be_taken = 1 WHERE ord_id = " +
                            String.valueOf(simulatedOrder.getId()) + ";";
                    db.execSQL(query);
                } else if (difBetweenNumOfProductQty == numOfProductInOrder) { //No hay productos faltantes
                    //es decir, puede Confirmarse la orden
                    query = "UPDATE simulated_orders_temporary SET ord_can_be_taken = 2 WHERE ord_id = " +
                            String.valueOf(simulatedOrder.getId()) + ";";
                    db.execSQL(query);
                }
            }
        }

    }


    private ArrayList<Product> getAllProductsOfOrder (int orderId)
    {
        //Obtener todos los productos y sus cantidades, de una cierta orden
        String query = null;
        ArrayList<Product> list = new ArrayList<Product>();
        Cursor cursor;

        query="SELECT p.id, p.category_id, p.description, p.price, p.qty\n" +
                "        FROM orders o \n" +
                "        INNER JOIN order_assemblies oa ON (o.id = oa.id)\n" +
                "        INNER JOIN assembly_products ap ON (oa.assembly_id = ap.id)\n" +
                "        INNER JOIN products p ON(ap.product_id = p.id)\n" +
                "        WHERE o.id=" + String.valueOf(orderId) +
                " ORDER BY p.id;";

        cursor = db.rawQuery(query,null);
//list.add(new Customers(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
        while (cursor.moveToNext()){
            list.add(new Product(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.CAEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.PRICE))/100,
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.QUANTITY))
            ));
        }
        cursor.close();
        return list;
    }

    public Product getASpecificProductById (int id)
    {
        String query = null;
        Cursor cursor;

        query = "SELECT * " +
                " FROM products p" +
                " WHERE p.id = " + id +";";
        cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        Product product = new Product(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.CAEGORY_ID)),
                cursor.getString(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.DESCRIPTION)),
                cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.PRICE))/100,
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.QUANTITY))
        );

        return product;
    }

    public ArrayList<SimProductsNeeded> GetInformationAboutNeededProducts(int orderId)
    {
        String query = null;
        ArrayList<SimProductsNeeded> list = new ArrayList<SimProductsNeeded>();
        Cursor cursor;

        query = "SELECT *" +
                " FROM prod_needed pn" +
                " WHERE pn.ord_id = " + String.valueOf(orderId) + ";";

        cursor = db.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            list.add( new SimProductsNeeded(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2))
            );
        }
        cursor.close();
        return list;
    }

    public  void SetVirtualTableToSimulate()
    {
        String query = null;
        ArrayList<SimulatedOrder> listOrigOrders = new ArrayList<SimulatedOrder>();
        Cursor cursor;


        //Se extraen todas las órdenes pendientes para simular:
        query = "SELECT o.id AS order_id, os.description AS order_status, c.last_name AS customerLN, c.first_name AS customerFN, \n" +
                "       substr(o.date,1,2) AS date_day, substr(o.date,4,2) AS date_month, substr(o.date,7,4)AS date_year,\n" +
                "       tNew.order_price AS order_price\n" +
                "FROM\n" +
                "        (\n" +

                "        SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                "        FROM\n" +
                "        (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty        ) AS tot_prodPrice\n" +
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
                "WHERE os.description = 'Pendiente'\n" +
                "ORDER BY c.last_name, c.first_name,o.id DESC";

        cursor=db.rawQuery(query,null);

        while (cursor.moveToNext())
        {
                        /*
                        //0     1       2     3     4    5     6     7
                        //id,status, LName,FName, day, month, year, price
                        //int id, String status, String lastName, String firstName, Date date, double price, int canBeTaken
                        */
            listOrigOrders.add(new SimulatedOrder(
                    cursor.getInt(0),       //Id
                    cursor.getString(1),    //Status
                    cursor.getString(2),    //LName
                    cursor.getString(3),    //FName
                    Date.valueOf(cursor.getString(6) + "-" + cursor.getString(5) + "-" + cursor.getString(4)), //Date
                    (cursor.getDouble(7)/100), //Price
                    0                           //canBeTaken
            ));
        }
        cursor.close();

        //Aquí se va a crear la nueva tabla temporal de todas las órdenes que se tienen actualmente
        //en el recycler view

        query = "DROP TABLE IF EXISTS [simulated_orders_temporary];";//Primero reiniciar la tabla temporal
        db.execSQL(query);

        query = " ";

        query = "CREATE TABLE [simulated_orders_temporary] (\n" + //Si es una TEMPORARY TABLE se tiene problemas
                "[ord_id] INTEGER PRIMARY KEY,\n" +
                "[ord_status] TEXT,\n" +
                "[cust_last_name] TEXT,\n" +
                "[cust_first_name] TEXT,\n" +
                "[ord_date] TEXT,\n" +
                "[ord_price] INTEGER,\n" +
                "[ord_can_be_taken] INTEGER\n" +
                ");";
        db.execSQL(query);

       for(SimulatedOrder simulatedO : listOrigOrders)
       {
           //Aquí se ejecutan los querys para agregar todos los valores de las órdenes a simular
           //a la nueva tabla temporal

           int tempId = simulatedO.getId();
           String tempStatus = simulatedO.getStatus();
           String tempLastName = simulatedO.getLastName();
           String tempFirstName = simulatedO.getFirstName();
           String tempDate = simulatedO.getDate().toString();
           int newPrice = (int)(simulatedO.getPrice()) *100;
           int newCanBeTaken = simulatedO.getCanBeTaken();

           query = " ";
           query ="INSERT INTO simulated_orders_temporary " +
                   "(ord_id, ord_status, cust_last_name,cust_first_name,ord_date, ord_price, ord_can_be_taken) VALUES (" +
                   String.valueOf(tempId) +  ", " + "'" + tempStatus + "'"   + ", " + "'" + tempLastName + "'" +", " +
                   "'" + tempFirstName + "'" + ", " + "'" + tempDate + "'" + ", " + String.valueOf(newPrice) + ", " + String.valueOf(newCanBeTaken) +
                   ");"         ;

           db.execSQL(query);
       }
    }

    public ArrayList<SimulatedOrder> SearchTemporaryOrdersByCriteria( String critreria) {
        //Debe de devolverse la misma lista de órdenes en total, sólo que aquí se deben de ordenar
        //dependiendo de lo que se seleccione en el spinner.
        //Es decir, que lo que se hizo en la función de SearchOrdersByCriteria debe de hacerse aquí.
        //Checar si es mejor modificar SearchOrdersByCriteria o hacer los cambios en esta función.

        String query = null;
        ArrayList<SimulatedOrder> list = new ArrayList<SimulatedOrder>();
        Cursor cursor;

        if (!critreria.isEmpty()) {
            switch (critreria) {
                case "Cliente":
                    query = "SELECT *" +
                            "FROM simulated_orders_temporary sot " +
                            "ORDER BY sot.cust_last_name, sot.cust_first_name, sot.ord_date;";

                    cursor = db.rawQuery(query, null);

                    while (cursor.moveToNext()) {
                        list.add(new SimulatedOrder(
                                cursor.getInt(0), //Id
                                cursor.getString(1), //Status
                                cursor.getString(2), //LastName
                                cursor.getString(3), //FirstName
                                Date.valueOf(cursor.getString(4)), //Date
                                (cursor.getDouble(5) / 100), //Price
                                cursor.getInt(6)            // CanBeTaken
                        ));
                    }
                        cursor.close();
                        return list;


                case "Fecha":
                    query = "SELECT *" +
                            "FROM simulated_orders_temporary sot " +
                            "ORDER BY sot.ord_date, sot.ord_price, sot.cust_last_name, sot.cust_first_name;";

                    cursor = db.rawQuery(query, null);

                    while (cursor.moveToNext()) {
                        list.add(new SimulatedOrder(
                                cursor.getInt(0), //Id
                                cursor.getString(1), //Status
                                cursor.getString(2), //LastName
                                cursor.getString(3), //FirstName
                                Date.valueOf(cursor.getString(4)), //Date
                                (cursor.getDouble(5) / 100), //Price
                                cursor.getInt(6)            // CanBeTaken
                        ));
                    }
                        cursor.close();
                        return list;


                case "Monto de Venta":
                    query = "SELECT * \n" +
                            "FROM simulated_orders_temporary sot\n" +
                            "ORDER BY sot.ord_price DESC, sot.ord_date, sot.cust_last_name, sot.cust_first_name;";

                    cursor = db.rawQuery(query, null);

                    while (cursor.moveToNext()) {
                        list.add(new SimulatedOrder(
                                cursor.getInt(0), //Id
                                cursor.getString(1), //Status
                                cursor.getString(2), //LastName
                                cursor.getString(3), //FirstName
                                Date.valueOf(cursor.getString(4)), //Date
                                (cursor.getDouble(5) / 100), //Price
                                cursor.getInt(6)            // CanBeTaken
                        ));
                    }
                        cursor.close();
                        return list;
                    default: list.clear(); return list;
            }

        }
        else { list.clear(); return list; }
    }


    public SimulatedOrder getSimulatedOrderById (int Id)
    {
        String query = null;
        Cursor cursor;

        query = "SELECT *" +
                " FROM simulated_orders_temporary sot" +
                " WHERE sot.ord_id = " + String.valueOf(Id) + ";";
        //simulated_orders_temporary
        //"(ord_id, ord_status, cust_last_name,cust_first_name,ord_date, ord_price, ord_can_be_taken) VALUES (" +

        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        SimulatedOrder simulatedOrder = new SimulatedOrder(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), Date.valueOf(cursor.getString(4)), cursor.getDouble(5)/100, cursor.getInt(6)
                );

        return simulatedOrder;

    }

    public void CancelOrderInVirtualTableContent(int orderId)
    {
        //En esta función se debe de cancelar la orden deseada en la tambla creada temporalmente.
        //Deben de sumarse las cantidades de los productos de esta orden a la tabla temporal
        //de productos actuales en el stock.
        String query = null;
        ArrayList<SimProductsNeeded> list = new ArrayList<SimProductsNeeded>();
        Cursor cursor;

        int pastValue = 0;
        int actualValue = 0;

        query = " SELECT * " +
                " FROM prod_needed pn " +
                " WHERE pn.ord_id = "+ String.valueOf(orderId) +
                " ORDER BY pn.prod_id;";
        cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {

            int oId = cursor.getInt(0);
            int pId = cursor.getInt(1);
            actualValue = pId;
            int pQty = cursor.getInt(2);
                query = " SELECT nTab.oId, nTab.pId, MIN(nTab.qty) " +
                " FROM" +
                        " (SELECT pn.ord_id AS oId, pn.prod_id AS pId, pn.prod_qty_needed AS qty " +
                " FROM prod_needed pn" +
                " WHERE (pn.ord_id = " + String.valueOf(oId) + " AND pn.prod_id = "+ String.valueOf(pId) +")) AS nTab" +
                ";";

            Cursor cursor1 = db.rawQuery(query, null);

            cursor1.moveToFirst();

            cursor1.close();
            query = " SELECT sp.p_qty" +
                    " FROM simulated_products sp" +
                    " WHERE sp.p_id =" + String.valueOf(pId) + ";";
            cursor1 = db.rawQuery(query, null);
            cursor1.moveToFirst();
            int productQtyInStock = cursor.getInt(0);
            cursor1.close();
            int newProductQtyInStock = productQtyInStock + ((-1) * pQty);
            query = " UPDATE simulated_products SET p_qty = " + String.valueOf(newProductQtyInStock) + " WHERE p_id = " + String.valueOf(pId) + ";";
            db.execSQL(query);

            if(actualValue != pastValue ) {
                //query para eliminar
                query = "DELETE FROM prod_needed WHERE(ord_id = " + String.valueOf(oId) + " AND  prod_id = " + String.valueOf(pId) + ");";
                db.execSQL(query); //(ord_id, prod_id, prod_qty_needed)
            }

            pastValue = actualValue;


        /*"CREATE TABLE [simulated_products](\n" +
                "[p_id] INTEGER PRIMARY KEY,\n" +
                "[p_categ_id] INTEGER,\n" +
                "[p_description] TEXT,\n" +
                "[p_price] INTEGER,\n" +
                "[p_qty] INTEGER\n" +
                ");";
                */



        }
        cursor.close();

        query = "UPDATE simulated_orders_temporary SET ord_status = 'Cancelado' WHERE ord_id = " + String.valueOf(orderId) + ";";
                /*"CREATE TABLE [simulated_orders_temporary] (\n" + //Si es una TEMPORARY TABLE se tiene problemas
                "[ord_id] INTEGER PRIMARY KEY,\n" +
                "[ord_status] TEXT,\n" +
                "[cust_last_name] TEXT,\n" +
                "[cust_first_name] TEXT,\n" +
                "[ord_date] TEXT,\n" +
                "[ord_price] INTEGER,\n" +
                "[ord_can_be_taken] INTEGER\n" +
                ");";*/
                db.execSQL(query);
    }

    public String orderStatus (int orderId)
    {
        String query = "SELECT sot.ord_status" +
                " FROM simulated_orders_temporary sot " +
                " WHERE sot.ord_id = " + String.valueOf(orderId) + ";";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public void ConfirmOrderInVirtualTableContent ( int orderId)
    {
        //En esta función se debe de confirmar la orden seleccionada.
        //Se debe de verificar si se tienen los productos necesarios para poder confirmar la orden.
    }

    public ArrayList<SimulatedOrder> SearchOrdersByCriteria(String criteria)
    {
        String query = null;
        ArrayList<SimulatedOrder> list = new ArrayList<SimulatedOrder>();
        Cursor cursor;


        if (!criteria.isEmpty())
        {
            switch (criteria)
            {
                case "Cliente":
                    query = "SELECT o.id AS order_id, os.description AS order_status, c.last_name AS customerLN, c.first_name AS customerFN, \n" +
                            "       substr(o.date,1,2) AS date_day, substr(o.date,4,2) AS date_month, substr(o.date,7,4)AS date_year,\n" +
                            "       tNew.order_price AS order_price\n" +
                            "FROM\n" +
                            "        (\n" +
                            "        --Obtener el precio de cada orden\n" +
                            "        SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                            "        FROM\n" +
                            "        (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty        ) AS tot_prodPrice\n" +
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
                            "WHERE os.description = 'Pendiente'\n" +
                            "ORDER BY c.last_name, c.first_name,o.id DESC";

                    cursor=db.rawQuery(query,null);

                    while (cursor.moveToNext())
                    {
                        /*
                        //0     1       2     3     4    5     6     7
                        //id,status, LName,FName, day, month, year, price
                        //int id, String status, String lastName, String firstName, Date date, double price, int canBeTaken
                        */
                        list.add(new SimulatedOrder(
                                cursor.getInt(0),       //Id
                                cursor.getString(1),    //Status
                                cursor.getString(2),    //LName
                                cursor.getString(3),    //FName
                                Date.valueOf(cursor.getString(6) + "-" + cursor.getString(5) + "-" + cursor.getString(4)), //Date
                                (cursor.getDouble(7)/100), //Price
                                0                           //canBeTaken
                                ));
                    }
                    cursor.close();
                    return list;

                case "Fecha":

                    query = "SELECT o.id AS order_id, os.description AS order_status, c.last_name AS customerLN, c.first_name AS customerFN, \n" +
                            "       substr(o.date,1,2) AS date_day, substr(o.date,4,2) AS date_month, substr(o.date,7,4)AS date_year,\n" +
                            "       tNew.order_price AS order_price\n" +
                            "FROM\n" +
                            "        (\n" +
                            "        --Obtener el precio de cada orden\n" +
                            "        SELECT tabPrices.id_order AS orderID, SUM(tabPrices.tot_prodPrice) AS order_price\n" +
                            "        FROM\n" +
                            "        (SELECT o.id AS id_order, oa.id AS id_assembly, oa.qty AS oa_qty,  ap.qty AS ap_qty, p.id AS id_product , p.price AS p_price, (p.price*ap.qty*oa.qty        ) AS tot_prodPrice\n" +
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
                            "WHERE os.description = 'Pendiente'\n" +
                            "ORDER BY date_year, date_month, date_day ASC";

                    cursor=db.rawQuery(query,null);

                    while (cursor.moveToNext())
                    {
                        list.add(new SimulatedOrder(
                                cursor.getInt(0),       //Id
                                cursor.getString(1),    //Status
                                cursor.getString(2),    //LName
                                cursor.getString(3),    //FName
                                Date.valueOf(cursor.getString(6) + "-" + cursor.getString(5) + "-" + cursor.getString(4)), //Date
                                (cursor.getDouble(7)/100), //Price
                                0                           //canBeTaken
                        ));
                    }
                    cursor.close();

                    //Implementar método para ordenar las fechas por java
                    return list;

                case "Monto de Venta":
                    query = "SELECT o.id AS order_id, os.description AS order_status, c.last_name AS customerLN, c.first_name AS customerFN, \n" +
                            "       substr(o.date,1,2) AS date_day, substr(o.date,4,2) AS date_month, substr(o.date,7,4)AS date_year,\n" +
                            "       tNew.order_price AS order_price\n" +
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
                            "WHERE os.description = 'Pendiente'\n" +
                            "ORDER BY tNew.order_price DESC";

                    cursor=db.rawQuery(query,null);

                    while (cursor.moveToNext())
                    {
                        String day = cursor.getString(5);
                        String month = cursor.getString(6);
                        String year = cursor.getString(7);
                        String newDate = year + "/" + month + "/" +day;
                        //0,    1,      2,  3,      4,     5,   6,      7
                        //id,status, LName,FName, price, day, month, year

                        list.add(new SimulatedOrder(
                                cursor.getInt(0),       //Id
                                cursor.getString(1),    //Status
                                cursor.getString(2),    //LName
                                cursor.getString(3),    //FName
                                Date.valueOf(cursor.getString(6) + "-" + cursor.getString(5) + "-" + cursor.getString(4)), //Date
                                (cursor.getDouble(7)/100), //Price
                                0                           //canBeTaken
                        ));
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

    public ArrayList<Customers> searchCustomersByFilter(int intFilter, String criteria)  //Búsqueda unitaria
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

    //public GetDetailInfo()

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

