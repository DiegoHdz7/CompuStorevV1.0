package com.fiuady.android.compustorevv10.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.Assembly;
import com.fiuady.android.compustorevv10.AssemblyProducts;
import com.fiuady.android.compustorevv10.Customer;
import com.fiuady.android.compustorevv10.DetailInfo;
import com.fiuady.android.compustorevv10.MissingProductsActivity;
import com.fiuady.android.compustorevv10.Order;
import com.fiuady.android.compustorevv10.Order_Assembly;
import com.fiuady.android.compustorevv10.Order_Status;
import com.fiuady.android.compustorevv10.Product;
import com.fiuady.android.compustorevv10.ProductLara;
import com.fiuady.android.compustorevv10.Product_Categories;
import com.fiuady.android.compustorevv10.ProductoFaltante;
import com.fiuady.android.compustorevv10.Productos.Product_Activity;
import com.fiuady.android.compustorevv10.Productos.Proudct_Category;
import com.fiuady.android.compustorevv10.SalesPerMounthActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//Manuel Lara












//End Manuel Lara
public final class Inventory {
    private InventoryHelper inventoryHelper;
    private SQLiteDatabase db;
    private List<Product_Categories>categories;
    private List<Assembly>assemblies;//ManuelLara

    private static ClientsFiltersDbName[] cfDb_Array = {ClientsFiltersDbName.LAST_NAME,ClientsFiltersDbName.FIRST_NAME, ClientsFiltersDbName.ADDRESS,
            ClientsFiltersDbName.PHONE,ClientsFiltersDbName.E_MAIL};


    public Inventory(Context contex){
        inventoryHelper = new InventoryHelper(contex);
        db = inventoryHelper.getWritableDatabase();
    }

