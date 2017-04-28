package com.fiuady.android.compustorevv10;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddOrder_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, MyNumberPickerDialog.NoticeDialogListener,DialogOnClickListener{
    private static final int CODE_ADD=1;
    private static final int CODE_CANCEL=2;
    private static final int CODE_BACKPRESSED=3;
    private Inventory inventory;
    private Spinner CustomersSpinner;

    private Button btn_saveOrder;
    private Button btn_cancelOrder;
    private int customerId;
    private int assemblyId;
    private Order order;
    private int orderId;
    private static final String TAG="tag_modifyQuantity";
    private static final String TAG2="tag_deleteQuantity";
    private static final String TAG3="tag_cancelActivity";
    private static final String TAG4="tag_backPressActivity";


    private static final String SAVE_ASSEMBLY_ID="assembly_Id";
    private static final String SAVE_ORDER_ID="order_id";
    private static final String SAVE_ORDER_ASSEMBLY_ID="order_assembly_id";
    private static final String SAVE_SELETED_POSITION="item_selected";
    private static final String SAVE_STATE_RECYCLER="save_state_recycler";

    private class OrderAssemblyHolder extends RecyclerView.ViewHolder{
        private TextView txtAssemblyName;
        private ImageView imageOptions;
        private TextView txtAssemblyId;
        private TextView txtQuantity;

        public OrderAssemblyHolder(View itemView){
            super(itemView);
            txtAssemblyName = (TextView)itemView.findViewById(R.id.assemblyName_description);
            txtAssemblyId=(TextView)itemView.findViewById(R.id.assemblyId_description);
            imageOptions = (ImageView)itemView.findViewById(R.id.assemblyOrdersOption_View);
            txtQuantity=(TextView)itemView.findViewById(R.id.assemblyQty_description);
        }
        public void bindOrderAssembly(Order_Assembly order_assembly)
        {
            txtAssemblyId.setText(String.valueOf(order_assembly.getAssembly_Id()));
            txtAssemblyName.setText(order_assembly.getAssembly_Description());
            txtQuantity.setText(String.valueOf(order_assembly.getQuantity()));
        }
    }
    private class OrderAssemblyAdapter extends RecyclerView.Adapter<OrderAssemblyHolder>{
        private List<Order_Assembly> order_assemblies;
        public OrderAssemblyAdapter(List<Order_Assembly>order_assemblies){
            this.order_assemblies = order_assemblies;
        }

        @Override
        public OrderAssemblyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.assemblyoforder_item,parent,false);
            return new OrderAssemblyHolder(view);
        }

        @Override
        public void onBindViewHolder(OrderAssemblyHolder holder, int position) {
            holder.bindOrderAssembly(order_assemblies.get(position));
        }

        @Override
        public int getItemCount() {
            return order_assemblies.size();
        }

        public List<Order_Assembly> getOrder_assemblies() {
            return order_assemblies;
        }
    }
    private RecyclerView recyclerView;
    private OrderAssemblyAdapter adapter;
    private ImageView imageView;
    private Order_Assembly order_assembly;
    private int order_assemblyId;
    private int selectedPosition;
    private Parcelable recyclerViewState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_AddOrder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.add_order_window);
        inventory = new Inventory(getApplicationContext());
        CustomersSpinner = (Spinner)findViewById(R.id.spinner_AddOrder);//customer spinner
        CustomersSpinner.setOnItemSelectedListener(this);//to execute the event
        recyclerView = (RecyclerView) findViewById(R.id.addOrder_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_saveOrder = (Button)findViewById(R.id.button_SaveOrder);
        btn_cancelOrder= (Button)findViewById(R.id.button_CancelOrder);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,getAllListOfCustomers(inventory.getAllCustomerSortedByName()));
        //ArrayAdapter<String> adapter =  new ArrayAdapter(this,getAllListOfCustomers(inventory.getAllCustomers()),android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CustomersSpinner.setAdapter(spinnerAdapter);
        if(savedInstanceState != null)
        {
            assemblyId = savedInstanceState.getInt(SAVE_ASSEMBLY_ID);
            orderId = savedInstanceState.getInt(SAVE_ORDER_ID);
            order_assemblyId = savedInstanceState.getInt(SAVE_ORDER_ASSEMBLY_ID);
            selectedPosition = savedInstanceState.getInt(SAVE_SELETED_POSITION);
            recyclerViewState = savedInstanceState.getParcelable(SAVE_STATE_RECYCLER);
            CustomersSpinner.setSelection(selectedPosition);
            List<Order_Assembly>order_assembliesList= inventory.getAllAssembliesOfOrdersById(orderId);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            adapter = new OrderAssemblyAdapter(order_assembliesList);
            recyclerView.setAdapter(adapter);
        }
        else {
           // CustomersSpinner.setAdapter(spinnerAdapter);//set the adapter
            customerId = getTheCustomerId(CustomersSpinner.getSelectedItem().toString());//get the Id of the customer
            //  Calendar calendar = Calendar.getInstance();
            //  SimpleDateFormat df= new SimpleDateFormat("dd-MM-yyyy");
            //  String formattedDate = df.format(calendar);
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());//get the current date in the specific format
            orderId = inventory.getLastIdOfOrder() + 1;//The currentId plus one
            String temporalLastName = inventory.getLastNameOfCustomer(customerId);
            String temporalFirstName = inventory.getFirstNameOfCustomer(customerId);
            order = new Order(orderId, 0, "Pendiente", customerId, temporalFirstName, temporalLastName, formattedDate, null);
            inventory.InsertNewOrder(order);
        }






        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(AddOrder_Activity.this, recyclerView, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                order_assembly=adapter.getOrder_assemblies().get(position);//get the object of the item selected
                order_assemblyId = order_assembly.getAssembly_Id();
                imageView = (ImageView)view.findViewById(R.id.assemblyOrdersOption_View);
                imageView.setOnClickListener(AddOrder_Activity.this);


            }

            @Override
            public void onLongItemClick(View view, int position) {

                order_assembly=adapter.getOrder_assemblies().get(position);
                order_assemblyId = order_assembly.getAssembly_Id();
                imageView = (ImageView)view.findViewById(R.id.assemblyOrdersOption_View);
                imageView.setOnClickListener(AddOrder_Activity.this);
            }
        }));

        // order = new Order();
        btn_saveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"save",Toast.LENGTH_SHORT).show();
                List<Order_Assembly>order_assembliesList= inventory.getAllAssembliesOfOrdersById(orderId);
                if(order_assembliesList.size()==0)
                {
                    Toast.makeText(getApplicationContext(),"There are not assemblies in the order",Toast.LENGTH_SHORT).show();
                }
                else
                {   //Update the assembly
                    int NewCustomerId = getTheCustomerId(CustomersSpinner.getSelectedItem().toString());//this is a reliable method
                    String CustomerLastName =inventory.getLastNameOfCustomer(NewCustomerId);
                    String CustomerFirstName = inventory.getFirstNameOfCustomer(NewCustomerId);
                    String date =new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                    Order NewOrder = new Order(orderId,0,"Pendiente",NewCustomerId,CustomerFirstName,CustomerLastName,date,null);
                    UpdateOrderDatabase(NewOrder);
                    final Intent intent = new Intent();
                    setResult(AppCompatActivity.RESULT_OK, intent);
                    finish();

                }
            }
        });
        btn_cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = fragmentManager.findFragmentByTag(TAG3);
                if (frag != null) {
                    fragmentManager.beginTransaction().remove(frag).commit();
                }
                MyGeneralDialog myGeneralDialog= MyGeneralDialog.newInstance("Cancel","Are you sure you want to cancel?, all the non-saved info will be deleted",CODE_CANCEL);
                myGeneralDialog.show(fragmentManager, TAG3);


            }
        });
    }
    private void UpdateOrderDatabase(Order order)
    {
       inventory.UpdateOrder(order);
    }
    @Override
    public void onClick(View v) {//OnClick the image to show options
       PopupMenu popupMenu;//create a new popup menu
        popupMenu = new PopupMenu(AddOrder_Activity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_modify_orderassemblies,popupMenu.getMenu());
        PopupItemClickListener(popupMenu);
        popupMenu.show();
    }
    private void PopupItemClickListener(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.option_Modify:
                        //Toast.makeText(getApplicationContext(), "hello",Toast.LENGTH_SHORT).show();
                        int quantity = inventory.VerifyTheQuantityOfAssembly(order_assemblyId ,orderId);
                        if (quantity <=100) {
                            //private Parcelable recyclerViewState;
                            recyclerViewState= recyclerView.getLayoutManager().onSaveInstanceState();
                            FragmentManager fragmentManager = getFragmentManager();
                            Fragment frag = fragmentManager.findFragmentByTag(TAG);
                            if (frag != null) {
                                fragmentManager.beginTransaction().remove(frag).commit();
                            }
                            MyNumberPickerDialog myNumberPickerDialog = MyNumberPickerDialog.newInstance(quantity);
                            myNumberPickerDialog.show(fragmentManager, TAG);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"We do not have such quantity", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.option_Delete:
                       // Toast.makeText(getApplicationContext(), "hola",Toast.LENGTH_SHORT).show();
                        recyclerViewState= recyclerView.getLayoutManager().onSaveInstanceState();
                        FragmentManager fragmentManager = getFragmentManager();
                        Fragment frag = fragmentManager.findFragmentByTag(TAG2);
                        if (frag != null) {
                            fragmentManager.beginTransaction().remove(frag).commit();
                        }
                        MyGeneralDialog myGeneralDialog= MyGeneralDialog.newInstance("Delete assembly", "Are you sure you want to delete the assembly?",0);
                        myGeneralDialog.show(fragmentManager, TAG2);
                        break;

                }
                return true;
            }
        });

    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment,int code) {
        switch (code) {
            case 0://this is for delete
                RemoveOrderAssemblyDatabase(order_assemblyId);
                //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                adapter = new OrderAssemblyAdapter(inventory.getAllAssembliesOfOrdersById(orderId));
                recyclerView.setAdapter(adapter);
                break;
            case CODE_CANCEL:
                inventory.DeleteAllOrderAssemblyByOrderId(orderId);//to get back to the previous Activity
                inventory.DeleteOrder(orderId);
                final Intent intent = new Intent();
                setResult(AppCompatActivity.RESULT_OK, intent);
                this.finish();
                break;
            case CODE_BACKPRESSED:
                inventory.DeleteAllOrderAssemblyByOrderId(orderId);
                inventory.DeleteOrder(orderId);
                final Intent NewIntent = new Intent();
                setResult(AppCompatActivity.RESULT_OK, NewIntent);
                this.finish();
                break;


        }
    }
    private void RemoveOrderAssemblyDatabase(int order_assemblyId) {
        String assemblyDescription = inventory.getAssemblyDescriptionById(order_assemblyId);//get the description
        Order_Assembly order_assembly = new Order_Assembly(orderId,order_assemblyId,assemblyDescription,1);//create new order_assembly
        inventory.DeleteOrderAssembly(order_assembly);                                              //the quantity does not matter because all the assembly will be delete

    }

        @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment, int code)
    {
        //justtoOverride
    }

    public void onDialogPositiveClick(int newQuantity)
    {

        setNewQuantityOfAssemblyDatabase(newQuantity);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        adapter = new OrderAssemblyAdapter(inventory.getAllAssembliesOfOrdersById(orderId));
        recyclerView.setAdapter(adapter);


        //Toast.makeText(getApplicationContext(),String.valueOf(newQuantity),Toast.LENGTH_SHORT).show();

    }
    private void setNewQuantityOfAssemblyDatabase(int newquantity)
    {
        String assemblyDescription = inventory.getAssemblyDescriptionById(order_assemblyId);//get the description
        Order_Assembly order_assembly = new Order_Assembly(orderId,order_assemblyId,assemblyDescription,newquantity);//create new order_assembly
        inventory.UpdateNewOrderAssembly(order_assembly);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addneworder,menu);

        return super.onCreateOptionsMenu(menu);
    }
    private ArrayList<String> getAllListOfCustomers(List<Customer> customers)
    {
        ArrayList<String> list = new ArrayList<String>();
        for(Customer customer:customers)
        {
            list.add(customer.getFirst_Name().trim()+ " " + customer.getLast_Name().trim());
        }
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_addAssembly:
                recyclerViewState= recyclerView.getLayoutManager().onSaveInstanceState();
                Intent intent = new Intent(AddOrder_Activity.this, AddAssemblyToOrder_Activity.class);
                startActivityForResult(intent,CODE_ADD);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        selectedPosition = pos;//get the current position of the spinner
        String customerName = parent.getItemAtPosition(pos).toString();
        customerId= getTheCustomerId(customerName);//getTheId
      //  Toast.makeText(getApplicationContext(),String.valueOf(customerId),Toast.LENGTH_SHORT).show();




    }
    public void onNothingSelected(AdapterView<?>parent){
      //just to override the OnItemSelected
    }
    public int getTheCustomerId(String customerName)
    {   int customerId=0;
        List<Customer>customers= inventory.getAllCustomers();
        for(Customer customer:customers)
        {
            if(customerName.equalsIgnoreCase(customer.getFirst_Name().trim()+" "+customer.getLast_Name().trim()))
            {
                customerId = customer.getId();
            }
        }
        return customerId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CODE_ADD: {
                if(resultCode == AppCompatActivity.RESULT_OK){
                     assemblyId= data.getIntExtra(AddAssemblyToOrder_Activity.EXTRA_ASSEMBLY_ID,0);//this is the actual value transfer do not confound with order_assemblyId
                    AddAssemblyDataBase(assemblyId);
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    adapter = new OrderAssemblyAdapter(inventory.getAllAssembliesOfOrdersById(orderId));//get All the assemblies and show them
                    recyclerView.setAdapter(adapter);

                   // Toast.makeText(getApplicationContext(),String.valueOf(assemblyId),Toast.LENGTH_SHORT).show();

                }
            }
            break;


        }
    }
    private void AddAssemblyDataBase(int assemblyId)
    {
        //Toast.makeText(getApplicationContext(),inventory.getAssemblyDescriptionById(assemblyId),Toast.LENGTH_SHORT).show();
        String assemblyDescription = inventory.getAssemblyDescriptionById(assemblyId);
        int quantity= inventory.VerifyTheQuantityOfAssembly(assemblyId,orderId);
        if(quantity ==-1)
        {
            Order_Assembly order_assembly = new Order_Assembly(orderId,assemblyId,assemblyDescription,1);//quantityisOneforthefirstTime
            //add
            inventory.InsertNewOrderAssembly(order_assembly);

        }
        else
        {
            //update
            Order_Assembly order_assembly = new Order_Assembly(orderId,assemblyId,assemblyDescription,quantity+1);//modify the quantity
            //add
            inventory.UpdateNewOrderAssembly(order_assembly);

        }


    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment frag = fragmentManager.findFragmentByTag(TAG4);
        if (frag != null) {
            fragmentManager.beginTransaction().remove(frag).commit();
        }
        MyGeneralDialog myBackPressDialog= MyGeneralDialog.newInstance("Exit","Are you sure you want to exit?,all the non-saved-information will be deleted",CODE_BACKPRESSED);
        myBackPressDialog.show(fragmentManager, TAG4);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_ASSEMBLY_ID,assemblyId);
        outState.putInt(SAVE_ORDER_ID,orderId);
        outState.putInt(SAVE_ORDER_ASSEMBLY_ID,order_assemblyId);
        outState.putInt(SAVE_SELETED_POSITION,selectedPosition);
        outState.putParcelable(SAVE_STATE_RECYCLER,recyclerViewState);
    }
}
