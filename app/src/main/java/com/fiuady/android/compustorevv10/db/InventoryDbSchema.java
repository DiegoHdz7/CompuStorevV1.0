package com.fiuady.android.compustorevv10.db;

/**
 * Created by Aarón Madera on 05/04/2017.
 */

public final class InventoryDbSchema {
    public static final class ProductsCategoriesTable{
        public static final String NAME = "product_categories";

        public static final class Columns{
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
        }
    }

    public static final class ProductsTable{
        public static final String NAME = "products";

        public static final class Columns{
            public static final String ID = "id";
            public static final String CAEGORY_ID = "category_id";
            public static final String DESCRIPTION = "description";
            /*precio en centavos, recordar dividir entre 100 al dar el precio*/
            public static final String PRICE = "price";
            public static final String QUANTITY = "qty";
        }
    }

    public static final class AssembliesTable{
        public static final String NAME = "assemblies";

        public static final class Columns{
            public static final String ID = "id";
            public static final String DESCRIPTION = "description";
        }
    }

    public static final class AssemblyProductsTable{
        public static final String NAME = "assembly_products";

        public static final class Columns{
            public static final String ID = "id";
            public static final String PRODUCT_ID = "product_id";
            public static final String QUANTITY = "qty";
        }
    }

    public static final class CustomersTable{
        public static final String NAME = "customers";

        public static final class Columns{
            public static final String ID = "id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String ADDRESS = "address";
            public static final String PHONE1 = "phone1";
            public static final String PHONE2 = "phone2";
            public static final String PHONE3 = "phone3";
            public static final String E_MAIL = "e_mail";
        }
    }

    public static final class OrderStatusTable {
        public static final String NAME = "order_status";

        public static final class Columns{
            public static final String ID = "id";
            public static final String DESCRIPTION = "descritpion";
            public static final String EDITABLE = "editable";
            public static final String PREVIOUS = "previous";
            public static final String NEXT = "next";
        }
    }

    public static final class OrdersTable{
        public static final String NAME = "order_status";

        public static final class Columns{
            public static final String ID = "id";
            public static final String STATUS_ID = "status_id";
            public static final String CUSTOMER_ID="costumer_id";
            public static final String DATE = "date";
            public static final String CHANGE_LOG="change_log";

        }
    }

    public static final class OrderAssenbliesTable{
        public static final String NAME = "order_assemblies";

        public static final class Columns{
            public static final String ID = "id";
            public static final String ASSEMBLY_ID = "assembly_id";
            public static final String QUANTITY ="qty";

        }
    }

}


