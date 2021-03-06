package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.fiuady.android.compustorevv10.Productos.Product_Activity;

public class ReportsActivity extends AppCompatActivity {

    private ImageButton btnFaltantes,btnVentas, btnSimulator,btnProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        btnFaltantes=(ImageButton) findViewById(R.id.btnMissing);
        btnVentas=(ImageButton) findViewById(R.id.btnSalesPerMounth);
        btnSimulator = (ImageButton) findViewById(R.id.btnSimulator);

        btnFaltantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportsActivity.this,MissingProductsActivity.class);
                startActivity(i);
            }
        }); //

        btnSimulator.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportsActivity.this,ReportSimulatorActivity.class);
                startActivity(i);
            }
        });

        btnVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportsActivity.this,SalesPerMounthActivity.class);
                startActivity(i);
            }
        });


    }
}
