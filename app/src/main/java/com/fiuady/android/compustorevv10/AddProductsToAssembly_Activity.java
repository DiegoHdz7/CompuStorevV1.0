package com.fiuady.android.compustorevv10;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

public class AddProductsToAssembly_Activity extends AppCompatActivity implements MyShowCategoriesDialogFragment.OnDialogSelectorListener,SearchView.OnQueryTextListener{
          private Inventory inventory;
          private static final String TAG="MyShowCategoriesDialog";
    public static final String EXTRA_PRODUCT_ID="com.fiuady.android.project1.product_id";
    private static final String SAVE_SELECTED_CATEGORY="savecategoryId";
    private static final String SAVE_QUERY ="save_query";
    private static final String SAVE_BOOLEANFLAG="save_booleanFlag";
    private int SelectedcategoryId;

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


   //String
    private SearchView searchView;
    private MenuItem searchItem;
    private String mSearchQuery=null;
    private Boolean FlagSearch=false;


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
        //List<ProductLara>products = inventory.getAllProducts();
       // adapter = new ProductAdapter(products);
       // recyclerView.setAdapter(adapter);
        if(savedInstanceState != null)
        {
            SelectedcategoryId = savedInstanceState.getInt(SAVE_SELECTED_CATEGORY);
            FlagSearch = savedInstanceState.getBoolean(SAVE_BOOLEANFLAG);
            mSearchQuery = savedInstanceState.getString(SAVE_QUERY);
            UpdateSearch(mSearchQuery,FlagSearch);
        }
        else
        {
            List<ProductLara>products = inventory.getAllProducts();
            adapter = new ProductAdapter(products);
            recyclerView.setAdapter(adapter);

        }
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
        searchItem = menu.findItem(R.id.item_searchAssemblies);
        searchItem = menu.findItem(R.id.action_SearchProducts);


        if(searchItem != null){
            searchView =(SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {

                    return false;
                }
            });
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });

            SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            if(mSearchQuery !=null)
            {
                searchView.setIconified(false);
                //searchView.onActionViewExpanded();
                searchItem.expandActionView();
                searchView.setQuery(mSearchQuery,false);
                searchView.setFocusable(true);
                //List<Assembly>assemblies = inventory.getAllAssembliesOnTextChange(mSearchQuery);
                //adapter= new AddAssemblyToOrder_Activity.AssemblyAdapter(assemblies);
               // recyclerView.setAdapter(adapter);
                //searchView.clearFocus();
                UpdateSearch(mSearchQuery,FlagSearch);

            }
            EditText searchPlate =(EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search Products");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this,android.R.color.transparent));
            searchView.setOnQueryTextListener(this);
            MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    //here is nothing
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    mSearchQuery=null;
                    return true;
                }
            });
        }


        return super.onCreateOptionsMenu(menu);



    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
        mSearchQuery=query;
         UpdateSearch(query,FlagSearch);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_SHORT).show();
        mSearchQuery=newText;
        UpdateSearch(newText,FlagSearch);
        return false;
    }
    private void UpdateSearch(String searchText,boolean flagCategory)
    {
        mSearchQuery = searchText;

        if(mSearchQuery==null && flagCategory == false)
        {

            //List<Assembly>assemblies = inventory.getAllAssembliesOnTextChange(searchText);
            // adapter= new AddAssemblyToOrder_Activity.AssemblyAdapter(assemblies);
            // recyclerView.setAdapter(adapter);
            adapter = new ProductAdapter(inventory.getAllProducts());
            recyclerView.setAdapter(adapter);
        }
        else if(mSearchQuery==null && flagCategory ==true)
        {
            //List<Assembly>assemblies = inventory.getAllAssembliesOrderByName();
            // adapter= new AddAssemblyToOrder_Activity.AssemblyAdapter(assemblies);
            //recyclerView.setAdapter(adapter);
            adapter = new ProductAdapter(inventory.getProductsByCategory(SelectedcategoryId));//first is all categories but we obtain the value
            recyclerView.setAdapter(adapter);
        }
        else if(searchText.length()>0 && flagCategory==false)
        {
            adapter = new ProductAdapter(inventory.getProductsByTextChange(searchText));//first is all categories but we obtain the value
            recyclerView.setAdapter(adapter);

        }
        else if(searchText.length()>0 && flagCategory==true)
        {
            adapter = new ProductAdapter(inventory.getProductsByCategoryIdAndTextChange(SelectedcategoryId,searchText));//first is all categories but we obtain the value
            recyclerView.setAdapter(adapter);

        }


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
            case -1:
                //adapter = new ProductAdapter(inventory.getAllProducts());
               // recyclerView.setAdapter(adapter);
                   FlagSearch =false;
                UpdateSearch(mSearchQuery,FlagSearch);
                break;
            default:
                //adapter = new ProductAdapter(inventory.getProductsByCategory(selectedIndex));//first is all categories but we obtain the value
                //recyclerView.setAdapter(adapter);
                FlagSearch = true;
                SelectedcategoryId = selectedIndex;
                UpdateSearch(mSearchQuery,FlagSearch);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_SELECTED_CATEGORY, SelectedcategoryId);
        outState.putBoolean(SAVE_BOOLEANFLAG,FlagSearch);
        outState.putString(SAVE_QUERY,mSearchQuery);
    }
}
