package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.fiuady.android.compustorevv10.db.SimulatedOrder;
import android.view.MenuInflater;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.fiuady.android.compustorevv10.db.Customers;
import com.fiuady.android.compustorevv10.db.Inventory;

/**
 * Created by Aarón Madera on 22/04/2017.
 */

public class ReportSimulatorActivity extends AppCompatActivity {
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

            /*Date dateObject;
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dob_var = simOrd.getDate().toString();
            dateObject = formatter.parseObject(dob_var);
            StringTokenizer st = new StringTokenizer(dob_var,"-");
            String date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
            */



            txvFullName.setText(simOrd.getLastName() + ',' + simOrd.getFirstName());
            txvOrderId.setText(String.valueOf(simOrd.getId()));
            txvOrderStatus.setText(simOrd.getStatus());
            txvOrderDate.setText(simOrd.getDate().toString());
            txvOrderPrice.setText(String.valueOf(simOrd.getPrice()));

        }
    }

    private class SimulatedOrderAdapter extends RecyclerView.Adapter<SimulatedOrderHolder> implements View.OnClickListener
    {
        private List<SimulatedOrder> _orders;
        private View.OnClickListener listener;
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
            if(_orders.get(position).getStatus().toString().equals("Pendiente"))
            {
                holder.itemView.setBackgroundColor(Color.BLUE);
            }
            else if(_orders.get(position).getStatus().toString().equals("Confirmado"))
            {
                holder.itemView.setBackgroundColor(Color.GREEN);
            }

            else if(_orders.get(position).getStatus().toString().equals("Cancelado"))
            {
                holder.itemView.setBackgroundColor(Color.RED);
            }
        }

        @Override
        public SimulatedOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmation_simulator_list_item,parent,false);
            SimulatedOrderHolder holder = new SimulatedOrderHolder(view);


            view.setOnClickListener(this);  //Para el onItemTouchListener
            return holder;
        }

        /*Para el onItemTouchListener -->*/
        @Override
        public void onClick(View v) {
            if (listener != null)
            {
                listener.onClick(v);
            }
        }

        public void SetOnClickListener(View.OnClickListener listener)
        {
            this.listener = listener;
        }

        /*<-- Para el onItemTouchListener*/

    }

    private Spinner spnProcess;
    private Inventory inventory;
    private RecyclerView recyclerView;
    private SimulatedOrderAdapter simulatedOrderAdapter;
    private String[] spnValues = {"Cliente","Fecha","Monto de Venta"};



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orders_simulator);

        Intent i = getIntent();

        inventory = new Inventory(getApplicationContext());
        //ArrayAdapter<String> adapterProcess = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,spnValues);
        spnProcess = (Spinner) findViewById(R.id.spn_filter_process);
        spnProcess.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spnValues));

        spnProcess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getApplicationContext(),"Prioridad por: " + spnProcess.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                //RecyclerView
                recyclerView = (RecyclerView) findViewById(R.id.simulated_orders_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(ReportSimulatorActivity.this));
                simulatedOrderAdapter = new SimulatedOrderAdapter(inventory.SearchOrdersByCriteria(spnProcess.getSelectedItem().toString()));
                recyclerView.setAdapter(simulatedOrderAdapter);


                simulatedOrderAdapter.SetOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                        int pos = recyclerView.getChildAdapterPosition(v);  //Obtengo la posición de la lista que se le pasó al
                                                                            //recycler view, anteriormente

                        String lName = simulatedOrderAdapter.getItemOfList(pos).getLastName();
                        String fName = simulatedOrderAdapter.getItemOfList(pos).getFirstName();
                        //Mando a llamar la misma lista que se le pasó al recyclerview y selecciono el objeto
                        //que se encuentra en la posición que se seleccionó.

                        Toast.makeText(getApplicationContext(),"Cliente seleccionado:" + lName + ", " + fName,Toast.LENGTH_SHORT).show();
                        */
                        PopupMenu pMenu = new PopupMenu(v.getContext(), v);
                        pMenu.inflate(R.menu.menu_simulated_orders);
                        pMenu.setGravity(Gravity.END);
                        pMenu.show();
                    }
                });

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////


        ///CONFIGURAR LOS RESULTADOS PARA EL RECYCLER VIEW
        //////////////////////////////////



    }
}
