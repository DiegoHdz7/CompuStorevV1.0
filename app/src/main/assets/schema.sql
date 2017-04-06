CREATE TABLE [product_categories](
    [id] INTEGER PRIMARY KEY,
    [description] TEXT NOT NULL);

CREATE TABLE [products](
    [id] INTEGER PRIMARY KEY,
    [category_id] INTEGER NOT NULL REFERENCES product_categories([id]),
    [description] TEXT NOT NULL,
    [price] INTEGER NOT NULL,
    [qty] INTEGER NOT NULL,
    CHECK(price >= 0),
    CHECK(qty >= 0));

CREATE TABLE [assemblies](
    [id] INTEGER PRIMARY KEY,
    [description] TEXT NOT NULL);

CREATE TABLE [assembly_products](
    [id] INTEGER NOT NULL REFERENCES assemblies([id]),
    [product_id] INTEGER REFERENCES products([id]),
    [qty] INTEGER NOT NULL,
    CHECK(qty >= 0),
    UNIQUE([id], [product_id]));

CREATE TABLE [customers](
    [id] INTEGER PRIMARY KEY,
    [first_name] TEXT NOT NULL,
    [last_name] TEXT NOT NULL,
    [address] TEXT NOT NULL,
    [phone1] TEXT,
    [phone2] TEXT,
    [phone3] TEXT,
    [e_mail] TEXT);

CREATE TABLE [order_status](
    [id] INTEGER PRIMARY KEY,
    [description] INTEGER NOT NULL,
    [editable] INTEGER NOT NULL,
    [previous] TEXT NOT NULL,
    [next] TEXT NOT NULL,
    CHECK(editable = 0 OR editable = 1));

CREATE TABLE [orders](
    [id] INTEGER PRIMARY KEY,
    [status_id] INTEGER NOT NULL REFERENCES order_status([id]),
    [customer_id] INTEGER NOT NULL REFERENCES customers([id]),
    [date] TEXT NOT NULL,
    [change_log] TEXT DEFAULT NULL);

CREATE TABLE [order_assemblies](
    [id] INTEGER NOT NULL REFERENCES orders([id]),
    [assembly_id] INTEGER NOT NULL REFERENCES assemblies([id]),
    [qty] INTEGER NOT NULL,
    UNIQUE([id], [assembly_id]));