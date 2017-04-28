package com.fiuady.android.compustorevv10;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Orders_Activity extends AppCompatActivity implements MyShowOrdersClientsDialog.OnDialogSelectorListener,DatePickerFragment.OnDateSetListener,MyShowStatusOrdersDialog.OnPositiveClick,View.OnClickListener,MyChangeLog1.NoticeDialogListener,MyChangeLog2.DialogListener{
     private Inventory inventory;
    private static final String TAG="showCustomerstag";
    private static final String TAG2="showStatustag";
    private static final String TAG3="showMyChangeLog1";
    private static final String TAG4="showMyChangeLog2";
    private static final String EXTRA_ARRAYLISTVIEW="com.fiuady.android.project1.arrayListViews";
    private static final String EXTRA_BOOLEANARRAY="booleanArray";
    private static final String DATE_PICKER="DatePicker";
    private static final int CODE_ADD=1;
    private static final int CODE_EDIT=2;
    private static final String SAVE_FLAGCHECK1="save_flag1";
    private static final String SAVE_FLAGCHECK2="save_flag2";
    private static final String SAVE_INITIAL_DATE ="save_initial";
    private static final String SAVE_FINAL_DATE ="save_final";

    private String IniDate;
    private String FinDate;

    private Boolean FlagCheck=false;
    private Boolean FlagCheck2 = false;

    private CheckBox chk_InitialDate;
    private CheckBox chk_FinalDate;
    private Spinner spinner;
    private boolean[] booleanArrayList = new boolean[5];
    MySpinnerAdapter mySpinnerAdapter;
    ArrayList<StateView> Views= new ArrayList<StateView>();
    ArrayList<StateView> StateViews = new ArrayList<StateView>();
    List<Order_Status>order_statusList=null;
    private RecyclerView recyclerView;
    private int statusPicked=0;//important
    private boolean selectedClient=false;//important
    private String status="";//important
    private int selectedCustomerId;//important
    private String InitialDate;//important
    private String FinalDate;//important
    private static final String CODE_STATUSPICKED=" com.fiuady.android.project1.statuspicked";
    private static final String CODE_SELECTEDCLIENT="com.fiuady.android.project1.selectedclient";
    private static final String CODE_STATUS="com.fiuady.android.project1.status";
    private static final String CODE_SELECTEDCUSTOMERID=" com.fiuady.android.project1.selectedcustomerid";
    private static final String CODE_INITIALDATE="com.fiuady.android.project1.initialDate";
    private static final String CODE_FINALDATE="com.fiuady.android.project1.finalDate";
    private TextView txtInitialDate;
    private TextView txtFinalDate;
    private static final String CODE_ORDER_ID="code_orderid";//save the Id
    private static final String CODE_RECYCLER_STATE="recycler_state";//recyclerState;
    private Parcelable recyclerViewState;

    private class OrderHolder extends RecyclerView.ViewHolder{
        private TextView txtOrderId;
        private TextView txtCostumer;
        private TextView txtStatus;
        private TextView txtDate;
        private ImageView imageOptions;
        public OrderHolder(View itemView){
            super(itemView);
            txtOrderId = (TextView)itemView.findViewById(R.id.orderNumber_description);
            txtCostumer = (TextView)itemView.findViewById(R.id.costumer_description);
            txtStatus= (TextView)itemView.findViewById(R.id.statusOrder_description);
            txtDate = (TextView)itemView.findViewById(R.id.orderDate_description);
            imageOptions = (ImageView)itemView.findViewById(R.id.orderOption_View);
        }
        public void bindOrder(Order order)
        {
            txtOrderId.setText(String.valueOf(order.getId()));
            txtCostumer.setText(order.getCostumer_FirstName()+ " " + order.getCostumer_LastName());
            txtStatus.setText(order.getStatus_Description());
            txtDate.setText(order.getDate());
        }
    }
    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder>{
        private List<Order> orders;
        public OrderAdapter(List<Order>orders){
            this.orders = orders;
        }

        @Override
        public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.orders_item,parent,false);
            return new OrderHolder(view);
        }

        @Override
        public void onBindViewHolder(OrderHolder holder, int position) {
            holder.bindOrder(orders.get(position));
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public List<Order> getOrders() {
            return orders;
        }
    }
    private OrderAdapter orderAdapter;
    private int statusOrderId;//important
    private ImageView imageView;
    private int orderId;//important
    private int customerId;//important
    private String date;//important
    private String changeLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_Orders);
        //booleanArrayList ={false,false,false,false,false,false};
        booleanArrayList[0]=false;
        booleanArrayList[1]=false;
        booleanArrayList[2]=false;
        booleanArrayList[3]=false;
        booleanArrayList[4]=false;

        inventory = new Inventory(getApplicationContext());
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders");
        //spinner =(Spinner)findViewById(R.id.spinner_Orders);

        //Views= getAllViews();//this does not work
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_Orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chk_InitialDate = (CheckBox)findViewById(R.id.chk_InitialDate);
        chk_FinalDate = (CheckBox)findViewById(R.id.chk_FinalDate);

        txtInitialDate =(TextView)findViewById(R.id.txtOrders_InitialDate);
        txtFinalDate =(TextView)findViewById(R.id.txtOrders_FinalDate);
        if(savedInstanceState !=null) {
            StateViews= savedInstanceState.getParcelableArrayList(EXTRA_ARRAYLISTVIEW);//to retrieve the state of the array
            booleanArrayList = savedInstanceState.getBooleanArray(EXTRA_BOOLEANARRAY);
            statusPicked=savedInstanceState.getInt(CODE_STATUSPICKED);
            selectedClient=savedInstanceState.getBoolean(CODE_SELECTEDCLIENT);

            status=savedInstanceState.getString(CODE_STATUS);
            selectedCustomerId=savedInstanceState.getInt(CODE_SELECTEDCUSTOMERID);
            InitialDate=savedInstanceState.getString(CODE_INITIALDATE);
            FinalDate=savedInstanceState.getString(CODE_FINALDATE);
            recyclerViewState = savedInstanceState.getParcelable(CODE_RECYCLER_STATE);
            orderId = savedInstanceState.getInt(CODE_ORDER_ID);
            SelectionOfOrders();//RefreshTheSearch


            FlagCheck = savedInstanceState.getBoolean(SAVE_FLAGCHECK1);
            FlagCheck2= savedInstanceState.getBoolean(SAVE_FLAGCHECK2);
            IniDate = savedInstanceState.getString(SAVE_INITIAL_DATE);
            FinDate = savedInstanceState.getString(SAVE_FINAL_DATE);

            txtInitialDate.setText(savedInstanceState.getString(SAVE_INITIAL_DATE));
            txtFinalDate.setText(savedInstanceState.getString(SAVE_FINAL_DATE));

        }
        else {
            StateViews = retrieveAllItems();//important
            List<Order>orders = inventory.getAllOrdersOrderByDate();
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);
        }
        //mySpinnerAdapter = new MySpinnerAdapter(Orders_Activity.this, 0, Views);


       // spinner.setAdapter(mySpinnerAdapter);
       // mySpinnerAdapter.setOnCheckedChange(this);

        order_statusList = inventory.getAllOrderStatus();
        //chk_InitialDate = (CheckBox)findViewById(R.id.chk_InitialDate);
       // chk_FinalDate = (CheckBox)findViewById(R.id.chk_FinalDate);




        chk_InitialDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if(!FlagCheck) {
                        showDatePicker(1);
                        FlagCheck = true;
                    }


                }
                else{
                    SelectionOfOrders();//refresh the search
                    IniDate = "InitialDate";
                    txtInitialDate.setText(IniDate);
                    FlagCheck = false;
                }

            }
        });
        chk_FinalDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if(!FlagCheck2) {
                        showDatePicker(2);
                        FlagCheck2 = true;
                    }
                }
                else
                {
                    SelectionOfOrders();//refresh the search
                    FinDate = "FinalDate";
                    txtFinalDate.setText(FinDate);
                    FlagCheck2 = false;
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(Orders_Activity.this, recyclerView, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Order order =orderAdapter.getOrders().get(position);  //get the object of the item selected
                statusOrderId = order.getStatus_Id();//important
                orderId= order.getId();
                customerId = order.getCostumer_Id();
                date = order.getDate();
                changeLog = order.getChange_Log();
                imageView = (ImageView)view.findViewById(R.id.orderOption_View);
                imageView.setOnClickListener(Orders_Activity.this);


            }

            @Override
            public void onLongItemClick(View view, int position) {

                Order order =orderAdapter.getOrders().get(position);  //get the object of the item selected
                statusOrderId = order.getStatus_Id();//important
                orderId= order.getId();
                customerId = order.getCostumer_Id();
                date = order.getDate();
                changeLog = order.getChange_Log();
                imageView = (ImageView)view.findViewById(R.id.orderOption_View);
                imageView.setOnClickListener(Orders_Activity.this);
            }
        }));




    }
    @Override
    public void onClick(View v) {//OnClick the image to show options
        PopupMenu popupMenu;//create a new popup menu
        popupMenu = new PopupMenu(Orders_Activity.this, v);
        if(statusOrderId ==0) {//Pendiente
            popupMenu.getMenuInflater().inflate(R.menu.menu_editorder, popupMenu.getMenu());
            PopupItemClickListener(popupMenu);
            popupMenu.show();
        }
        else if (statusOrderId>0 && statusOrderId<4)//All minus Finalizado
        {
            popupMenu.getMenuInflater().inflate(R.menu.menu_changelog, popupMenu.getMenu());
            PopupItemClickListener(popupMenu);
            popupMenu.show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"The order has been delivered!",Toast.LENGTH_SHORT).show();
        }

    }
    private void PopupItemClickListener(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.option_ChangeLogOrder:
                        switch (statusOrderId)
                        {
                            case 0://Pendiente
                                String statusCancelado = inventory.getStatusDescriptionById(statusOrderId+1);
                                String statusCOnfirmado = inventory.getStatusDescriptionById(statusOrderId+2);
                                recyclerViewState= recyclerView.getLayoutManager().onSaveInstanceState();
                                FragmentManager fragmentMan = getFragmentManager();
                                Fragment frag = fragmentMan.findFragmentByTag(TAG4);
                                if(frag != null){
                                    fragmentMan.beginTransaction().remove(frag).commit();
                                }
                                final MyChangeLog2 myChangeLog2= MyChangeLog2.newInstance(statusCOnfirmado,statusCancelado);//start dialog
                                myChangeLog2.show(fragmentMan,TAG4);
                                break;
                            default:
                                String statusDescription=null;
                                int NewstatusId;
                                switch (statusOrderId)
                                {
                                    case 1://Cancelado
                                        statusDescription = inventory.getStatusDescriptionById(statusOrderId-1);
                                        NewstatusId=statusOrderId-1;//Pendiente
                                        break;
                                    default:
                                        statusDescription = inventory.getStatusDescriptionById(statusOrderId+1);
                                        NewstatusId=statusOrderId+1;
                                        break;
                                }
                                recyclerViewState= recyclerView.getLayoutManager().onSaveInstanceState();
                                FragmentManager fragmentManager = getFragmentManager();
                                Fragment fragment = fragmentManager.findFragmentByTag(TAG3);
                                if(fragment != null){
                                    fragmentManager.beginTransaction().remove(fragment).commit();
                                }
                                final MyChangeLog1 myChangeLog1= MyChangeLog1.newInstance(NewstatusId,statusDescription);
                                myChangeLog1.show(fragmentManager,TAG3);
                                break;

                        }

                       //Toast.makeText(getApplicationContext(),"changeLog",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.option_editOrder://here we will edit
                        Intent intent = new Intent(Orders_Activity.this, EditOrder_Activity.class);//EYE HERE
                        intent.putExtra(EditOrder_Activity.EXTRA_ORDER_ID,orderId);
                        intent.putExtra(EditOrder_Activity.EXTRA_CUSTOMER_ID,customerId);
                        intent.putExtra(EditOrder_Activity.EXTRA_DATE_ORDER,date);
                        intent.putExtra(EditOrder_Activity.EXTRA_CHANGE_LOG,changeLog);
                        startActivityForResult(intent,CODE_EDIT);//this is the code for edit
                        break;

                }
                return true;
            }
        });

    }
    public void onDialogPositiveClick(int newStatusId, String newStatusDescription)
    {
        String date =new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String changeLog = date + ":"+newStatusDescription;
        inventory.UpdateOrderStatusByOrderId(newStatusId,changeLog,orderId);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        orderAdapter = new OrderAdapter(inventory.getAllOrdersOrderByDate());//get All orders
        recyclerView.setAdapter(orderAdapter);

        Toast.makeText(getApplicationContext(),changeLog,Toast.LENGTH_SHORT).show();

    }
    public void onPositiveClick(int newStatusId, String newStatusDescription)
    {
        String date =new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String changeLog = date + ":"+newStatusDescription;
        inventory.UpdateOrderStatusByOrderId(newStatusId,changeLog,orderId);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        orderAdapter = new OrderAdapter(inventory.getAllOrdersOrderByDate());//get All orders
        recyclerView.setAdapter(orderAdapter);

        Toast.makeText(getApplicationContext(),changeLog,Toast.LENGTH_SHORT).show();

    }
    private void showDatePicker(int valueOfChecked) {
        // date = new DatePickerFragment();
        Calendar calendar = Calendar.getInstance();//setUp date into dialog



        FragmentManager fragmentManager = getFragmentManager();
        Fragment frag = fragmentManager.findFragmentByTag(DATE_PICKER);
        if (frag != null) {
            fragmentManager.beginTransaction().remove(frag).commit();
        }
        DatePickerFragment datePickerDialog = DatePickerFragment.newInstance(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),valueOfChecked);
        datePickerDialog.show(fragmentManager, DATE_PICKER);
        datePickerDialog.setOnDateSetListener(this);


    }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth,int valueOfChecked) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,monthOfYear+1,dayOfMonth);
            Toast.makeText(
                    Orders_Activity.this,
                    String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                            + "-" + String.valueOf(dayOfMonth),
                    Toast.LENGTH_LONG).show();
            switch(valueOfChecked) {
                case 1:
                    if (monthOfYear + 1 < 10) {//adjust the value of month to the format
                        InitialDate = String.valueOf(year) + "-" + "0" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                    } else {
                        InitialDate = String.valueOf(year) + "-" + "0" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                    }
                    IniDate=ChangeFormat(InitialDate);
                    txtInitialDate.setText(ChangeFormat(InitialDate));

                    break;
                case 2:
                    if (monthOfYear + 1 < 10) {//adjust the value of month to the format
                        FinalDate = String.valueOf(year) + "-" + "0" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                    } else {
                        FinalDate = String.valueOf(year) + "-" + "0" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
                    }
                    FinDate=ChangeFormat(FinalDate);
                    txtFinalDate.setText(ChangeFormat(FinalDate));
                    break;
            }
            SelectionOfOrders();
        }
    private String ChangeFormat(String Date)
    {
        String result="";
        try
        {
            String date = Date;
            SimpleDateFormat spf= new SimpleDateFormat("yyyy-MM-dd");
            Date newDate=spf.parse(date);
            SimpleDateFormat spfNew= new SimpleDateFormat("dd MMM yyyy");
            result = spfNew.format(newDate);

        }
        catch(ParseException e)
        {
         e.printStackTrace();
        }


        return result;
    }

    public void onDialogNegativeClick(int value)
    {
        switch (value)
        {
            case 1:
                if(chk_InitialDate.isChecked())
                {
                    chk_InitialDate.setChecked(false);
                    IniDate ="InitialDate";
                    txtInitialDate.setText(IniDate);
                }
                break;
            case 2:
                if(chk_FinalDate.isChecked())
                {
                    FinDate="FinalDate";
                    chk_FinalDate.setChecked(false);
                    txtInitialDate.setText(FinDate);
                }
                break;

        }

    }


    private ArrayList<StateView>getAllViews()
    {
        ArrayList<StateView> listViews= new ArrayList<StateView>();
        StateView stateView = new StateView("Status",false);
       /* stateView.setTitle("Status");
        stateView.setSelected(false);*/
        listViews.add(0,stateView);//add the firstParam
        List<Order_Status>statusList = inventory.getAllOrderStatus();
        for(Order_Status order_status: statusList)
        {
            StateView stateView1 = new StateView(order_status.getDescription(),false);
            listViews.add(stateView1);
        }
        return listViews;


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_orders,menu);
        inventory = new Inventory(getApplicationContext());


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_filterClients:
                ArrayList<String>customers=getAllListOfCustomers(inventory.getAllCustomerSortedByName());//Important here it is add in order
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag(TAG);
                if(fragment != null){
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
                final MyShowOrdersClientsDialog myShowOrdersClientsDialog = MyShowOrdersClientsDialog.newInstance(customers,0);
                myShowOrdersClientsDialog.show(fragmentManager,TAG);

                break;
            case R.id.item_filterOrders:
                FragmentManager fragmentMan = getFragmentManager();
                Fragment frag = fragmentMan.findFragmentByTag(TAG2);
                if(frag != null){
                    fragmentMan.beginTransaction().remove(frag).commit();
                }
                booleanArrayList[0] = StateViews.get(0).isSelected();
                booleanArrayList[1] = StateViews.get(1).isSelected();
                booleanArrayList[2] = StateViews.get(2).isSelected();
                booleanArrayList[3] = StateViews.get(3).isSelected();
                booleanArrayList[4] = StateViews.get(4).isSelected();

                final MyShowStatusOrdersDialog myShowStatusOrdersDialog =  MyShowStatusOrdersDialog.newInstance(StateViews);
                myShowStatusOrdersDialog.show(fragmentMan,TAG2);
                break;
            case R.id.item_addOrder:
                Intent intent = new Intent(Orders_Activity.this, AddOrder_Activity.class );
                startActivityForResult(intent,CODE_ADD);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onSelectedOption(int selectedIndex)
    {
        Toast.makeText(getApplicationContext(),Integer.toString(selectedIndex),Toast.LENGTH_SHORT).show();
        switch (selectedIndex)
        {
            case -1:
                selectedClient=false;
                SelectionOfOrders();

                break;
            default:
                selectedClient=true;
                selectedCustomerId=selectedIndex;
               SelectionOfOrders();
                break;
        }


    }
    public void OnCheckedChange(int selectedIndexOfStatus)
    {
        Toast.makeText(getApplicationContext(),Integer.toString(selectedIndexOfStatus),Toast.LENGTH_SHORT).show();
        //booleanArrayList[selectedIndexOfStatus]=true;


    }
    public void OnCheckedNegativeChange(int selectedIndexOfStatus)
    {
       // Toast.makeText(getApplicationContext(),Integer.toString(selectedIndexOfStatus),Toast.LENGTH_SHORT).show();
        //booleanArrayList[selectedIndexOfStatus]=true;


    }

    private ArrayList<String> getAllListOfCustomers(List<Customer>customers)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add(0,"All Customers");
        for(Customer customer:customers)
        {
            list.add(customer.getFirst_Name().trim()+ " " + customer.getLast_Name().trim());
        }
        return list;
    }

    private ArrayList<StateView> retrieveAllItems() {
        ArrayList<StateView> listViews= new ArrayList<StateView>();

        List<Order_Status>statusList = inventory.getAllOrderStatus();
        for(Order_Status order_status: statusList)
        {
            StateView stateView = new StateView(order_status.getDescription(),false);
            listViews.add(stateView);
        }
        return listViews;
    }
    public void OnPositiveClickSelection(ArrayList mItemsSelected)
    { statusPicked=0;
         status="";
        if(mItemsSelected.size()>0)
        {

            if(mItemsSelected.contains(0))
            {
                statusPicked= statusPicked+1;
                StateViews.get(0).setSelected(true);
                    status="'0'";




            }
            if(mItemsSelected.contains(1))
            {
                statusPicked=statusPicked+1;
                StateViews.get(1).setSelected(true);
                if(status.length()==0)
                {
                    status=status+"'1'";
                }
                else{
                    status=status+",'1'";

                }
            }
            if(mItemsSelected.contains(2))
            {
                statusPicked = statusPicked+1;
                StateViews.get(2).setSelected(true);
                if(status.length()==0)
                {
                    status=status+"'2'";
                }
                else{
                    status=status+",'2'";

                }
            }
            if(mItemsSelected.contains(3))
            {
                statusPicked= statusPicked+1;
                StateViews.get(3).setSelected(true);
                if(status.length()==0)
                {
                    status=status+"'3'";
                }
                else{
                    status=status+",'3'";

                }

            }
            if(mItemsSelected.contains(4))
            {
                statusPicked = statusPicked+1;
                StateViews.get(4).setSelected(true);
                if(status.length()==0)
                {
                    status=status+"'4'";
                }
                else{
                    status=status+",'4'";

                }
            }
        }
        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();
       // SelectioOfStatus(status);
        SelectionOfOrders();
    }

    private void SelectionOfOrders()
    {
        if((statusPicked == 0) && (selectedClient == false) && (chk_InitialDate.isChecked() == false)&&(chk_FinalDate.isChecked() == false))
        {//1
         //show everything of the orders
            List<Order>orders = inventory.getAllOrdersOrderByDate();
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);
        }
        else if((statusPicked > 0) && (selectedClient==false) && (chk_InitialDate.isChecked()==false)&&(chk_FinalDate.isChecked()==false))
        {//2
            List<Order>orders = inventory.getAllOrdersByStatus(status);
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);
        }
        else if(statusPicked == 0 && (selectedClient==true) && (chk_InitialDate.isChecked()==false)&&(chk_FinalDate.isChecked()==false))
        {//3
            List<Order>orders = inventory.getAllOrdersByCustomerId(selectedCustomerId);//its minus one because the first is all the products
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if(statusPicked == 0 && (selectedClient==false) && (chk_InitialDate.isChecked()==true)&&(chk_FinalDate.isChecked()==false))
        {//4
            List<Order>orders= getListOfOrdersByInitialDate(inventory.getAllOrdersOrderByDate());
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if(statusPicked== 0 && (selectedClient==false)&& (chk_InitialDate.isChecked()==false)&&(chk_FinalDate.isChecked()==true))
        {//5
            List<Order>orders= getListOfOrdersByFinalDate(inventory.getAllOrdersOrderByDate());
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        //with two conditions
        else if((statusPicked > 0) && (selectedClient == true) && (chk_InitialDate.isChecked() == false)&&(chk_FinalDate.isChecked() == false))
        {//6
            List<Order>orders = inventory.getAllOrdersByStatusAndCustomer(selectedCustomerId,status);
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if((statusPicked > 0) && (selectedClient == false) && (chk_InitialDate.isChecked() == true)&&(chk_FinalDate.isChecked() == false))
        {//7
            List<Order>orders= getListOfOrdersByInitialDate( inventory.getAllOrdersByStatus(status));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);
        }
        else if((statusPicked > 0) && (selectedClient == false) && (chk_InitialDate.isChecked() == false)&&(chk_FinalDate.isChecked() == true))
        {//8
            List<Order>orders= getListOfOrdersByFinalDate( inventory.getAllOrdersByStatus(status));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if((statusPicked == 0) && (selectedClient == true) && (chk_InitialDate.isChecked() == true)&&(chk_FinalDate.isChecked() == false))
        {//9
            List<Order>orders= getListOfOrdersByInitialDate(inventory.getAllOrdersByCustomerId(selectedCustomerId));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);


        }
        else if((statusPicked == 0) && (selectedClient == true) && (chk_InitialDate.isChecked() == false)&&(chk_FinalDate.isChecked() == true))
        {//10
            List<Order>orders= getListOfOrdersByFinalDate(inventory.getAllOrdersByCustomerId(selectedCustomerId));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if((statusPicked == 0) && (selectedClient == false) && (chk_InitialDate.isChecked() == true)&&(chk_FinalDate.isChecked() == true))
        {//11
            List<Order>orders= getListOfOrdersByInitialAndFinalDate(inventory.getAllOrdersOrderByDate());
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        //Three Conditions
        else if((statusPicked > 0) && (selectedClient == true) && (chk_InitialDate.isChecked() == true)&&(chk_FinalDate.isChecked() == false))
        {//12
            List<Order>orders= getListOfOrdersByInitialDate(inventory.getAllOrdersByStatusAndCustomer(selectedCustomerId,status));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if((statusPicked > 0) && (selectedClient == true) && (chk_InitialDate.isChecked() == false)&&(chk_FinalDate.isChecked() == true))
        {//13
            List<Order>orders= getListOfOrdersByFinalDate(inventory.getAllOrdersByStatusAndCustomer(selectedCustomerId,status));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if((statusPicked == 0) && (selectedClient == true) && (chk_InitialDate.isChecked() == true)&&(chk_FinalDate.isChecked() == true))
        {//14
            List<Order>orders= getListOfOrdersByInitialAndFinalDate(inventory.getAllOrdersByCustomerId(selectedCustomerId));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        else if((statusPicked > 0) && (selectedClient == false) && (chk_InitialDate.isChecked() == true)&&(chk_FinalDate.isChecked() == true))
        {//15
            List<Order>orders= getListOfOrdersByInitialAndFinalDate(inventory.getAllOrdersByStatus(status));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }
        //with four conditions
        else if((statusPicked > 0) && (selectedClient == true) && (chk_InitialDate.isChecked() == true)&&(chk_FinalDate.isChecked() == true))
        {//16
            List<Order>orders= getListOfOrdersByInitialAndFinalDate(inventory.getAllOrdersByStatusAndCustomer(selectedCustomerId,status));
            orderAdapter= new OrderAdapter(orders);
            recyclerView.setAdapter(orderAdapter);

        }

    }
    private List<Order> getListOfOrdersByInitialDate(List<Order> orders)
    {
        boolean flag=false;
        ArrayList<Order>list = new ArrayList<Order>();
        for(Order order:orders)
        {
            flag=CheckIfTheDateisLongerOrEqual(order.getDate());
            if(flag)
            {
                list.add(order);
            }
        }
        return list;
    }
    public boolean CheckIfTheDateisLongerOrEqual(String date)
    {
        boolean flag=false;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String str1 = date;
            String str2 = InitialDate;
            Date date1=formatter.parse(str1);
            Date date2 = formatter.parse(str2);
            if(date1.compareTo(date2)>=0)
            {
                flag=true;
            }
        }catch (ParseException e)
        {
          e.printStackTrace();
        }

        return flag;
    }
    private List<Order> getListOfOrdersByFinalDate(List<Order> orders)
    {
        boolean flag=false;
        ArrayList<Order>list = new ArrayList<Order>();
        for(Order order:orders)
        {
            flag=CheckIfTheDateisShorterOrEqual(order.getDate());
            if(flag)
            {
                list.add(order);
            }
        }
        return list;
    }
    public boolean CheckIfTheDateisShorterOrEqual(String date)
    {
        boolean flag=false;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String str1 = date;
            String str2 = FinalDate;
            Date date1=formatter.parse(str1);
            Date date2 = formatter.parse(str2);
            if(date1.compareTo(date2)<=0)
            {
                flag=true;
            }
        }catch (ParseException e)
        {
            e.printStackTrace();
        }

        return flag;
    }
    private List<Order> getListOfOrdersByInitialAndFinalDate(List<Order> orders)
    {
        boolean flag=false;
        ArrayList<Order>list = new ArrayList<Order>();
        for(Order order:orders)
        {
            flag=CheckIfTheDateIsInTheRange(order.getDate());
            if(flag)
            {
                list.add(order);
            }
        }
        return list;
    }
    public boolean CheckIfTheDateIsInTheRange(String date)
    {
        boolean flag=false;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String str1 = date;
            String str2 = InitialDate;
            String str3 = FinalDate;
            Date date1 = formatter.parse(str1);
            Date date2 = formatter.parse(str2);
            Date date3 = formatter.parse(str3);
            if(date1.compareTo(date2)>=0 && date1.compareTo(date3)<=0)
            {
                flag=true;
            }
        }catch (ParseException e)
        {
            e.printStackTrace();
        }

        return flag;
    }

    public void OnCancelClickSelection()
    {

        StateViews.get(0).setSelected(booleanArrayList[0]);
        StateViews.get(1).setSelected(booleanArrayList[1]);
        StateViews.get(2).setSelected(booleanArrayList[2]);
        StateViews.get(3).setSelected(booleanArrayList[3]);
        StateViews.get(4).setSelected(booleanArrayList[4]);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CODE_ADD:
            {
                if(resultCode == AppCompatActivity.RESULT_OK)
                {
                   // Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();
                    List<Order>orders = inventory.getAllOrdersOrderByDate();
                    orderAdapter= new OrderAdapter(orders);
                    recyclerView.setAdapter(orderAdapter);
                }

            }
            break;
            case CODE_EDIT:
            {
                if(resultCode == AppCompatActivity.RESULT_OK)
                {
                    // Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();
                    List<Order>orders = inventory.getAllOrdersOrderByDate();
                    orderAdapter= new OrderAdapter(orders);
                    recyclerView.setAdapter(orderAdapter);
                }

            }
            break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(EXTRA_ARRAYLISTVIEW,StateViews);
        outState.putBooleanArray(EXTRA_BOOLEANARRAY,booleanArrayList);
        outState.putInt(CODE_STATUSPICKED,statusPicked);
        outState.putBoolean(CODE_SELECTEDCLIENT,selectedClient);
        outState.putString(CODE_STATUS,status);
        outState.putInt(CODE_SELECTEDCUSTOMERID,selectedCustomerId);
        outState.putString(CODE_INITIALDATE,InitialDate);
        outState.putString(CODE_FINALDATE,FinalDate);
        outState.putInt(CODE_ORDER_ID,orderId);
        outState.putParcelable(CODE_RECYCLER_STATE,recyclerViewState);
        outState.putBoolean(SAVE_FLAGCHECK1,FlagCheck);
        outState.putBoolean(SAVE_FLAGCHECK2,FlagCheck2);
        outState.putString(SAVE_INITIAL_DATE,IniDate);
        outState.putString(SAVE_FINAL_DATE,FinDate);



    }
}
