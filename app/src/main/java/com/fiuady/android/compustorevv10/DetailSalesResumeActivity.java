package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.db.Inventory;

import static com.fiuady.android.compustorevv10.DetailsPerOrderActivity.EXTRA_AÑO_A;
import static com.fiuady.android.compustorevv10.DetailsPerOrderActivity.EXTRA_Position_A;

public class DetailSalesResumeActivity extends AppCompatActivity {

    public static final String EXTRA_Position = "com.fiuady.android.compustorevv10.AdapterPosition";
    public static final String EXTRA_AÑO = "com.fiuady.android.compustorevv10.Año";
    private RecyclerView rv;
    private TextView txtPrecio,txtTag;

    private int Mes;
    private String año;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sales_resume);

        rv = (RecyclerView) findViewById(R.id.rvDetailedResume);
        txtPrecio = (TextView) findViewById(R.id.txtRealTotalPrice);
        txtTag=(TextView) findViewById(R.id.tagTotal);

        Inventory manager = new Inventory(getApplicationContext());

        final Intent i = getIntent();

          Mes= i.getIntExtra(EXTRA_Position,0);
         año= i.getStringExtra(EXTRA_AÑO);


        txtPrecio.setText(String.valueOf(Mes));
        txtTag.setText(String.valueOf(año));

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        DetailResumeAdapter adapter = new DetailResumeAdapter (manager.GetOrderInfobyMounth(Mes,año),1);
        rv.setAdapter(adapter);



        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();
                Intent b = new Intent(DetailSalesResumeActivity.this,DetailsPerOrderActivity.class);
                b.putExtra(DetailsPerOrderActivity.EXTRA_Position_A,Mes);
                b.putExtra(DetailsPerOrderActivity.EXTRA_AÑO_A,año);
                startActivity(b);


            }
        });





    }
}
