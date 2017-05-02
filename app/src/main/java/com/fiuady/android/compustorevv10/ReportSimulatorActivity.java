package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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
    private final String KEY_BOOLEAN_IS_STARTED = "key_isStarted";
    private final String KEY_STRING_PREVIOUS_SPINNER_ITEM = "key_previousSpinnerItem";
    private final String KEY_STRING_ACTUAL_SPINNER_ITEM = "key_actualSpinnerItem";

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



            txvFullName.setText(simOrd.getLastName() + ", " + simOrd.getFirstName());
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





            if(_orders.get(position).getCanBeTaken() == 0) // 0 = no se tienen productos para hacer la orden
            {
                holder.itemView.setBackgroundColor(Color.RED);
            }
            else if(_orders.get(position).getCanBeTaken() == 1) // 1 = faltan algunos productos para hacer la orden
            {
                holder.itemView.setBackgroundColor(Color.YELLOW);
            }

            else if(_orders.get(position).getCanBeTaken() == 2) // 2 = se tienen todos los productos para hacer la orden
            {
                holder.itemView.setBackgroundColor(Color.CYAN);
            }

            if(_orders.get(position).getStatus().equals("Cancelado")) {
                holder.itemView.setBackgroundColor(Color.LTGRAY);
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

    private Boolean isStarted = false;
    private Boolean isCanceled = false;
    private String previousSpinerItem = null;
    private String  actualSpinnerItem = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orders_simulator);

        Intent i = getIntent();
        inventory = new Inventory(getApplicationContext());
        spnProcess = (Spinner) findViewById(R.id.spn_filter_process);
        spnProcess.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spnValues));

        if (savedInstanceState != null)
        {
            //Extraer el booleano isStarted;
            isStarted = savedInstanceState.getBoolean(KEY_BOOLEAN_IS_STARTED);
            previousSpinerItem = savedInstanceState.getString(KEY_STRING_PREVIOUS_SPINNER_ITEM);
            actualSpinnerItem = savedInstanceState.getString(KEY_STRING_ACTUAL_SPINNER_ITEM);

        }
        if (isStarted != true)
        {
            Toast.makeText(getApplicationContext(),"Esto sólo debe de salir una vez", Toast.LENGTH_SHORT).show();
            inventory.SetVirtualTableToSimulate();
          //  inventory.SetVirtualTableOfProducts();
            //inventory.SetNeededProductsInSimulation(spnProcess.getSelectedItem().toString());
            isStarted = true;
        }
        //inventory.SetVirtualTableToSimulate();
        //inventory.SetVirtualTableOfProducts();

//        inventory.SetVirtualTableToSimulate(); //Al parecer siempre se tiene que crear la tabla antes de cualquier movimiento


        //ArrayAdapter<String> adapterProcess = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,spnValues);
        spnProcess = (Spinner) findViewById(R.id.spn_filter_process);
        spnProcess.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,spnValues));



        spnProcess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, final int position, long id) {

                actualSpinnerItem = spnProcess.getSelectedItem().toString();

                if (!actualSpinnerItem.equals(previousSpinerItem)) {
                    Toast.makeText(getApplicationContext(), "Prioridad por: " + spnProcess.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    previousSpinerItem = actualSpinnerItem;
                }

                inventory.SetNeededProductsInSimulation(spnProcess.getSelectedItem().toString());

                //RecyclerView
                recyclerView = (RecyclerView) findViewById(R.id.simulated_orders_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(ReportSimulatorActivity.this));
                ArrayList<SimulatedOrder> listToRecyclerView= inventory.SearchTemporaryOrdersByCriteria(spnProcess.getSelectedItem().toString());
                simulatedOrderAdapter = new SimulatedOrderAdapter(listToRecyclerView);
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
                            final PopupMenu pMenu = new PopupMenu(v.getContext(), v);
                            pMenu.inflate(R.menu.menu_simulated_orders);
                            pMenu.setGravity(Gravity.END);
                            //pMenu.dismiss();
                            pMenu.show();

                            final int pos = recyclerView.getChildAdapterPosition(v);

                            pMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if (inventory.orderStatus(simulatedOrderAdapter.getItemOfList(pos).getId()).equals("Cancelado")) {
                                        isCanceled = true;
                                    } else {
                                        isCanceled = false;
                                    }

                                    if (item.getTitle().equals("Cancelar Orden")) {
                                        if (isCanceled) {
                                            Toast.makeText(getApplicationContext(), "Orden " + String.valueOf(simulatedOrderAdapter.getItemOfList(pos).getId()) + ", actualemente cancelada", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String ord_id = String.valueOf(simulatedOrderAdapter.getItemOfList(pos).getId());

                                            inventory.CancelOrderInVirtualTableContent(simulatedOrderAdapter.getItemOfList(pos).getId());
                                            //simulatedOrderAdapter.getItemOfList(pos).setStatus("Cancelado");
                                            Toast.makeText(getApplicationContext(), "¡Orden " + ord_id + ", Cancelada!", Toast.LENGTH_SHORT).show();
                                            //simulatedOrderAdapter = new SimulatedOrderAdapter(inventory.SearchTemporaryOrdersByCriteria(spnProcess.getSelectedItem().toString()));
                                            //recyclerView.setAdapter(simulatedOrderAdapter);
                                        }
                                        return false;
                                    } else if (item.getTitle().equals("Confirmar Orden")) {

                                        String ord_id = String.valueOf(simulatedOrderAdapter.getItemOfList(pos).getId());
                                        Toast.makeText(getApplicationContext(), "¡Orden " + ord_id + ", Confirmada!", Toast.LENGTH_SHORT).show();
                                        return false;
                                    } else if (item.getTitle().equals("Detalles de la Orden")) {

                                        //Intent i = new Intent(TestActivity.this, CheatActivity.class);
                                        //i.putExtra(CheatActivity.EXTRA_QUESTION_ID, questions[counter].getResId());


                                        String ord_id = String.valueOf(simulatedOrderAdapter.getItemOfList(pos).getId());

                                        Intent i = new Intent(ReportSimulatorActivity.this, ReportSimulatorNeededProductsActivity.class);
                                        i.putExtra("OrderIdUnicoYDetergente", simulatedOrderAdapter.getItemOfList(pos).getId());

                                        startActivityForResult(i, ReportSimulatorNeededProductsActivity.CODE_NEEDED_PRODUCTS);

                                        Toast.makeText(getApplicationContext(), "Detalles de la orden: " + ord_id, Toast.LENGTH_SHORT).show();
                                        return false;
                                    } else {
                                        return false;
                                    }
                                }
                            });
                        }
                    });


            }
                @Override
                public void onNothingSelected (AdapterView < ? > parent){

                }

        });

        ////////////////////////////////////


        ///CONFIGURAR LOS RESULTADOS PARA EL RECYCLER VIEW
        //////////////////////////////////



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_BOOLEAN_IS_STARTED,isStarted);
        outState.putString(KEY_STRING_PREVIOUS_SPINNER_ITEM, previousSpinerItem);
        outState.putString(KEY_STRING_ACTUAL_SPINNER_ITEM, actualSpinnerItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("IntentOf:/", "onDestroy: ");
        //Toast.makeText(getApplicationContext(), "Intetn info; " + getIntent().getScheme(), Toast.LENGTH_SHORT).show();
    }

}

