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


















   //End ManuelLara

}



