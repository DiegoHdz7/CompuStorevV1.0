package com.fiuady.android.compustorevv10;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

public class AddProductsToAssembly_Activity extends AppCompatActivity implements MyShowCategoriesDialogFragment.OnDialogSelectorListener {
          private Inventory inventory;
          private static final String TAG="MyShowCategoriesDialog";
    public static final String EXTRA_PRODUCT_ID="com.fiuady.android.project1.product_id";

    //public static final int CODE=1;

    private class ProductsHolder extends RecyclerView.ViewHolder{
        private TextView txtProductName;
        private TextView txtProductId;
        private TextView txtProductCategory;
        private ImageView imageAddProductToAssembly;
        public ProductsHolder(View itemView){
            super(itemView);
                txtProductName = (TextView)itemView.findViewById(R.id.product_name_description);
                txtProductId = (TextView)itemView.findViewById(R.id.product_id_description);
                txtProductCategory =(TextView)itemView.findViewById(R.id.product_categoryId_description);
                imageAddProductToAssembly = (ImageView)itemView.findViewById(R.id.product_add_icon);
            }
        public void bindProduct (ProductLara product){
            txtProductName.setText(product.getDescription());
            txtProductCategory.setText(String.valueOf(product.getCategory_id()));
            txtProductId.setText(String.valueOf(product.getId()));
        }
    }
    private class ProductAdapter extends RecyclerView.Adapter<ProductsHolder>{
        private List<ProductLara> products;
        public ProductAdapter(List<ProductLara>products){
            this.products = products;
        }
        @Override
        public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view= getLayoutInflater().inflate(R.layout.products_item,parent,false);
                    return new ProductsHolder(view);

        }
        @Override
        public void onBindViewHolder(ProductsHolder holder,int position){
            holder.bindProduct(products.get(position));
        }
        @Override
        public int getItemCount(){
            return (products.size());
        }

        public List<ProductLara> getProducts() {
            return products;
        }
    }


    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ImageView imageView;
    private TextView txt_ProductId;
    private int value_ProductId;
    private PopupMenu popupMenu;
    private boolean flag= false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_to_assembly_);
        Toolbar toolbar = (Toolbar)findViewById(R.id.addProductToAssembly_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Products");

        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.ProductsToAssembly_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ProductLara>products = inventory.getAllProducts();
        adapter = new ProductAdapter(products);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(AddProductsToAssembly_Activity.this, recyclerView, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                imageView = (ImageView)view.findViewById(R.id.product_add_icon);
                txt_ProductId = (TextView)view.findViewById(R.id.product_id_description);
                value_ProductId =Integer.valueOf(txt_ProductId.getText().toString());



                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        popupMenu = new PopupMenu(AddProductsToAssembly_Activity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_addproducttoassembly,popupMenu.getMenu());
                        PopupItemClickListener(popupMenu);
                        popupMenu.show();
                    }
                });




            }

            @Override
            public void onLongItemClick(View view, int position) {
                imageView = (ImageView)view.findViewById(R.id.product_add_icon);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupMenu = new PopupMenu(AddProductsToAssembly_Activity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_addproducttoassembly,popupMenu.getMenu());
                        PopupItemClickListener(popupMenu);
                        popupMenu.show();
                    }
                });

            }
        }));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_addproductstoassembly, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_ShowCategories:

                ArrayList<String>categories = GetAllCategories(inventory.getAllCategoriesLara());
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag(TAG);
                if(fragment != null){
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
               final MyShowCategoriesDialogFragment myShowCategoriesDialogFragment = MyShowCategoriesDialogFragment.newInstance(categories,0);
                     myShowCategoriesDialogFragment.show(fragmentManager,TAG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSelectedOption(int selectedIndex){
        switch(selectedIndex){
            case 0:
                adapter = new ProductAdapter(inventory.getAllProducts());
                recyclerView.setAdapter(adapter);
                break;
            default:
                adapter = new ProductAdapter(inventory.getProductsByCategory(selectedIndex-1));//minus 1 cause the first is all categories 
                recyclerView.setAdapter(adapter);
                break;

        }

    }
    private ArrayList<String> GetAllCategories(List<Product_Categories> categories){
        ArrayList<String> list= new ArrayList<String>();
        list.add(0,"All categories");//to add to the list the Option AllCategories
        for(Product_Categories cat: categories){
            list.add(cat.getDescription());
        }
        return list;
    }
    private void PopupItemClickListener(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_product_option:
                        //Toast.makeText(getApplicationContext(), "hello",Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent();

                        intent.putExtra(EXTRA_PRODUCT_ID,value_ProductId);
                        setResult(AppCompatActivity.RESULT_OK,intent);

                        finish();

                        break;
                }
                return false;
            }
        });

    }
}
