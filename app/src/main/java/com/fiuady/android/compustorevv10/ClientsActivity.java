package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.Productos.DialogAlerta;
import com.fiuady.android.compustorevv10.Productos.Product_Activity;
import com.fiuady.android.compustorevv10.Productos.RecyclerItemOnClickListener;
import com.fiuady.android.compustorevv10.Productos.UpdateActivity;
import com.fiuady.android.compustorevv10.db.ClientsFilters;

import com.fiuady.android.compustorevv10.db.Customers;
import com.fiuady.android.compustorevv10.db.Inventory;



import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ClientsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MultiselectionSpinner.OnMultipleItemsSelectedListener{

    public static final int CODE_CLIENTS = 4;
    //Agregar Enumeración enum enFilter {fName("first_name",0), lName(lN,"last_name")};


    private static ClientsFilters[] filterValues = {ClientsFilters.LAST_NAME, ClientsFilters.FIRST_NAME,ClientsFilters.ADDRESS,
            ClientsFilters.PHONE,ClientsFilters.E_MAIL};
    //private String[] filterValues = {cf_lN.toString(), cf_fN.toString(), cf_ad.toString(), cf_ph.toString(),cf_em.toString()};
    private Spinner spnFilter;
    private Inventory inventory;
    private EditText edtSearch;

    private final String KEY_EDIT_TEXT_SEARCH="key_edtSearch";
    private final String KEY_SPINNER_FILTERS="key_spnFilters";


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView txvSelectedFilter = (TextView) view;
        if (view != null) {
            Toast.makeText(this, "Filtrado por: " + txvSelectedFilter.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*Clases para implementar el recycler view -->*/

    private class CustomerHolder extends RecyclerView.ViewHolder{

        private TextView txtFirstName;
        private TextView txtLastName;
        private TextView txtAddress;
        private TextView txtPhone1;
        private TextView txtPhone2;
        private TextView txtPhone3;
        private TextView txtEMail;

        public CustomerHolder (View itemView){
            super(itemView);
            txtFirstName = (TextView)itemView.findViewById(R.id.cLI_fName_text);
            txtLastName = (TextView)itemView.findViewById(R.id.cLI_lName_text);
            txtAddress = (TextView)itemView.findViewById(R.id.cLI_ad_text);
            txtPhone1 = (TextView)itemView.findViewById(R.id.cLI_ph1_text);
            txtPhone2 = (TextView)itemView.findViewById(R.id.cLI_ph2_text);
            txtPhone3 = (TextView)itemView.findViewById(R.id.cLI_ph3_text);
            txtEMail = (TextView)itemView.findViewById(R.id.cLI_em_text);
        }

        public void bindHolder (Customers customer)
        {
            txtFirstName.setText(customer.getFirstName());
            txtLastName.setText(customer.getLastName());
            txtAddress.setText(customer.getAddress());
            txtPhone1.setText(customer.getPhone1());
            txtPhone2.setText(customer.getPhone2());
            txtPhone3.setText(customer.getPhone3());
            txtEMail.setText(customer.geteMail());
        }
    }

    private class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder>
    {
        private List<Customers> _customers;

        public CustomerAdapter(List<Customers> customers){this._customers = customers;}

        public Customers getItemOfList(int index)
        {
            return _customers.get(index);
        }


        @Override
        public int getItemCount() {
            return _customers.size();
        }

        @Override
        public void onBindViewHolder(CustomerHolder holder, int position) {
            holder.bindHolder(_customers.get(position));
        }

        @Override
        public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.customer_list_item,parent,false);
            return new CustomerHolder(view);
        }
    }

    /*<-- Clases para implementar el recycler view*/


    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    private boolean searched=false;
    private final String KEY_SEARCHED="key_boolSearched";



    public int getIntOfClientFilter(String str)
    {
        if (str == "Nombre") {return 1;}
        else if (str == "Apellido") {return 0;}
        else if (str == "Dirección") {return 2;}
        else if (str == "Teléfono") {return 3;}
        else if (str == "Correo") {return 4;}
        else {return -1;}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //MultiSpinner
        setContentView(R.layout.activity_main);
        String[] array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        MultiselectionSpinner multiSelectionSpinner = (MultiselectionSpinner) findViewById(R.id.msp);
        multiSelectionSpinner.setItems(array);

        multiSelectionSpinner.setSelection(new int[]{2, 6});
        multiSelectionSpinner.setListener(this);
        */

        setContentView(R.layout.activity_clients);
        /*Configuración del intent*/
        Intent i = getIntent();

        inventory = new Inventory(getApplicationContext());


        /*Configuración de la ToolBar*/
        Toolbar clientsToolbar = (Toolbar) findViewById(R.id.tlb_clients);

        setSupportActionBar(clientsToolbar);
        //getSupportActionBar();

        //Configuración del Spinner
        spnFilter = (Spinner) findViewById(R.id.spn_filter);
        ImageButton imbSearch = (ImageButton) findViewById(R.id.imb_search);
        edtSearch = (EditText) findViewById(R.id.etx_search);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterFilters = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked);
        spnFilter.setAdapter(adapterFilters);
        // Specify the layout to use when the list of choices appears
        //adapterFilters.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        for (int ii=0; ii< filterValues.length; ii++)
        {
            adapterFilters.add(filterValues[ii].toString());
        }
        // Apply the adapter to the spinner

        spnFilter.setAdapter(adapterFilters);
        spnFilter.setOnItemSelectedListener(this);


        //////////////////////////////////
        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.customers_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClientsActivity.this));

        ArrayList<Customers> l = new ArrayList<Customers>();
        customerAdapter = new CustomerAdapter(l);

        //ArrayList<Customers> l = new ArrayList<Customers>();

        recyclerView.setAdapter(customerAdapter);
        //////////////////////////////////

        /*
        //Let's declare and initialize the recycler view inside the listener for clicking
        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.customers_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        */

        if (savedInstanceState!= null)
        {
            spnFilter.setSelection(savedInstanceState.getInt(KEY_SPINNER_FILTERS));
            edtSearch.setText(savedInstanceState.getString(KEY_EDIT_TEXT_SEARCH));
            searched=savedInstanceState.getBoolean(KEY_SEARCHED,false);

            //This isn't going to be necessary, taking into account that the reccler view does not lose
            //his values when there is a change from portrait to landscape and vice versa.
            if(searched)
            {
                customerAdapter = new CustomerAdapter(inventory.searchCustomersByFilter(savedInstanceState.getInt(KEY_SPINNER_FILTERS),
                        savedInstanceState.getString(KEY_EDIT_TEXT_SEARCH)));
                recyclerView.setAdapter(customerAdapter);
            }

        }


        //Identificar qué filtros se tienen seleccionados
        //spnFilter.getSelectedItemId();
        //public List<Customers> searchCustomersByFilter(int intFilter, String criteria)



        imbSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //RecyclerView
                recyclerView = (RecyclerView) findViewById(R.id.customers_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClientsActivity.this));

                customerAdapter = new CustomerAdapter(inventory.searchCustomersByFilter(
                        getIntOfClientFilter(spnFilter.getSelectedItem().toString()),
                        String.valueOf(edtSearch.getText().toString()))
                );

                //ArrayList<Customers> l = new ArrayList<Customers>();

                recyclerView.setAdapter(customerAdapter);
                //setResult(RESULT_OK);
