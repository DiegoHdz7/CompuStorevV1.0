package com.fiuady.android.compustorevv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fiuady.android.compustorevv10.db.Inventory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SalesPerMounthActivity extends AppCompatActivity {

    DateFormat df = new SimpleDateFormat("dd-MM-yy");
    Calendar cal = Calendar.getInstance();
    RecyclerView RvSales ;

    CardView cardView ;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_per_mounth);

        cardView= (CardView) findViewById(R.id.cardView);
        txt= (TextView) findViewById(R.id.rvPrecioMes);
        RvSales = (RecyclerView) findViewById(R.id.rv_SalesPerMounth);

        final Inventory manager = new Inventory(getApplicationContext());



        ArrayList<String> dates = new ArrayList<String>();
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

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
       RvSales.setLayoutManager(llm);
        final SalesPerMounthAdapter adapter = new SalesPerMounthAdapter(dates);
       RvSales.setAdapter(adapter);



      txt.setText( String.valueOf(manager.GetTotalSalesPerMounth(10)));
















    }
}
