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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

public class AddAssembly_Activity extends AppCompatActivity implements MyNumberPickerDialog.NoticeDialogListener,DialogClickListener {
    //public static final String EXTRA_PRODUCT_ID="com.fiuady.android.project1.product_id";
    private static final String EXTRA_ASSEMBLY_PRODUCTS="assembly_products";
    private static final String TAG ="modify_quantity"; //to open the new fragment
    private static final String TAG2="delete_product";
    private static final String TAG3 = "cancel";
    private static final String TAG4 = "backpress";
    private static final String TEMPORAL_NAME="com.fiuady.android.project1";
    private static final String EXTRA_ASSEMBLY_ID="assembly_id";
    public static final int CODE=1;
    private int  value_ProductId=0;
    private Inventory inventory;
    private ArrayList<AssemblyProducts>products = new ArrayList<AssemblyProducts>();
    private class ProductsAssemblyHolder extends RecyclerView.ViewHolder{
        private TextView txtProductDescription;
        private TextView txtProductId;
        private TextView txtCategoryId;
        private TextView txtQuantity;
        private ImageView imageView;
        public ProductsAssemblyHolder(View itemView){
            super(itemView);
            txtProductDescription = (TextView)itemView.findViewById(R.id.product_name_description);
            txtCategoryId = (TextView)itemView.findViewById(R.id.product_categoryId_description);
            txtProductId =(TextView)itemView.findViewById(R.id.product_id_description);
            txtQuantity = (TextView)itemView.findViewById(R.id.product_quantity_description);
            imageView = (ImageView)itemView.findViewById(R.id.product_add_icon);
        }
        public void bindProductAssembly(AssemblyProducts assemblyProducts)
        {
            txtProductDescription.setText(assemblyProducts.getDescription());
            txtProductId.setText(String.valueOf(assemblyProducts.getProductId()));
            txtCategoryId.setText(String.valueOf(assemblyProducts.getCategoryId()));
            txtQuantity.setText(String.valueOf(assemblyProducts.getQuantity()));
        }

    }
    private class ProductsAssemblyAdapter extends RecyclerView.Adapter<ProductsAssemblyHolder>{
        private List<AssemblyProducts> assemblyProductsList;
        public ProductsAssemblyAdapter(List<AssemblyProducts> assemblyProductsList){
            this.assemblyProductsList =assemblyProductsList;
        }
        @Override
        public ProductsAssemblyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.productofassembly_item,parent,false);
            return new ProductsAssemblyHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductsAssemblyHolder holder, int position) {
            holder.bindProductAssembly(assemblyProductsList.get(position));
        }

        @Override
        public int getItemCount() {
            return assemblyProductsList.size();
        }

    }
    private RecyclerView recyclerView;
    private ProductsAssemblyAdapter adapter;
    private Parcelable viewRecycler;
    private ImageView imageView;
    private TextView txt_ProductId;
    private PopupMenu popupMenu;
    private EditText txtNewAssemblyName;
    private Button btnCancel;
    private Button btnOk;
    private int AssemblyId;
    private Assembly assembly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assembly_);
        Toolbar toolbar = (Toolbar)findViewById(R.id.addAssembly_toolbar);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//to remove the focus of the text
        txtNewAssemblyName = (EditText)findViewById(R.id.addAssembly_text);
        btnOk= (Button)findViewById(R.id.addAssembly_saveButton);
        btnCancel = (Button)findViewById(R.id.addAssembly_cancelButton);


        getSupportActionBar().setTitle("Add Assembly");
         inventory = new Inventory(getApplicationContext());
         //AssemblyId = inventory.getLastIdAssembly()+1;

         /*assembly = new Assembly(AssemblyId,TEMPORAL_NAME); //just create a temporal object to create a temporal row in the database
        inventory.InsertNewAssembly(assembly);*/
        recyclerView = (RecyclerView)findViewById(R.id.addAssembly_RecyclerView);//cast the recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // AssemblyId = inventory.getLastIdAssembly()+1;
       // assembly = new Assembly(AssemblyId,TEMPORAL_NAME); //just create a temporal object to create a temporal row in the database

        if(savedInstanceState != null){
            products = savedInstanceState.getParcelableArrayList(EXTRA_ASSEMBLY_PRODUCTS);
            AssemblyId = savedInstanceState.getInt(EXTRA_ASSEMBLY_ID);
            assembly = new Assembly(AssemblyId,TEMPORAL_NAME);
            adapter = new ProductsAssemblyAdapter(inventory.getAllProductsOfAssemblyById(AssemblyId));
            recyclerView.setAdapter(adapter);

        }
        else
        {
            AssemblyId = inventory.getLastIdAssembly()+1;
            assembly = new Assembly(AssemblyId,TEMPORAL_NAME); //just create a temporal object to create a temporal row in the database
            inventory.InsertNewAssembly(assembly);

        }




        //List<Product>products = inventory.getAllProducts();
        //adapter = new ProductAdapter(products);

       /* Intent i = getIntent();
        value_ProductId= i.getIntExtra(EXTRA_PRODUCT_ID,0);

            Toast.makeText(getApplicationContext(),String.valueOf(value_ProductId),Toast.LENGTH_SHORT);*/
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(),recyclerView,new RecyclerItemOnClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                imageView = (ImageView)view.findViewById(R.id.product_modify_icon);
                txt_ProductId = (TextView)view.findViewById(R.id.product_id_description);
                value_ProductId =Integer.valueOf(txt_ProductId.getText().toString());



                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        popupMenu = new PopupMenu(AddAssembly_Activity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_modify_assemblyproducts,popupMenu.getMenu());
                        PopupItemClickListener(popupMenu);
                        popupMenu.show();
                    }
                });




            }

            @Override
            public void onLongItemClick(View view, int position) {
                imageView = (ImageView)view.findViewById(R.id.product_modify_icon);
                txt_ProductId = (TextView)view.findViewById(R.id.product_id_description);
                value_ProductId =Integer.valueOf(txt_ProductId.getText().toString());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupMenu = new PopupMenu(AddAssembly_Activity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_modify_assemblyproducts,popupMenu.getMenu());
                        PopupItemClickListener(popupMenu);
                        popupMenu.show();
                    }
                });

            }

    }));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewAssemblyName = txtNewAssemblyName.getText().toString().trim();
                if(NewAssemblyName.length() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Enter a valid assembly name",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(inventory.getAllProductsOfAssemblyById(AssemblyId).size()==0){//ToKnowIfExistTheId

                        Toast.makeText(getApplicationContext(),"There are not products in the assembly",Toast.LENGTH_SHORT).show();

                    }
                    else {

                        if (inventory.KnowIfExistsTheAssembly(NewAssemblyName)) {
                            Toast.makeText(getApplicationContext(), "The assembly name already exists", Toast.LENGTH_SHORT).show();

                        } else {
                           /* Assembly assembly = new Assembly(inventory.getLastIdAssembly()+1,NewAssemblyName);
                            if(inventory.InsertNewAssembly(assembly)) {//verify that all is ok

                                //InserttheListOfProductsInDataBase();
                                inventory.InsertListOfNewAssemblyProduct(products);

                                Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
                                final Intent intent = new Intent();
                                setResult(AppCompatActivity.RESULT_OK, intent);
                                finish();
                            }*///WithoutDataBase
                            Assembly assembly = new Assembly(AssemblyId,NewAssemblyName);
                            inventory.UpdateAssembly(assembly);
                            final Intent intent = new Intent();
                            setResult(AppCompatActivity.RESULT_OK, intent);
                            finish();


                        }
                    }
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment frag = fragmentManager.findFragmentByTag(TAG3);
                if (frag != null) {
                    fragmentManager.beginTransaction().remove(frag).commit();
                }
                MyCancelDialog myCancelDialog= MyCancelDialog.newInstance("Cancel");
                myCancelDialog.show(fragmentManager, TAG3);

            }
        });





    }
    public void InserttheListOfProductsInDataBase()
    {

        for(AssemblyProducts product:products)
        {
            inventory.InsertNewAssemblyProduct(product);

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addassembly,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_assembly:
                final Intent intent= new Intent(getApplicationContext(),AddProductsToAssembly_Activity.class);
                startActivityForResult(intent, CODE);
                return true;



        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CODE: {
                if(resultCode == AppCompatActivity.RESULT_OK){
                    value_ProductId = data.getIntExtra(AddProductsToAssembly_Activity.EXTRA_PRODUCT_ID,0);

                     AddProduct(value_ProductId);
                    /*adapter = new ProductsAssemblyAdapter(products);
                    recyclerView.setAdapter(adapter);*///WithoutDatabase
                    adapter = new ProductsAssemblyAdapter(inventory.getAllProductsOfAssemblyById(AssemblyId));//get All the products
                    recyclerView.setAdapter(adapter);

                    //Toast.makeText(getApplicationContext(),String.valueOf(value_ProductId),Toast.LENGTH_SHORT).show();

                }
            }
            break;

        }
    }
    private void AddProduct(int productId)
    {

        ProductLara product;
        product = inventory.getProductById(productId);//gettheProductFromInventory
        //Toast.makeText(AddAssembly_Activity.this, product.getDescription(), Toast.LENGTH_SHORT).show();
       // int assemblyId=inventory.getLastIdAssembly()+1;//it sums one more because it will be now the last assembly id WITHOUT THE DATABASE
        //Toast.makeText(AddAssembly_Activity.this, String.valueOf(assemblyId), Toast.LENGTH_SHORT).show();


        //int quantity = gettheProductsQuantity(productId);WITHOUT DATABASE
        int quantity = gettheProductsQuantityDataBase(productId,inventory.getAllProductsOfAssemblyById(AssemblyId));//togetTheQuantityOfProductIfExist
       AssemblyProducts assemblyProducts = new AssemblyProducts(AssemblyId,product.getId(),quantity,product.getCategory_id(),product.getDescription(),product.getPrice());

        if (quantity > 1)
        {
            int IndexOfProduct=getIndexofProduct(productId);
            products.set(IndexOfProduct,assemblyProducts);
            inventory.UpdateAssemblyProduct(assemblyProducts);
        }
        else
        {
            products.add(assemblyProducts);
            inventory.InsertNewAssemblyProduct(assemblyProducts);
        }

        //return products;*/

    }
    private int gettheProductsQuantityDataBase(int productId,List<AssemblyProducts> assemblyProductsList)
    {
        int quantity = 1;
        for(AssemblyProducts product:assemblyProductsList){
            if(product.getProductId()== productId)
            {
                quantity = product.getQuantity()+1;
            }
        }
        return quantity;
    }
    private void RemoveProduct(int productId){
        for(AssemblyProducts product:products)
        {
            if(product.getProductId()==productId){
                products.remove(product);
            }
        }
    }
    private void RemoveProductDatabase(int productId){
        ProductLara product;
        product = inventory.getProductById(productId);//get the product         //put 0 cause all will be eliminate
        AssemblyProducts assemblyProducts = new AssemblyProducts(AssemblyId,product.getId(),1,product.getCategory_id(),product.getDescription(),product.getPrice());
        inventory.DeleteAssemblyProduct(assemblyProducts);

    }
    private int gettheProductsQuantity(int productId)
    {   int quantity = 1;

        for(AssemblyProducts product:products)
        {
            if(product.getProductId()==productId)
            {
                quantity = product.getQuantity()+1;

            }

        }
        return quantity;

    }
    int state;
    private Parcelable recyclerViewState;
    private int getEachProductQuantity(int productId){
        int quantity=0;
        for(AssemblyProducts product:products)
        {
            if(product.getProductId() == productId)
            {
                quantity = product.getQuantity();
            }
        }
        return quantity;
    }
    private int getEachProductQuantityDatabase(int productId, List<AssemblyProducts>assemblyProductsList)
    {
        int quantity=0;
        for(AssemblyProducts product:assemblyProductsList)
        {
            if(product.getProductId()==productId)
            {
                quantity = product.getQuantity();
            }
        }
        return quantity;
    }
    private int getIndexofProduct(int productId)
    {
        int index=0;

        for(AssemblyProducts product:products)
        {
            if(product.getProductId()==productId)
            {
                index=products.indexOf(product);

            }

        }
        return index;

    }
    public void setNewQuantityOfProduct(int newQuantity){
        for(AssemblyProducts product:products){
            if(product.getProductId() == value_ProductId){
                product.setQuantity(newQuantity);

            }
        }
    }
    public void setNewQuantityOfProductDatabase(int newQuantity)
    {
        ProductLara product;
        product = inventory.getProductById(value_ProductId);//get the product
        AssemblyProducts assemblyProducts = new AssemblyProducts(AssemblyId,product.getId(),newQuantity,product.getCategory_id(),product.getDescription(),product.getPrice());
        inventory.UpdateAssemblyProduct(assemblyProducts);

    }
    public void onDialogPositiveClick(int newQuantity)
    {
        //setNewQuantityOfProduct(newQuantity);WITHOUTDATABASE
        setNewQuantityOfProductDatabase(newQuantity);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        adapter = new ProductsAssemblyAdapter(inventory.getAllProductsOfAssemblyById(AssemblyId));
        recyclerView.setAdapter(adapter);


        //Toast.makeText(getApplicationContext(),String.valueOf(newQuantity),Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(EXTRA_ASSEMBLY_PRODUCTS,recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(EXTRA_ASSEMBLY_PRODUCTS,products);
        outState.putInt(EXTRA_ASSEMBLY_ID,AssemblyId);

    }
    private void PopupItemClickListener(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.option_Modify:
                        //int quantity = getEachProductQuantity(value_ProductId);Without DATABASE
                        int quantity = getEachProductQuantityDatabase(value_ProductId,inventory.getAllProductsOfAssemblyById(AssemblyId));
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
                        recyclerViewState= recyclerView.getLayoutManager().onSaveInstanceState();//save the state of recyclerView
                        FragmentManager fragmentManager = getFragmentManager();
                        Fragment frag = fragmentManager.findFragmentByTag(TAG2);
                        if (frag != null) {
                            fragmentManager.beginTransaction().remove(frag).commit();
                        }
                        MyDeleteProductDialogFragment myDeleteProductDialogFragment= MyDeleteProductDialogFragment.newInstance("Delete Product");
                        myDeleteProductDialogFragment.show(fragmentManager, TAG2);
                        break;
                }
                return true;
            }
        });

    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {
        if(dialogFragment.getClass() == MyDeleteProductDialogFragment.class) {//could come from other class
            RemoveProductDatabase(value_ProductId);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            adapter = new ProductsAssemblyAdapter(inventory.getAllProductsOfAssemblyById(AssemblyId));
            recyclerView.setAdapter(adapter);
        }
        if(dialogFragment.getClass() == MyCancelDialog.class)
        {
            List<AssemblyProducts>productsList = inventory.getAllProductsOfAssemblyById(AssemblyId);
            inventory.DeleteListOfNewAssemblyProduct(productsList);
            inventory.DeleteAssembly(assembly);
            this.finish();
        }
        if(dialogFragment.getClass()== MyBackPressDialog.class)
        {
            List<AssemblyProducts>productsList = inventory.getAllProductsOfAssemblyById(AssemblyId);
            inventory.DeleteListOfNewAssemblyProduct(productsList);
            inventory.DeleteAssembly(assembly);
            finish();
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment frag = fragmentManager.findFragmentByTag(TAG4);
        if (frag != null) {
            fragmentManager.beginTransaction().remove(frag).commit();
        }
        MyBackPressDialog myBackPressDialog= MyBackPressDialog.newInstance("Exit");
        myBackPressDialog.show(fragmentManager, TAG4);
    }
}
