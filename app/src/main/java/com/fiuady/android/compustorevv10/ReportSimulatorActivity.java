package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.fiuady.android.compustorevv10.db.SimulatedOrder;
import android.view.MenuInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.fiuady.android.compustorevv10.db.Customers;
import com.fiuady.android.compustorevv10.db.Inventory;

/**
 * Created by Aarón Madera on 22/04/2017.
 */

public class ReportSimulatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final static int CODE_REPORTSIMULATOR = 62;

    private class SimulatedOrderHolder extends RecyclerView.ViewHolder{
        private TextView txvFullName;
        private TextView txvOrderId;
        private TextView txvOrderStatus;
        private TextView txvOrderDate;
        private TextView txvOrderPrice;

        public SimulatedOrderHolder (View itemView)
        {
            super(itemView);
            txvFullName = (TextView) itemView.findViewById(R.id.txv_cust_fullName);
            txvOrderId = (TextView) itemView.findViewById(R.id.txv_ord_id);
            txvOrderStatus = (TextView) itemView.findViewById(R.id.txv_ord_status);
            txvOrderDate = (TextView) itemView.findViewById(R.id.txv_ord_date);
            txvOrderPrice = (TextView) itemView.findViewById(R.id.txv_ord_price);

        }

        public void binHolder (SimulatedOrder simOrd)
        {
            txvFullName.setText(simOrd.getLastName() + ',' + simOrd.getFirstName());
            txvOrderId.setText(simOrd.getId());
            txvOrderStatus.setText(simOrd.getStatus());
            txvOrderDate.setText(simOrd.getDate().toString());
            txvOrderPrice.setText(String.valueOf(simOrd.getPrice()));
        }
    }

    private class SimulatedOrderAdapter extends RecyclerView.Adapter<SimulatedOrderHolder>
    {
        private List<SimulatedOrder> _orders;
        public SimulatedOrderAdapter(List<SimulatedOrder> orders)
        {
            this._orders = orders;
        }

        public SimulatedOrder getItemOfList(int index) {return _orders.get(index); }

        @Override
        public int getItemCount() {
            return _orders.size();
        }

        @Override
        public void onBindViewHolder(SimulatedOrderHolder holder, int position) {
            holder.binHolder(_orders.get(position));
        }

        @Override
        public SimulatedOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmation_simulator_list_item,parent,false);
            SimulatedOrderHolder holder = new SimulatedOrderHolder(view);
            return holder;
        }
    }

    private Spinner spnProcess;
    private Inventory inventory;
    private RecyclerView recyclerView;
    private SimulatedOrderAdapter simulatedOrderAdapter;
    private String[] spnValues = {"Cliente","Fecha","Monto de Venta"};

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //TextView txvSelectedFilter = (TextView) view;
        if (view != null) {
            Toast.makeText(this, "A procesar por: " +  spnProcess.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orders_simulator);

        Intent i = getIntent();

        inventory = new Inventory(getApplicationContext());
        //ArrayAdapter<String> adapterProcess = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,spnValues);
        spnProcess = (Spinner) findViewById(R.id.spn_filter_process);
        spnProcess.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spnValues));

        //////////////////////////////////
        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.simulated_orders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReportSimulatorActivity.this));

        //ArrayList<SimulatedOrder> sO = new ArrayList<SimulatedOrder>();
        //simulatedOrderAdapter = new ReportSimulatorActivity.SimulatedOrderAdapter(sO);

        simulatedOrderAdapter = new SimulatedOrderAdapter(inventory.SearchOrdersByCriteria(spnProcess.getSelectedItem().toString()));
        recyclerView.setAdapter(simulatedOrderAdapter);

        ///CONFIGURAR LOS RESULTADOS PARA EL RECYCLER VIEW
        //////////////////////////////////


    }
}