    class ProductCategoriesCursor extends CursorWrapper {
        public ProductCategoriesCursor(Cursor cursor){
            super(cursor);
        }
        public Product_Categories getCategory(){
            Cursor cursor = getWrappedCursor();
            return new Product_Categories(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductCategoriesTable.Columns.ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.ProductCategoriesTable.Columns.DESCRIPTION)));
        }
    }
    class AssemblyCursor extends CursorWrapper{
        public AssemblyCursor(Cursor cursor){
            super(cursor);

        }
        public Assembly getAssembly(){
            Cursor cursor = getWrappedCursor();
            return  new Assembly(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssembliesTable.Columns.ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.AssembliesTable.Columns.DESCRIPTION)));

        }
    }
    class ProductCursor extends CursorWrapper{
        public ProductCursor(Cursor cursor){
            super(cursor);
        }
        public ProductLara getProduct(){
            Cursor cursor = getWrappedCursor();
            return new ProductLara(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.PRICE)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.QUANTITY)));
        }
    }
    class AssemblyProductsCursor extends CursorWrapper{
        public AssemblyProductsCursor(Cursor cursor){
            super(cursor);
        }
        public AssemblyProducts getAssemblyProducts(){//justCreateToRead
            Cursor cursor = getWrappedCursor(); //the first param is assembly_id
            return new AssemblyProducts(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProductsLaraTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProductsLaraTable.Columns.PRODUCT_ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProductsLaraTable.Columns.QUANTITY)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProductsLaraTable.Columns.CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.AssemblyProductsLaraTable.Columns.DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.AssemblyProductsLaraTable.Columns.PRICE)));
        }
    }
    class CustomersCursor extends CursorWrapper{
        public CustomersCursor(Cursor cursor){
            super(cursor);
        }
        public Customer getCostumer(){
            Cursor cursor = getWrappedCursor();
            return new Customer(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.LAST_NAME)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE1)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE2)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.PHONE3)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.E_MAIL))
            );
        }

    }
    class OrdersCursor extends CursorWrapper{
        public OrdersCursor(Cursor cursor){
            super(cursor);
        }
        public Order getOrder(){
            Cursor cursor = getWrappedCursor();
            return new Order(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.STATUS_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.STATUS_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.COSTUMER_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.COSTUMER_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.COSTUMER_LASTNAME)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.DATE)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersTable.Columns.CHANGE_LOG))
            );
        }

    }
    class OrderStatusCursor extends CursorWrapper{
        public OrderStatusCursor(Cursor cursor){
            super(cursor);
        }
        public Order_Status getOrderStatus(){
            Cursor cursor = getWrappedCursor();
            return new Order_Status(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.EDITABLE)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.NEXT)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrdersStatusTable.Columns.PREVIOUS)));
        }

    }
    class OrderAssemblyCursor extends CursorWrapper{
        public OrderAssemblyCursor(Cursor cursor){
            super(cursor);
        }
        public Order_Assembly getOrderAssembly(){
            Cursor cursor = getWrappedCursor();
            return new Order_Assembly(cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrderAssembliesTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrderAssembliesTable.Columns.ASSEMBLY_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.OrderAssembliesTable.Columns.ASSEMBLY_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.OrderAssembliesTable.Columns.QUANTITY)));
        }

    }



    public void SetVirtualTableOfProducts()
    {
        //En esta función se extraen todas las cantidades actuales de los productos en stock
        //Se creará una nueva tabla en donde sólo importan los p_id, p_description, p_qty.
        //Aunque se guarden todos los datos un objeto Producto

        String query = null;
        ArrayList<Product> listOrigProducts = new ArrayList<Product>();
        Cursor cursor ;

        query = "SELECT *\n" +
                "FROM products p;";
        cursor = db.rawQuery(query,null);

        //cursor.getString(cursor.getColumnIndex(InventoryDbSchema.CustomersTable.Columns.FIRST_NAME)),
        while (cursor.moveToNext()){
            listOrigProducts.add(new Product( cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.CATEGORY_ID)),
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
        //Se agrega una descripción cualquiera para que no produzca un problema cuando se guardan '' o "
        //De todas maneras la descripción no importa en esta nuevatabla
    }

    public void SetNeededProductsInSimulation(String critreria)
    {
        //Cargar los productos actuales en el stock
        SetVirtualTableOfProducts();

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


        query = " CREATE TABLE [prod_needed](" +
                " [ord_id] INTEGER," +
                " [prod_id] INTEGER,"+
                " [prod_qty_needed] INTEGER"+
                " );";
        db.execSQL(query);

        for(SimulatedOrder simOrd : listSimOrders) {
            if (!simOrd.getStatus().equals("Cancelado")) {


                int ordIdToGetHisProducts = simOrd.getId();

                listProdPerOrder = getAllProductsOfOrder(ordIdToGetHisProducts); //Tengo los productos de cada order
                //1.¿El producto de la orden se encuentra en la tabla "prod_needed"?
                //¿Sí? Obtener el valor más negativo de la cantidad de ese producto y a esa cantidad
                //restarle la cantidad de ese producto necesitado por el ensamble

                for (Product pOrd : listProdPerOrder) {

                    int productOfSelectedOrder_id = pOrd.getId();   //Id del producto que se compara.
                    int productOfSelectedOrder_qty = pOrd.getQty(); //Qty del producto que se compara.


                    query = "SELECT *" +
                            " FROM prod_needed pn;";
                    cursor = db.rawQuery(query, null);
                    //Usar mejor para comparar
                    if (cursor.moveToFirst()) { //Verificar si la tabla no esta vacía

                        cursor.close();

                        //Verificar si el id del producto a buscar ya se encuentra en la tabla

                        query = "SELECT * " +
                                " FROM prod_needed pn" +
                                " WHERE pn.ord_id =" + String.valueOf(productOfSelectedOrder_id) + ";";
                        cursor = db.rawQuery(query, null);

                        if (cursor.moveToFirst()) {
                            //Hacer query para buscar en la tabla si se tiene la misma combinación de prod_id y order_id
                            query = "SELECT pn.ord_id, pn.prod_id, pn.prod_qty_needed" +
                                    " FROM prod_needed pn" +
                                    " WHERE (pn.ord_id = " + String.valueOf(ordIdToGetHisProducts) +
                                    " AND pn.prod_id = " + String.valueOf(productOfSelectedOrder_id) + ");";
                            cursor = db.rawQuery(query, null);

                            if (cursor.moveToFirst()) {  //Si ya se encuentra la combinación p_id y o_id en la tabla
                                //cursor.moveToFirst();
                                int actualQty = cursor.getInt(2);
                                cursor.close();

                                int newMinQty = actualQty - productOfSelectedOrder_qty;

                                query = "UPDATE prod_needed SET prod_qty_needed = " + String.valueOf(newMinQty) +
                                        " WHERE (ord_id = " + String.valueOf(ordIdToGetHisProducts) +
                                        " AND prod_id = " + String.valueOf(productOfSelectedOrder_id) + ");";
                                db.execSQL(query);


                            } else {
                                cursor.close();

                                query = "SELECT MIN(pn.prod_qty_needed) " +
                                        " FROM prod_needed pn" +
                                        " WHERE pn.prod_id = " + String.valueOf(productOfSelectedOrder_id) + ";";
                                cursor = db.rawQuery(query, null);
                                cursor.moveToFirst(); //cursor.moveToNext(); //Probar con moveToFirst
                                int minQtyInProductsNeededcursor = cursor.getInt(0);

                                //analizando sumar el pn.prod_qty_needed
                                int newMinQty = minQtyInProductsNeededcursor - productOfSelectedOrder_qty;

                                query = "INSERT INTO prod_needed (ord_id, prod_id, prod_qty_needed)" +
                                        " VALUES(" + String.valueOf(ordIdToGetHisProducts) + ", " + String.valueOf(productOfSelectedOrder_id) +
                                        ", " + String.valueOf(newMinQty) +
                                        ");";

                                db.execSQL(query);
                            }
                        } else {  //Si no se encuentra en la tabla de prod_needed comparar los datos de la tabla del stock
                            //Hacer la resta en la tabla de los productos del stock

                            //Obtener el valor de la cantidad del producto que se busca
                            query = "SELECT sp.p_qty" +
                                    " FROM simulated_products sp" +
                                    " WHERE sp.p_id = " + String.valueOf(productOfSelectedOrder_id)
                                    + ";";

                            cursor = db.rawQuery(query, null);
                            cursor.moveToFirst();
                            int stockQty = cursor.getInt(0);
                            cursor.close();
                            //Falta restarle el qty del producto de la orden.
                            int newStockQty = stockQty - productOfSelectedOrder_qty;

                            if (newStockQty < 0) {
                                //Agregarlo de una vez a prod_needed


                                query = "INSERT INTO prod_needed (ord_id, prod_id, prod_qty_needed)" +
                                        " VALUES(" + String.valueOf(ordIdToGetHisProducts) + ", " + String.valueOf(productOfSelectedOrder_id) +
                                        ", " + String.valueOf(newStockQty) +
                                        ");";
                                db.execSQL(query);

                                //Modificar también el stock
                                query = "UPDATE simulated_products SET p_qty = " + String.valueOf(newStockQty) +
                                        " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";";

                                db.execSQL(query);
                            } else {
                                query = "UPDATE simulated_products SET p_qty = " + String.valueOf(newStockQty) +
                                        " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";";

                                db.execSQL(query);
                            }

                        }
                    } else {
                        cursor.close();
                        //Si la tabla está vacía,
                        //obtener el valor de la cantidad del producto que se busca
                        //y restárselo al stock.

                        query = "SELECT sp.p_qty" +
                                " FROM simulated_products sp" +
                                " WHERE sp.p_id = " + String.valueOf(productOfSelectedOrder_id)
                                + ";";

                        cursor = db.rawQuery(query, null);
                        cursor.moveToFirst();
                        int stockQty = cursor.getInt(0);
                        cursor.close();
                        //Falta restarle el qty del producto de la orden.
                        int newStockQty = stockQty - productOfSelectedOrder_qty;

                        if (newStockQty < 0) {
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
                                    " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";";

                            db.execSQL(query);
                        } else {
                            query = "UPDATE simulated_products SET p_qty = " + String.valueOf(newStockQty) +
                                    " WHERE p_id = " + String.valueOf(productOfSelectedOrder_id) + ";";

                            db.execSQL(query);
                        }

                    }
                }

            }

            //Reiniciar el stock
            SetVirtualTableOfProducts();


            //Sacar el query para determinar si la orden se encuentra en la tabla de product_needed
            //y verificar si su número de productos = o < que los productos con el id de la orden
            //en la tabla, para determinar el color del itemView (editar el canBeTaken)
            if (listSimOrders.isEmpty() != true) {

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
                    } else if (difBetweenNumOfProductQty == numOfProductInOrder) { //No hay productos faltantes
                        //es decir, puede Confirmarse la orden
                        query = "UPDATE simulated_orders_temporary SET ord_can_be_taken = 2 WHERE ord_id = " +
                                String.valueOf(simulatedOrder.getId()) + ";";
                        db.execSQL(query);
                    } else if (difBetweenNumOfProductQty > 0) {  //Hacen falta productos para poder Confirmar esta orden
                        //Pero no hacen falta todos los prductos
                        query = "UPDATE simulated_orders_temporary SET ord_can_be_taken = 1 WHERE ord_id = " +
                                String.valueOf(simulatedOrder.getId()) + ";";
                        db.execSQL(query);
                    }
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

        query="SELECT p.id, p.category_id, p.description, p.price, (oa.qty * ap.qty ) AS prod_qty\n" +
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
                    cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.CATEGORY_ID)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.PRICE))/100,
                    cursor.getInt(4)
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
                cursor.getInt(cursor.getColumnIndex(InventoryDbSchema.ProductsTable.Columns.CATEGORY_ID)),
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




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = new java.util.Date();


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

            try {date=dateFormat.parse(cursor.getString(7)); }
            catch (ParseException e) {e.printStackTrace();}


                dInfo.add(new DetailInfo(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        date,
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
                    cursor.getDouble(10) / 100));


        }

        cursor.close();



        return dInfo;

    }


    ///////////////Products Section

    public List<Proudct_Category> getAllCategories()
    {
        ArrayList<Proudct_Category> categories = new ArrayList<Proudct_Category>();

        Cursor cursor = db.rawQuery("select * from product_categories",null);

        while (cursor.moveToNext()) {
           categories.add(new Proudct_Category(cursor.getInt(0),cursor.getString(1)));


        }


        cursor.close();

        return categories;

    }



    //This shit falla preguntarle a Edson
    public boolean comprobacion2 (int categoryId, String StartWith)
    {
        String catId = String.valueOf(categoryId);
        Cursor cursor = db.rawQuery("select * from products p  inner join product_categories pc on P.category_id = pc.id \n" +
                "where p.category_id = "+catId+" and p.description like '"+StartWith+"%' order by description asc",null);

        cursor.moveToFirst();



        if (cursor.getCount()==0) {
            cursor.close();
            return true;

        } else {

            cursor.close();
            return false;
        }

    }




    public int GetCategoryId (String cat)
    {
        int id;

        Cursor cursor = db.rawQuery(" select id from product_categories where description like '"+cat+"'",null);
        cursor.moveToFirst();
          id=cursor.getInt(0);


        return id;
    }



    public ArrayList<Product> GetFilteredProducts (int CId, String SW, String cat_des)
    {
        String catId=String.valueOf(CId);
        ArrayList<Product> products = new ArrayList<Product>();


        Log.i("log",SW);


        if(cat_des.equals("Todas")&& SW.length() == 0)
        {
            Cursor cursor = db.rawQuery("select * from products p  inner join product_categories pc on p.category_id = pc.id  order by description asc;",null);



            while (cursor.moveToNext()) {
                if (cursor.getCount() == 0) {
                    cursor.close();
                    products.clear();
                    return products;

                }

                else {
                    products.add(new Product(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                            cursor.getDouble(3) / 100, cursor.getInt(4), cursor.getString(6)));
                }

            }

            cursor.close();
            return products;

        }
////////////////////////////////////////////////////////////////////////////////////////
        if(cat_des.equals("Todas") && SW.length() > 0)
        {
            Cursor cursor = db.rawQuery("select * from products p  inner join product_categories pc on P.category_id = pc.id "+
                    "where p.description like '%"+SW+"%' order by description asc",null);

            while (cursor.moveToNext()) {
                if (cursor.getCount() == 0) {
                    cursor.close();
                    products.clear();
                    return products;

                }

                else {
                    products.add(new Product(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                            cursor.getDouble(3) / 100, cursor.getInt(4), cursor.getString(6)));
                }

            }

            cursor.close();
            return products;

        }
///////////////////////////////////////////////////////////////////////////////////////
         if(cat_des.equals("Todas")==false && SW.length() == 0)
        {
            Cursor cursor = db.rawQuery("select * from products p  inner join product_categories pc on P.category_id = pc.id \n" +
                    "where  p.category_id = "+catId+"  order by description asc",null);

            while (cursor.moveToNext()) {
                if (cursor.getCount() == 0) {
                    cursor.close();
                    products.clear();
                    return products;

                } else {
                    products.add(new Product(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                            cursor.getDouble(3) / 100, cursor.getInt(4), cursor.getString(6)));
                }






            }
            cursor.close();
            return products;

        }
////////////////////////////////////////////////////////////////////////////////////////////
       if(cat_des.equals("Todas")==false && SW.length() > 0)
        {
            Cursor cursor = db.rawQuery("select * from products p  inner join product_categories pc on P.category_id = pc.id \n" +
                    "where p.category_id = "+catId+" and p.description like '%"+SW+"%' order by description asc",null);

            while (cursor.moveToNext()) {
                if (cursor.getCount() == 0) {
                    cursor.close();
                    products.clear();
                    return products;

                } else {
                    products.add(new Product(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                            cursor.getDouble(3) / 100, cursor.getInt(4), cursor.getString(6)));
                }






            }
            cursor.close();
            return products;

        }

      products.clear();
            return products;


    }


    public  boolean CheckDescription (String description)
    {

        Cursor cursor = db.rawQuery("select * from products p where p.description like '"+description+"';",null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0)
        {
            cursor.close();
            return false;
        }

        else
        {
            cursor.close();
            return true;
        }





    }

    public void AddProduct (String categoryId, String descripcion, String precio)
    {
        Cursor cursor = db.rawQuery("SELECT MAX(id)AS qty FROM products",null);
        cursor.moveToNext();
        int max= cursor.getInt(0) + 1;
        String g = String.valueOf(max);
        Log.i("eval = ",g);
        //INSERT INTO [products](id, category_id, description, price, qty) VALUES(2, 0, 'Toshiba 3.5" 3TB, SATA 6.0Gb/s 64MB Cache, 7200 RPM', 209900, 0);

        ContentValues contenedor = new ContentValues();
        contenedor.put("id",g);
        contenedor.put("category_id",categoryId);
        contenedor.put("description",descripcion);
        contenedor.put("price",precio);
        contenedor.put("qty","0");

        db.insert("products",null,contenedor);
        cursor.close();

    }

    public void DeleteProduct (int Id)
    {
        String id= String.valueOf(Id);
        String args[]={id};


        db.delete("products", "id = ?", args);
        //r.setAdapter(new MyProductAdapter(String.valueOf(fragment),Integer.valueOf(categoryId),String.valueOf(categoria)));

    }

    public void UpdateProduct (String newDescription, int id, String newPrice,String Category)
    {
        String Id = String.valueOf(id);
        String args[] = {Id};
        ContentValues valores = new ContentValues();
        valores.put("description",newDescription);
        valores.put("price",newPrice);
        valores.put("category_id",Category);


        Log.i("eval = ",newDescription);
        Log.i("eval = ",String.valueOf(id));
        Log.i("eval = ",newPrice);


//Actualizamos el registro en la base de datos
        db.update("products", valores, "id = ?",args );
    }

    public void AddStockProduct( int id, String qty)
    {
        String Id = String.valueOf(id);
        String args[] = {Id};
        ContentValues valores = new ContentValues();

        valores.put("qty",qty);

       // Log.i("eval = ",newPrice);


//Actualizamos el registro en la base de datos
        db.update("products", valores, "id = ?",args );
    }


    public List<Product> prueba3 (int CId, String SW, String cat_des) {
        String catId = String.valueOf(CId);

        Log.i("log", catId);
        Log.i("log", SW);
        Log.i("log", cat_des);
        ArrayList<Product> products = new ArrayList<Product>();
        Cursor cursor = db.rawQuery("select * from products p  inner join product_categories pc on P.category_id = pc.id \n" +
                "where p.category_id = " + catId + " and p.description like '%" + SW + "%' order by description asc", null);


        while (cursor.moveToNext()) {
            if (cursor.getCount() == 0) {
                cursor.close();
                products.clear();
                return products;

            } else {
                products.add(new Product(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getDouble(3) / 100, cursor.getInt(4), cursor.getString(6)));
            }


            cursor.close();



        }
        return products;
    }
    //Manuel Lara------------------------------------------------------------------------------
    public List<Product_Categories>getAllCategoriesLara(){
        ArrayList<Product_Categories>list= new ArrayList<Product_Categories>();
        ProductCategoriesCursor cursor = new ProductCategoriesCursor(db.rawQuery("SELECT*FROM product_categories ORDER BY id",null));
        while (cursor.moveToNext()){
            list.add(cursor.getCategory());
        }
        cursor.close();

        return list;
    }
    public List<Assembly>getAllAssemblies(){
        ArrayList<Assembly>list = new ArrayList<Assembly>();
        AssemblyCursor cursor = new AssemblyCursor(db.rawQuery("SELECT*FROM assemblies ORDER BY id",null));
        while (cursor.moveToNext()){
            list.add(cursor.getAssembly());
        }
        cursor.close();

        return list;

    }
    public List<Assembly>getAllAssembliesOrderByName(){
        ArrayList<Assembly>list = new ArrayList<Assembly>();
        AssemblyCursor cursor = new AssemblyCursor(db.rawQuery("SELECT*FROM assemblies ORDER BY description ASC",null));
        while (cursor.moveToNext()){
            list.add(cursor.getAssembly());
        }
        cursor.close();

        return list;

    }
    public List<Assembly>getAllAssembliesOnTextChange(String textSearch){
        ArrayList<Assembly>list = new ArrayList<Assembly>();
        AssemblyCursor cursor = new AssemblyCursor(db.rawQuery("SELECT * FROM assemblies WHERE description LIKE '%"+textSearch+"%' ORDER BY description ASC",null));
        while (cursor.moveToNext()){
            list.add(cursor.getAssembly());
        }
        cursor.close();

        return list;

    }
    public List<ProductLara>getAllProducts(){
        ArrayList<ProductLara>list = new ArrayList<ProductLara>();
        ProductCursor cursor = new ProductCursor(db.rawQuery("SELECT * FROM products ORDER BY description ASC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getProduct());

        }
        cursor.close();
        return list;

    }
    public List<Customer>getAllCustomers(){
        ArrayList<Customer>list = new ArrayList<Customer>();
        CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT*FROM customers ORDER BY id",null));
        while (cursor.moveToNext()){
            list.add(cursor.getCostumer());
        }
        cursor.close();
        return list;

    }
    public List<Customer>getAllCustomerSortedByName(){

        ArrayList<Customer>list = new ArrayList<Customer>();
        CustomersCursor cursor = new CustomersCursor(db.rawQuery("SELECT*FROM customers GROUP BY last_name, first_name",null));
        while (cursor.moveToNext()){
            list.add(cursor.getCostumer());
        }
        cursor.close();
        return list;

    }
    public List<Order_Status>getAllOrderStatus(){
        ArrayList<Order_Status>list = new ArrayList<Order_Status>();
        OrderStatusCursor cursor = new OrderStatusCursor(db.rawQuery("SELECT*FROM order_status ORDER BY id",null));
        while (cursor.moveToNext()){
            list.add(cursor.getOrderStatus());
        }
        cursor.close();
        return list;

    }
    public List<ProductLara>getProductsByCategory(int category){
        ArrayList<ProductLara>list = new ArrayList<ProductLara>();
        ProductCursor cursor = new ProductCursor(db.rawQuery("SELECT * FROM products WHERE category_id = "+ String.valueOf(category) +" ORDER BY description ASC",null));
        while (cursor.moveToNext()){
            list.add(cursor.getProduct());
        }
        cursor.close();
        return list;

    }
    public List<ProductLara>getProductsByCategoryIdAndTextChange(int category,String query){
        ArrayList<ProductLara>list = new ArrayList<ProductLara>();
        ProductCursor cursor = new ProductCursor(db.rawQuery("SELECT*FROM products WHERE "+String.valueOf(category)+" AND description LIKE '%"+query+"%' ORDER BY description ASC",null));
        while (cursor.moveToNext()){
            list.add(cursor.getProduct());
        }
        cursor.close();
        return list;

    }
    public List<ProductLara>getProductsByTextChange(String query){
        ArrayList<ProductLara>list = new ArrayList<ProductLara>();
        ProductCursor cursor = new ProductCursor(db.rawQuery("SELECT*FROM products WHERE description LIKE '%"+query+"%' ORDER BY description ASC",null));
        while (cursor.moveToNext()){
            list.add(cursor.getProduct());
        }
        cursor.close();
        return list;

    }
    public ProductLara getProductById(int category){
        ProductLara product;
        ProductCursor cursor = new ProductCursor(db.rawQuery("SELECT*FROM products WHERE id = "+ String.valueOf(category),null));
        cursor.moveToFirst();
        product = cursor.getProduct();

        cursor.close();
        return product;

    }
    public boolean KnowIfExistsTheCategory(Product_Categories category)
    {
        boolean flag = true;
        categories=getAllCategoriesLara();
        for(Product_Categories cat:categories)
        {
            if(cat.getDescription().equalsIgnoreCase(category.getDescription()))
            {
                flag=false;
            }

        }
        return flag;
    }
    public boolean KnowIfExistsTheAssembly(String newAssembly)
    {
        boolean flag = false;
        assemblies = getAllAssemblies();
        for (Assembly assembly:assemblies)
        {
            if(assembly.getDescription().equalsIgnoreCase(newAssembly))
            {
                flag=true;
            }
        }
        return flag;
    }
    public int getLastIdCategory()
    {

        int MaxId=0;
        Cursor cursor = db.rawQuery("SELECT MAX(id) AS qty FROM product_categories",null);
        if(cursor.moveToFirst()){
            do
            {
                MaxId = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",MaxId+"last id");
        cursor.close();
        return MaxId;

    }
    public int getLastIdAssembly()
    {
        int MaxId=0;
        Cursor cursor = db.rawQuery("SELECT MAX(id) AS qty FROM assemblies",null);
        if(cursor.moveToFirst()){
            do
            {
                MaxId = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",MaxId+" last id");
        cursor.close();
        return MaxId;

    }
    public void InsertNewCategory(Product_Categories category)
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.ProductCategoriesTable.Columns.DESCRIPTION,category.getDescription());
        values.put(InventoryDbSchema.ProductCategoriesTable.Columns.ID,category.getId());
        db.insert(InventoryDbSchema.ProductCategoriesTable.NAME,null,values);
        db.close();
    }
    public void DeleteCategory(Product_Categories category){
        db.delete(InventoryDbSchema.ProductCategoriesTable.NAME, InventoryDbSchema.ProductCategoriesTable.Columns.ID + " = ?",
                new String[]{String.valueOf(category.getId())});
        db.close();
    }
    public void UpdateCategory(Product_Categories category){
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.ProductCategoriesTable.Columns.DESCRIPTION,category.getDescription());
        db.update(InventoryDbSchema.ProductCategoriesTable.NAME,values,InventoryDbSchema.ProductCategoriesTable.Columns.ID + " = ?",
                new String[]{Integer.toString(category.getId())});

        db.close();
    }
    //this is for assemblies
    public Assembly getAssemblyById(int assemblyId)
    {
        Assembly assembly;
        AssemblyCursor cursor = new AssemblyCursor(db.rawQuery("SELECT*FROM assemblies WHERE id = "+ String.valueOf(assemblyId),null));
        cursor.moveToFirst();
        assembly = cursor.getAssembly();
        cursor.close();
        return assembly;

    }
    public List<AssemblyProducts>getAllProductsOfAssemblyById(int assemblyId)
    {
        ArrayList<AssemblyProducts>list = new ArrayList<AssemblyProducts>();//the first param is for assembly id
        AssemblyProductsCursor cursor = new AssemblyProductsCursor(db.rawQuery("SELECT ap.id, ap.product_id,ap.qty, p.category_id,p.description,p.price\n" +
                "FROM assembly_products ap \n" +
                "INNER JOIN products p ON (ap.product_id = p.id)\n" +
                "WHERE ap.id = "+ String.valueOf(assemblyId)+"\n" +
                "ORDER BY p.description ASC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getAssemblyProducts());

        }
        cursor.close();
        return list;
    }
    public ArrayList<AssemblyProducts>getAllProductsOfAssemblyId(int assemblyId)
    {
        ArrayList<AssemblyProducts>list = new ArrayList<AssemblyProducts>();//the first param is for assembly id
        AssemblyProductsCursor cursor = new AssemblyProductsCursor(db.rawQuery("SELECT ap.id, ap.product_id,ap.qty, p.category_id,p.description,p.price\n" +
                "FROM assembly_products ap \n" +
                "INNER JOIN products p ON (ap.product_id = p.id)\n" +
                "WHERE ap.id = "+ String.valueOf(assemblyId)+"\n" +
                "ORDER BY p.description ASC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getAssemblyProducts());

        }
        cursor.close();
        return list;

    }

    public boolean InsertNewAssembly(Assembly assembly){
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.AssembliesTable.Columns.DESCRIPTION,assembly.getDescription());
        values.put(InventoryDbSchema.AssembliesTable.Columns.ID,assembly.getId());
        db.insert(InventoryDbSchema.AssembliesTable.NAME,null,values);
        return true;

    }
    public void UpdateAssembly(Assembly assembly)
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.AssembliesTable.Columns.DESCRIPTION, assembly.getDescription());
        db.update(InventoryDbSchema.AssembliesTable.NAME,values,InventoryDbSchema.AssembliesTable.Columns.ID + " = ?",
                new String[]{Integer.toString(assembly.getId())});

        // db.close();

    }
    public void DeleteAssembly(Assembly assembly)
    {
        db.delete(InventoryDbSchema.AssembliesTable.NAME, InventoryDbSchema.AssembliesTable.Columns.ID + " = ?",
                new String[]{String.valueOf(assembly.getId())});
        //db.close();
    }
    public void InsertNewAssemblyProduct(AssemblyProducts assemblyProducts){
        ContentValues values = new ContentValues();

        values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.QUANTITY,assemblyProducts.getQuantity());
        values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.PRODUCT_ID,assemblyProducts.getProductId());
        values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID,assemblyProducts.getAssemblyId());
        db.insert(InventoryDbSchema.Assembly_ProductsTable.NAME,null,values);


    }
    public void UpdateAssemblyProduct(AssemblyProducts assemblyProducts)
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.QUANTITY, assemblyProducts.getQuantity());
        db.update(InventoryDbSchema.Assembly_ProductsTable.NAME,values,InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID + " = ? AND "+InventoryDbSchema.Assembly_ProductsTable.Columns.PRODUCT_ID + " = ?",
                new String[]{Integer.toString(assemblyProducts.getAssemblyId()),Integer.toString(assemblyProducts.getProductId())});




    }
    public void DeleteAssemblyProduct(AssemblyProducts assemblyProducts)
    {
        db.delete(InventoryDbSchema.Assembly_ProductsTable.NAME, InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID + " = ? AND "+InventoryDbSchema.Assembly_ProductsTable.Columns.PRODUCT_ID + " = ?",
                new String[]{Integer.toString(assemblyProducts.getAssemblyId()),Integer.toString(assemblyProducts.getProductId())});
        //db.close();
    }
    public void InsertListOfNewAssemblyProduct(ArrayList<AssemblyProducts> products){
        ContentValues values = new ContentValues();
        for(AssemblyProducts product:products) {
            values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.QUANTITY, product.getQuantity());
            values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.PRODUCT_ID, product.getProductId());
            values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID, product.getAssemblyId());
            db.insert(InventoryDbSchema.Assembly_ProductsTable.NAME, null, values);
        }
        db.close();

    }
    public void DeleteListOfNewAssemblyProduct(List<AssemblyProducts>products){
        ContentValues values = new ContentValues();
        for(AssemblyProducts assemblyProducts: products)
        {
            db.delete(InventoryDbSchema.Assembly_ProductsTable.NAME, InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID + " = ? AND "+InventoryDbSchema.Assembly_ProductsTable.Columns.PRODUCT_ID + " = ?",
                    new String[]{Integer.toString(assemblyProducts.getAssemblyId()),Integer.toString(assemblyProducts.getProductId())});
        }
        //db.close();
    }
    public void DeleteListOfModifiedAssemblyProduct(ArrayList<AssemblyProducts>products)//to delete the products from temporary list
    {
        ContentValues values = new ContentValues();
        for(AssemblyProducts assemblyProducts: products)
        {
            db.delete(InventoryDbSchema.Assembly_ProductsTable.NAME, InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID + " = ? AND "+InventoryDbSchema.Assembly_ProductsTable.Columns.PRODUCT_ID + " = ?",
                    new String[]{Integer.toString(assemblyProducts.getAssemblyId()),Integer.toString(assemblyProducts.getProductId())});
        }

    }
    public void UpdateListOfModifiedAssemblyProduct(List<AssemblyProducts>products)
    {
        for (AssemblyProducts assemblyProducts:products) {
            ContentValues values = new ContentValues();
            values.put(InventoryDbSchema.Assembly_ProductsTable.Columns.QUANTITY, assemblyProducts.getQuantity());
            db.update(InventoryDbSchema.Assembly_ProductsTable.NAME, values, InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID + " = ? AND " + InventoryDbSchema.Assembly_ProductsTable.Columns.PRODUCT_ID + " = ?",
                    new String[]{Integer.toString(assemblyProducts.getAssemblyId()), Integer.toString(assemblyProducts.getProductId())});
        }
    }
    public void DeleteAllTheProductsFromAssemblyById(int AssemblyId)
    {
        db.delete(InventoryDbSchema.Assembly_ProductsTable.NAME, InventoryDbSchema.Assembly_ProductsTable.Columns.ASSEMBLY_ID + " = ?",
                new String[]{Integer.toString(AssemblyId)});
    }
    public int GetTheNumberOfOrdersRelatedToAssembly(int AssemblyId)
    {
        int value = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM order_assemblies WHERE assembly_id = "+ String.valueOf(AssemblyId),null);//ToKnowIfTheAssemblyIsContainInaOrder
        value = cursor.getCount();
        cursor.close();
        return value;

    }
    //Here we start Orders
    public List<Order> getAllOrdersOrderByDate(){
        ArrayList<Order>list = new ArrayList<Order>();//the first param is for assembly id
        OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT o.id, o.status_id, os.description,o.customer_id,c.first_name,c.last_name, substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2) AS date ,o.change_log\n" +
                "FROM orders o \n" +
                "INNER JOIN order_status os ON (o.status_id = os.id)\n" +
                "INNER JOIN customers c ON(o.customer_id = c.id)\n" +
                "ORDER BY date DESC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrder());

        }
        cursor.close();
        return list;

    }
    public List<Order> getAllOrdersByCustomerId(int costumerId)
    {
        ArrayList<Order>list = new ArrayList<Order>();//the first param is for assembly id
        OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT o.id, o.status_id, os.description,o.customer_id,c.first_name,c.last_name, substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2) AS    date ,o.change_log\n" +
                "FROM orders o \n" +
                "INNER JOIN order_status os ON (o.status_id = os.id)\n" +
                "INNER JOIN customers c ON(o.customer_id = c.id)\n" +
                "WHERE o.customer_id = "+String.valueOf(costumerId)+"\n" +
                "ORDER BY date DESC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrder());

        }
        cursor.close();
        return list;

    }
    public List<Order>getAllOrdersByStatus (String status)
    {

        ArrayList<Order>list = new ArrayList<Order>();//the first param is for assembly id
        OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT o.id, o.status_id, os.description,o.customer_id,c.first_name,c.last_name, substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2) AS    date ,o.change_log\n" +
                "FROM orders o \n" +
                "INNER JOIN order_status os ON (o.status_id = os.id)\n" +
                "INNER JOIN customers c ON(o.customer_id = c.id)\n" +
                "WHERE os.id IN ("+status+") \n" +
                "ORDER BY date DESC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrder());

        }
        cursor.close();
        return list;

    }
    public List<Order>getAllOrdersByStatusAndCustomer(int customer_Id,String status)
    {
        ArrayList<Order>list = new ArrayList<Order>();//the first param is for assembly id
        OrdersCursor cursor = new OrdersCursor(db.rawQuery("SELECT o.id, o.status_id, os.description,o.customer_id,c.first_name,c.last_name, substr(o.date,7,4)||'-'||substr(o.date,4,2)||'-'||substr(o.date,1,2) AS    date ,o.change_log\n" +
                "FROM orders o \n" +
                "INNER JOIN order_status os ON (o.status_id = os.id)\n" +
                "INNER JOIN customers c ON(o.customer_id = c.id)\n" +
                "WHERE o.customer_id = "+String.valueOf(customer_Id)+" AND o.status_id IN ("+status+") \n" +
                "ORDER BY date DESC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrder());

        }
        cursor.close();
        return list;
    }
    public int getLastIdOfOrder()//get the last id of order
    {
        int MaxId=0;
        Cursor cursor = db.rawQuery("SELECT MAX(id) AS qty FROM orders",null);
        if(cursor.moveToFirst()){
            do
            {
                MaxId = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",MaxId+" last id");
        cursor.close();
        return MaxId;

    }
    public String getLastNameOfCustomer(int customerId)//get the last id of order
    {
        String last_name=null;
        Cursor cursor = db.rawQuery("SELECT last_name FROM customers WHERE id = "+String.valueOf(customerId),null);
        if(cursor.moveToFirst()){
            do
            {
                last_name = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",last_name+" last name");
        cursor.close();
        return last_name;

    }
    public String getFirstNameOfCustomer(int customerId)//get the last id of order
    {
        String first_name=null;
        Cursor cursor = db.rawQuery("SELECT first_name FROM customers WHERE id = "+String.valueOf(customerId),null);
        if(cursor.moveToFirst()){
            do
            {
                first_name = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",first_name+" first name");
        cursor.close();
        return first_name;

    }
    public void InsertNewOrder(Order order){
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Orders_Table.Columns.ID,order.getId());
        values.put(InventoryDbSchema.Orders_Table.Columns.STATUS_ID,order.getStatus_Id());
        values.put(InventoryDbSchema.Orders_Table.Columns.COSTUMER_ID,order.getCostumer_Id());
        values.put(InventoryDbSchema.Orders_Table.Columns.DATE,order.getDate());
        values.put(InventoryDbSchema.Orders_Table.Columns.CHANGE_LOG,order.getChange_Log());
        db.insert(InventoryDbSchema.Orders_Table.NAME,null,values);


    }
    public void UpdateOrder(Order order)
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Orders_Table.Columns.STATUS_ID,order.getStatus_Id());
        values.put(InventoryDbSchema.Orders_Table.Columns.COSTUMER_ID,order.getCostumer_Id());
        values.put(InventoryDbSchema.Orders_Table.Columns.DATE,order.getDate());
        values.put(InventoryDbSchema.Orders_Table.Columns.CHANGE_LOG,order.getChange_Log());

        db.update(InventoryDbSchema.Orders_Table.NAME,values,InventoryDbSchema.Orders_Table.Columns.ID+ " = ?",
                new String[]{Integer.toString(order.getId())});

        // db.close();

    }
    public void DeleteOrder(int OrderId)
    {
        db.delete(InventoryDbSchema.Orders_Table.NAME, InventoryDbSchema.Orders_Table.Columns.ID + " = ?",
                new String[]{Integer.toString(OrderId)});
    }
    public Assembly assembly(int assemblyId){
        Assembly assembly;
        AssemblyCursor cursor = new AssemblyCursor(db.rawQuery("SELECT*FROM assemblies WHERE id = "+ String.valueOf(assemblyId),null));
        cursor.moveToFirst();
        assembly = cursor.getAssembly();

        cursor.close();
        return assembly;

    }
    public int VerifyTheQuantityOfAssembly(int assemblyId, int orderId)
    {
        int quantity=0;
        Cursor cursor = db.rawQuery("SELECT qty FROM order_assemblies WHERE id = "+orderId+" AND assembly_id = "+assemblyId,null);

        if(cursor.getCount()==0)
        {
            quantity = -1;
        }
        else{
            if(cursor.moveToFirst()) {
                do {
                    quantity = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        }


        Log.d("count",quantity+" quantity");
        cursor.close();
        return quantity;
    }
    public String getAssemblyDescriptionById(int assemblyId)//get the last id of order
    {
        String assemblyDescription=null;
        Cursor cursor = db.rawQuery("SELECT description FROM assemblies WHERE id = "+String.valueOf(assemblyId),null);
        if(cursor.moveToFirst()){
            do
            {
                assemblyDescription = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",assemblyDescription+" first name");
        cursor.close();
        return assemblyDescription;

    }
    public void InsertNewOrderAssembly(Order_Assembly order_assembly){
        ContentValues values = new ContentValues();

        values.put(InventoryDbSchema.Order_AssembliesTable.Columns.QUANTITY,order_assembly.getQuantity());
        values.put(InventoryDbSchema.Order_AssembliesTable.Columns.ASSEMBLY_ID,order_assembly.getAssembly_Id());
        values.put(InventoryDbSchema.Order_AssembliesTable.Columns.ID,order_assembly.getId());
        db.insert(InventoryDbSchema.Order_AssembliesTable.NAME,null,values);


    }
    public void UpdateNewOrderAssembly(Order_Assembly order_assembly)
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Order_AssembliesTable.Columns.QUANTITY, order_assembly.getQuantity());
        db.update(InventoryDbSchema.Order_AssembliesTable.NAME,values,InventoryDbSchema.Order_AssembliesTable.Columns.ID + " = ? AND "+InventoryDbSchema.Order_AssembliesTable.Columns.ASSEMBLY_ID + " = ?",
                new String[]{Integer.toString(order_assembly.getId()),Integer.toString(order_assembly.getAssembly_Id())});


    }
    public void DeleteOrderAssembly(Order_Assembly order_assembly)
    {
        db.delete(InventoryDbSchema.Order_AssembliesTable.NAME, InventoryDbSchema.Order_AssembliesTable.Columns.ID + " = ? AND "+InventoryDbSchema.Order_AssembliesTable.Columns.ASSEMBLY_ID + " = ?",
                new String[]{Integer.toString(order_assembly.getId()),Integer.toString(order_assembly.getAssembly_Id())});
        //db.close();
    }
    public void DeleteAllOrderAssemblyByOrderId(int orderId)
    {
        db.delete(InventoryDbSchema.Order_AssembliesTable.NAME, InventoryDbSchema.Order_AssembliesTable.Columns.ID + " = ?",
                new String[]{Integer.toString(orderId)});
        //db.close();
    }
    public List<Order_Assembly>getAllAssembliesOfOrdersById(int orderId)
    {
        ArrayList<Order_Assembly>list = new ArrayList<Order_Assembly>();//the first param is for assembly id
        OrderAssemblyCursor cursor = new OrderAssemblyCursor(db.rawQuery("SELECT oas.id,oas.assembly_id, a.description,oas.qty \n" +
                "FROM order_assemblies oas\n" +
                "INNER JOIN assemblies a ON(oas.assembly_id = a.id)\n" +
                "WHERE oas.id = "+ String.valueOf(orderId)+"\n" +
                "ORDER BY a.description ASC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrderAssembly());

        }
        cursor.close();
        return list;
    }
    public ArrayList<Order_Assembly>getAllAssembliesOfTheOrder(int orderId)
    {
        ArrayList<Order_Assembly>list = new ArrayList<Order_Assembly>();//the first param is for assembly id
        OrderAssemblyCursor cursor = new OrderAssemblyCursor(db.rawQuery("SELECT oas.id,oas.assembly_id, a.description,oas.qty \n" +
                "FROM order_assemblies oas\n" +
                "INNER JOIN assemblies a ON(oas.assembly_id = a.id)\n" +
                "WHERE oas.id = "+ String.valueOf(orderId)+"\n" +
                "ORDER BY a.description ASC",null));
        while (cursor.moveToNext()) {
            list.add(cursor.getOrderAssembly());

        }
        cursor.close();
        return list;
    }
    public void DeleteListOfModifiedOrderAssemblies(ArrayList<Order_Assembly>order_assemblies)//to delete the products from temporary list
    {
        ContentValues values = new ContentValues();
        for(Order_Assembly order_assembly: order_assemblies)
        {
            db.delete(InventoryDbSchema.Order_AssembliesTable.NAME, InventoryDbSchema.Order_AssembliesTable.Columns.ID + " = ? AND "+InventoryDbSchema.Order_AssembliesTable.Columns.ASSEMBLY_ID + " = ?",
                    new String[]{Integer.toString(order_assembly.getId()),Integer.toString(order_assembly.getAssembly_Id())});
        }

    }
    public void UpdateListOfModifiedOrderAssemblies(ArrayList<Order_Assembly>order_assemblies)
    {
        for (Order_Assembly order_assembly: order_assemblies) {
            ContentValues values = new ContentValues();
            values.put(InventoryDbSchema.Order_AssembliesTable.Columns.QUANTITY, order_assembly.getQuantity());
            db.update(InventoryDbSchema.Order_AssembliesTable.NAME, values, InventoryDbSchema.Order_AssembliesTable.Columns.ID + " = ? AND " + InventoryDbSchema.Order_AssembliesTable.Columns.ASSEMBLY_ID + " = ?",
                    new String[]{Integer.toString(order_assembly.getId()), Integer.toString(order_assembly.getAssembly_Id())});
        }
    }
    public void InsertListOfModifiedOrderAssemblies(ArrayList<Order_Assembly>order_assemblies)
    {
        for(Order_Assembly order_assembly:order_assemblies) {
            ContentValues values = new ContentValues();

            values.put(InventoryDbSchema.Order_AssembliesTable.Columns.QUANTITY, order_assembly.getQuantity());
            values.put(InventoryDbSchema.Order_AssembliesTable.Columns.ASSEMBLY_ID, order_assembly.getAssembly_Id());
            values.put(InventoryDbSchema.Order_AssembliesTable.Columns.ID, order_assembly.getId());
            db.insert(InventoryDbSchema.Order_AssembliesTable.NAME, null, values);

        }
    }
    public String getStatusDescriptionById(int statusId)//get the last id of order
    {
        String status_description=null;
        Cursor cursor = db.rawQuery("SELECT description FROM order_status WHERE id = "+ String.valueOf(statusId),null);
        if(cursor.moveToFirst()){
            do
            {
                status_description = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",status_description+" first name");
        cursor.close();
        return status_description;

    }
    public void UpdateOrderStatusByOrderId(int newStatus,String changeLog,int orderId)
    {
        ContentValues values = new ContentValues();
        values.put(InventoryDbSchema.Orders_Table.Columns.STATUS_ID,newStatus);
        values.put(InventoryDbSchema.Orders_Table.Columns.CHANGE_LOG,changeLog);

        db.update(InventoryDbSchema.Orders_Table.NAME,values,InventoryDbSchema.Orders_Table.Columns.ID+ " = ?",
                new String[]{Integer.toString(orderId)});

        // db.close();

    }




    public int ReturnMaxNumberOfACategory()
    { int value=0;

        return value;
    }
    public int GettheNumberOfProductsinaCategory(int Id_productsCategory)
    {
        int value=0;
        //int valueOfProducts = 0;
        /*Cursor cursor = db.rawQuery("SELECT COUNT(*) AS qty FROM products WHERE category_id = " + String.valueOf(Id_productsCategory),null);
        if(cursor.moveToFirst()){
            do
            {
                valueOfProducts = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        Log.d("count",valueOfProducts + "numberOfProductsInTheCategory");
        cursor.close();*/
        // Cursor cursor = db.rawQuery("SELECT COUNT(*) AS qty FROM products WHERE category_id = " + ,null);
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE category_id = "+ String.valueOf(Id_productsCategory),null);
        value = cursor.getCount();
        cursor.close();
        return value;
    }
















    public void UpdateClient( int id, String FN, String LN,String AD , String PH1, String PH2, String PH3, String EM)
    {
        String Id = String.valueOf(id);
        String args[] = {Id};
        ContentValues valores = new ContentValues();

        valores.put("first_name",FN);
        valores.put("last_name",LN);
        valores.put("address",AD);
        valores.put("phone1",PH1);
        valores.put("phone2",PH2);
        valores.put("phone3",PH3);
        valores.put("e_mail",EM);

        db.update("customers", valores, "id = ?",args );
    }

    public void DeleteCustomer (int ClientId)
    {
        String id= String.valueOf(ClientId);
        String args[]={id};
        db.delete("customers", "id = ?", args);

    }

    public boolean CheckDelete(int Id)
    {
        String id = String.valueOf(Id);
        Cursor cursor = db.rawQuery("select * from orders where customer_id = "+id,null);
        cursor.moveToFirst();
        if(cursor.getCount()==0) return true;
        else return false;
    }




   //End ManuelLara

}



