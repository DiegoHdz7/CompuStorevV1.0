package com.fiuady.android.compustorevv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fiuady.android.compustorevv10.db.Inventory;

public class MissingProductsActivity extends AppCompatActivity {

    private Spinner spnOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_products);

        spnOrders = (Spinner) findViewById(R.id.spinner_orders);


        Inventory manager = new Inventory(getApplicationContext());

        manager.CreateTemporaryTable();
        spnOrders.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, manager.Orders()));
    }
}