//setResult(RESULT_OK);
                searched = customerAdapter.getItemCount() > 0;
            }
        });




        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(ClientsActivity.this, recyclerView, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(final View v, final int position) {

                final PopupMenu popmenu = new PopupMenu(v.getContext(), v);
                popmenu.inflate(R.menu.menu_clients_pop);
                popmenu.show();
                int AdapterPos = recyclerView.getChildAdapterPosition(v);

                final int ClientId = customerAdapter.getItemOfList(AdapterPos).getId();
                final  String FN = customerAdapter.getItemOfList(AdapterPos).getFirstName();
                final  String LN = customerAdapter.getItemOfList(AdapterPos).getLastName();
                final  String AD = customerAdapter.getItemOfList(AdapterPos).getAddress();
                final  String EM = customerAdapter.getItemOfList(AdapterPos).geteMail();

                final String LD1;
                final String N1;
                final String LD2;
                final String N2;
                final String LD3;
                final String N3;






               /* StringTokenizer ts3 = new StringTokenizer(customerAdapter.getItemOfList(AdapterPos).getPhone3(),"-");

                final String LD3 = ts3.nextToken();
                final String N3 = ts3.nextToken();*/






                popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()) {

                            case R.id.update_client:
                                Intent i = new Intent(ClientsActivity.this,UpdateClientActivity.class);
                                i.putExtra(UpdateClientActivity.EXTRA_CLIENT_ID_TO_UP,ClientId );
                                i.putExtra(UpdateClientActivity.EXTRA_FIRST_NAME_CLIENT,FN );
                                i.putExtra(UpdateClientActivity.EXTRA_LAST_NAME_CLIENT,LN );
                                i.putExtra(UpdateClientActivity.EXTRA_ADDRRES_CLIENT,AD );
                                i.putExtra(UpdateClientActivity.EXTRA_EMAIL_CLIENT,EM );
                               // i.putExtra(UpdateClientActivity.EXTRA_LADA1,LD1);
                                //i.putExtra(UpdateClientActivity.EXTRA_PHONE1,N1);

                               /* i.putExtra(UpdateClientActivity.EXTRA_LADA2,LD2);
                                i.putExtra(UpdateClientActivity.EXTRA_PHONE2,N2);
                                i.putExtra(UpdateClientActivity.EXTRA_LADA3,LD3);
                                i.putExtra(UpdateClientActivity.EXTRA_PHONE3,N3);*/
                               // startActivity(i);

                                startActivityForResult(i,UpdateClientActivity.CODE_UPDATE_CUSTOMER);
                                return true;


                            case R.id.delete_client:
                                Inventory manager = new Inventory(ClientsActivity.this);

                                if(manager.CheckDelete(ClientId)==true) {
                                    manager.DeleteCustomer(ClientId);
                                    customerAdapter = new CustomerAdapter(inventory.searchCustomersByFilter(
                                            getIntOfClientFilter(spnFilter.getSelectedItem().toString()),
                                            String.valueOf(edtSearch.getText().toString()))
                                    );
                                    recyclerView.setAdapter(customerAdapter);
                                }

                                else Toast.makeText(getApplicationContext(),"Orden en proceso, no se puede eliminar",Toast.LENGTH_SHORT).show();

                                return true;

                            default:
                                return false;
                        }


                    }
                });


            }

            @Override
            public void onLongItemClick(View v, int position) {


            }
        }));



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_EDIT_TEXT_SEARCH,spnFilter.getSelectedItemPosition());
        Log.i("SpinnerValue=",String.valueOf(spnFilter.getSelectedItemPosition()));
        outState.putString(KEY_EDIT_TEXT_SEARCH,edtSearch.getText().toString());
        outState.putBoolean(KEY_SEARCHED,searched);
        Log.i("Searched.boolean.value=",String.valueOf(searched));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_clients, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_clients_add:
                Intent j = new Intent(ClientsActivity.this, NewClientActivity.class);
                startActivityForResult(j, NewClientActivity.CODE_NEWCLIENT);
                return true;
            case R.id.action_clients_close:
                finish();
                return true;

            case R.id.update_client:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(),"Se agregó el producto",Toast.LENGTH_SHORT).show();
            customerAdapter = new CustomerAdapter(inventory.searchCustomersByFilter(
                    getIntOfClientFilter(spnFilter.getSelectedItem().toString()),
                    String.valueOf(edtSearch.getText().toString()))
            );
            recyclerView.setAdapter(customerAdapter);
        }
    }
}



