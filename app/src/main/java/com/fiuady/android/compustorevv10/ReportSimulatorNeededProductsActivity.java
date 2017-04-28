package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.db.Inventory;
import com.fiuady.android.compustorevv10.db.SimProductsNeeded;
import com.fiuady.android.compustorevv10.db.SimulatedOrder;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by electronica on 28/04/2017.
 */

public class ReportSimulatorNeededProductsActivity extends AppCompatActivity {
//public static final String EXTRA_QUESTION_ID = "com.fiuady.android.testapplication.question_id";
    public static final String EXTRA_ORDER_ID = "com.fiuady.android.compustorevv10.ReportSimulatorNeededProductsActivity.extra_order_id";
    public static final int CODE_NEEDED_PRODUCTS = 117;

    private class ReportSimulatorNeededProductsHolder extends RecyclerView.ViewHolder{
        private TextView txvOrderId;
        private TextView txvProductShortDescription;
        private TextView txvNeededQuantity;

        public ReportSimulatorNeededProductsHolder(View itemView)
        {
            super(itemView);


            txvOrderId = (TextView) itemView.findViewById(R.id.txv_id);
            txvProductShortDescription = (TextView) itemView.findViewById(R.id.txv_desc);
            txvNeededQuantity = (TextView) itemView.findViewById(R.id.txv_needed_qty);
        }

        public void bindNeededProducts (SimProductsNeeded spn)
        {
            Inventory inv = new Inventory(getApplicationContext());

            txvOrderId.setText(String.valueOf(spn.getProductId()));
            String shortDesc = inv.getASpecificProductById(spn.getProductId()).getDescription().substring(0,20);
            txvProductShortDescription.setText(shortDesc);
            txvNeededQuantity.setText(String.valueOf(spn.getQtyNeeded()*(-1)));
        }
    }

    private class NeededProductsAdapter extends RecyclerView.Adapter<ReportSimulatorNeededProductsHolder>
    {
        private List<SimProductsNeeded> _simProdNeed;

        public NeededProductsAdapter(List<SimProductsNeeded> simProdNeed)
        {
            this._simProdNeed = simProdNeed;
        }

        @Override
        public int getItemCount() {
            return _simProdNeed.size();
        }

        @Override
        public void onBindViewHolder(ReportSimulatorNeededProductsHolder holder, int position) {
            holder.bindNeededProducts(_simProdNeed.get(position));
        }

        @Override
        public ReportSimulatorNeededProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.neede_products_list_item, parent, false);
            return new ReportSimulatorNeededProductsHolder(view);
        }
    }


    private TextView txvOrderSimulatedId; // = (TextView) findViewById(R.id.txv_order_id);
    private TextView txvCustomerFullName; // = (TextView) findViewById(R.id.txv_order_customer_fN);
    private TextView txvOrderStatus; // = (TextView) findViewById(R.id.txv_order_status);
    private RecyclerView recyclerView;
    private NeededProductsAdapter adapter;
    private Inventory inventory;
    private int orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_needed_products);

        Intent i = getIntent();
        ///
        orderId =  i.getIntExtra("OrderIdUnicoYDetergente",0);


        Toast.makeText(getApplicationContext(),String.valueOf(orderId),Toast.LENGTH_SHORT).show();

        SimulatedOrder simulatedOrder;

        inventory = new Inventory(getApplicationContext());
        txvOrderSimulatedId = (TextView) findViewById(R.id.txv_order_id);
        txvCustomerFullName = (TextView) findViewById(R.id.txv_order_customer_fN);
        txvOrderStatus = (TextView) findViewById(R.id.txv_order_status);



        inventory = new Inventory(getApplicationContext());

        simulatedOrder = inventory.getSimulatedOrderById(orderId);

        txvOrderSimulatedId.setText(String.valueOf(simulatedOrder.getId()));
        txvCustomerFullName.setText(simulatedOrder.getLastName() + ", " + simulatedOrder.getFirstName());
        txvOrderStatus.setText(simulatedOrder.getStatus());

        //SimProductsNeeded
        //Implementar RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.needed_products_recycler_view);
        recyclerView.setLayoutManager( new LinearLayoutManager(ReportSimulatorNeededProductsActivity.this));
        adapter = new NeededProductsAdapter(inventory.GetInformationAboutNeededProducts(orderId));
        recyclerView.setAdapter(adapter);

    }
}
