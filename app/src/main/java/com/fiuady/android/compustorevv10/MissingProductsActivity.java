package com.fiuady.android.compustorevv10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MissingProductsActivity extends AppCompatActivity {


    private RecyclerView RVMissingProducts;
    private TextView txtAssembliesId,txtProductId,txtMissingProductQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_products);


        RVMissingProducts = (RecyclerView) findViewById(R.id.rv_MissingProducts);

        List<ProductoFaltante> RealMP_List;
       final Inventory manager = new Inventory(getApplicationContext());

        ArrayList<Integer> lista = new ArrayList<Integer>();
        lista.add( manager.CantidadDeEnsamblesPorOrden(0,0));







     //// int OrderId= Integer.valueOf(spnOrders.getSelectedItem().toString());

       // txtAssembliesId.setText(String.valueOf(OrderId));





       // manager.AssembliesIDbyOrder(OrderId);
        //RealMP_List=manager.GetMissingProducts(OrderId,manager.AssembliesIDbyOrder(OrderId));


      //// LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
       //// RVMissingProducts.setLayoutManager(llm);

        ////manager.GetMissingProducts(OrderId,manager.AssembliesIDbyOrder(OrderId));


       //// final MissingProductsAdapter adapter = new MissingProductsAdapter(manager.GetMissingProducts(OrderId,manager.AssembliesIDbyOrder(OrderId)));
       //// RVMissingProducts.setAdapter(adapter);
        //Toast.makeText(getApplicationContext(),"Change",Toast.LENGTH_SHORT).show();



                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                RVMissingProducts.setLayoutManager(llm);

               // manager.GetMissingProducts(OrderId,manager.AssembliesIDbyOrder(OrderId),manager.Orders());
                manager.GetMissingProducts();

                final MissingProductsAdapter adapter = new MissingProductsAdapter( manager.GetMissingProducts());
                RVMissingProducts.setAdapter(adapter);
                //Toast.makeText(getApplicationContext();



    }
}
