package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.fiuady.android.compustorevv10.db.Inventory;

public class DetailsPerOrderActivity extends AppCompatActivity {

    public static final String EXTRA_Position_A = "com.fiuady.android.compustorevv10.AdapterPosition_A";
    public static final String EXTRA_AÑO_A = "com.fiuady.android.compustorevv10.Año_A";

    private RecyclerView rv;
    private TextView txtCId, txtDate, txtfstName, txtscnName, txtOId,txtOS,txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_per_order);



        rv= (RecyclerView) findViewById(R.id.rvAssemblies);

        txtCId = (TextView) findViewById(R.id.txtCustomerId_o);
        txtDate = (TextView) findViewById(R.id.txtDate_o);
        txtfstName =(TextView) findViewById(R.id.txtfstName_o);
        txtscnName = (TextView) findViewById(R.id.txtLstName_o);
        txtOId = (TextView) findViewById(R.id.txtOrderId_o);
        txtOS = (TextView) findViewById(R.id.txtOrderStatus_o);
        txtAddress = (TextView) findViewById(R.id.txtAddress_o);
        Intent z = getIntent();

        int mes = z.getIntExtra(EXTRA_Position_A,0);
        String año = z.getStringExtra(EXTRA_AÑO_A);

        Inventory manager = new Inventory(getApplicationContext());





        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        /*
        DetailResumeAdapter adapter = new DetailResumeAdapter(); //(manager.GetDetailInfo(mes,año),2);
        rv.setAdapter(adapter);


        //adapter.GetItemOfList(mes)
        txtCId.setText(String.valueOf(mes));

        txtCId.setText(String.valueOf( adapter.GetItemOfList(0).getClientId()));
       txtDate.setText(String.valueOf( adapter.GetItemOfList(0).getDate()));
        txtfstName.setText( String.valueOf(adapter.GetItemOfList(0).getFirstName())+" ");
        txtscnName.setText( String.valueOf(adapter.GetItemOfList(0).getLastName()));
        txtOId.setText(String.valueOf( adapter.GetItemOfList(0).getOrderId()));
        txtOS.setText(String.valueOf( adapter.GetItemOfList(0).getStatus_Description()));
        txtAddress.setText(adapter.GetItemOfList(0).getAddress());
        */
    }
}
