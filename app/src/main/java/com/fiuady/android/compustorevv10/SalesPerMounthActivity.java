package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.db.Inventory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SalesPerMounthActivity extends AppCompatActivity {

    DateFormat df = new SimpleDateFormat("dd-MM-yy");
    Calendar cal = Calendar.getInstance();
    RecyclerView RvSales ;
    Spinner spnSales;

    CardView cardView ;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_per_mounth);

        cardView= (CardView) findViewById(R.id.cardView);
        txt= (TextView) findViewById(R.id.rvPrecioMes);
        RvSales = (RecyclerView) findViewById(R.id.rv_SalesPerMounth);
        spnSales = (Spinner)  findViewById(R.id.spinner_years);


        final Inventory manager = new Inventory(getApplicationContext());



       final  ArrayList<String> dates = new ArrayList<String>();
        dates.add("Enero");
        dates.add("Febrero");
        dates.add("Marzo");
        dates.add("Abril");
        dates.add("Mayo");
        dates.add("Junio");
        dates.add("Julio");
        dates.add("Agosto");
        dates.add("Septiembre");
        dates.add("Octubre");
        dates.add("Noviembre");
        dates.add("Diciembre");

        ArrayList<String> años = new ArrayList<String>();
        años.add("2016");
        años.add("2017");

        spnSales.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, años));


        //Falta filtrar por finalizado y confirmado
        //Falta filtrar por finalizado y confirmado
        //Falta filtrar por finalizado y confirmado
        //Falta filtrar por finalizado y confirmado
        //Falta filtrar por finalizado y confirmado
        //Falta filtrar por finalizado y confirmado





        spnSales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                RvSales.setLayoutManager(llm);
                final SalesPerMounthAdapter adapter = new SalesPerMounthAdapter(dates,spnSales.getSelectedItem().toString());
                RvSales.setAdapter(adapter);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(),DetailSalesResume.class);
                        Toast.makeText(getApplicationContext(),"clcick",Toast.LENGTH_SHORT).show();

                        int AdapterPosition = RvSales.getChildAdapterPosition(v);
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






      txt.setText( String.valueOf(manager.GetTotalSalesPerMounth(10,spnSales.getSelectedItem().toString())));
















    }
}
