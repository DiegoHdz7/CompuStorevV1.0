package com.fiuady.android.compustorevv10;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.List;


public class ProductCategory_Activity extends AppCompatActivity implements DialogClickListener {
    private boolean HasbeenClicked = false;
    private PopupMenu popupMenu;
    final private String KEY_CLICK = "clicked";
    private static final String BUNDLE_RECYCLER_LAYOUT="recycler_layout";
    final private String KEY_RECYCLER_ID = "recycler_id";
    private class  CategoriesHolder extends RecyclerView.ViewHolder{
        private TextView txtCategory;
        public  CategoriesHolder(View itemView)
        {
            super(itemView);
            txtCategory=(TextView)itemView.findViewById(R.id.product_category_text);
        }
        public void bindCategory(Product_Categories category)
        {
            txtCategory.setText(category.getDescription());
        }
    }
    private class CategoryAdapter extends RecyclerView.Adapter<CategoriesHolder>{
        private List<Product_Categories> categories;
        public CategoryAdapter(List<Product_Categories> categories){
            this.categories=categories;
        }

        @Override
        public CategoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d("ComputerStore--","Create holder...");
            View view= getLayoutInflater().inflate(R.layout.product_categories_item,parent,false);
            return new CategoriesHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoriesHolder holder, int position) {
            Log.d("ComputerStore","Binding employee in " + position);
            holder.bindCategory(categories.get(position));


        }

        @Override
        public int getItemCount() {
            return categories.size();
        }


    }
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private Context context;
    private Inventory inventory;
    private ClipData.Item btnAddItem;
    //private PopupMenu popupMenu;
    private TextView txtViewName;
    private String textCategoryName;
    private int positionView;
    private View viewRecycle;
    private Parcelable viewRecycler;

    public String getTextCategoryName() {
        return textCategoryName;
    }

    public void setTextCategoryName(String textCategoryName) {
        this.textCategoryName = textCategoryName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_);
        inventory = new Inventory(getApplicationContext());
        /*recyclerView=(RecyclerView)findViewById(R.id.Product_Category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventory.getAllCategories();
        List<Product_Categories>categories = inventory.getAllCategories();
        adapter= new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);*/
        //recyclerView=(RecyclerView)findViewById(R.id.Product_Category_recycler_view);
        if(savedInstanceState != null){
            HasbeenClicked =savedInstanceState.getBoolean(KEY_CLICK,false);
            viewRecycler = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView = (RecyclerView)findViewById(R.id.Product_Category_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.getLayoutManager().onRestoreInstanceState(viewRecycler);
            positionView = savedInstanceState.getInt(KEY_RECYCLER_ID);
            adapter= new CategoryAdapter(inventory.getAllCategoriesLara());
            recyclerView.setAdapter(adapter);
          //  Toast.makeText(getApplicationContext(),String.valueOf(positionView),Toast.LENGTH_SHORT).show();


        }
        else
        {

            recyclerView=(RecyclerView)findViewById(R.id.Product_Category_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            inventory.getAllCategories();
            List<Product_Categories>categories = inventory.getAllCategoriesLara();
            adapter= new CategoryAdapter(categories);
            recyclerView.setAdapter(adapter);
        }


        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(ProductCategory_Activity.this,recyclerView, new RecyclerItemOnClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {

                txtViewName = (TextView)view.findViewById(R.id.product_category_text); //casting the category´s name
                textCategoryName = txtViewName.getText().toString().trim();
               //Toast.makeText(getApplicationContext(),textCategoryName,Toast.LENGTH_SHORT).show();
                setTextCategoryName(textCategoryName);
                popupMenu = new PopupMenu(ProductCategory_Activity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menuproducts,popupMenu.getMenu());
                PopupItemClickListener(popupMenu);
                popupMenu.show();
                HasbeenClicked=true;
                positionView = position;


            }

            @Override
            public void onLongItemClick(View view, int position) {
                txtViewName = (TextView)view.findViewById(R.id.product_category_text); //casting the category´s name
                textCategoryName = txtViewName.getText().toString().trim();
                setTextCategoryName(textCategoryName);//set the textCategory for future use
                popupMenu = new PopupMenu(ProductCategory_Activity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.menuproducts,popupMenu.getMenu());
                PopupItemClickListener(popupMenu);
                popupMenu.show();

                positionView = position;

                HasbeenClicked=true;
            }
        }));

       Toolbar toolbar= (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");


    }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar,menu);

        return true;
  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getFragmentManager();
       Fragment frag = manager.findFragmentByTag("fragment_add_category");
       if(frag != null){
            manager.beginTransaction().remove(frag).commit();
       }
        switch (item.getItemId()){
            case R.id.action_add:

                MyAlertDialogFragment alertDialogFragment= new MyAlertDialogFragment();
                //alertDialogFragment.setTargetFragment(frag,0);
                alertDialogFragment.show(getFragmentManager(), "fragment_add_category");

                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void PopupItemClickListener (PopupMenu popupMenu)
    {
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.option_Edit:

                        FragmentManager fragmentManager1 =getFragmentManager();
                        Fragment frag1 = fragmentManager1.findFragmentByTag("fragment_edit_category");
                        if(frag1 != null){
                            fragmentManager1.beginTransaction().remove(frag1).commit();
                        }
                        MyEditDialogFragment myEditDialogFragment = MyEditDialogFragment.newInstance(getTextCategoryName());
                        myEditDialogFragment.show(fragmentManager1,"fragment_edit_category");

                        break;

                    case R.id.option_Delete:
                        FragmentManager fragmentManager =getFragmentManager();
                        Fragment frag = fragmentManager.findFragmentByTag("fragment_delete_category");
                        if(frag != null){
                            fragmentManager.beginTransaction().remove(frag).commit();
                        }
                        MyDeleteDialogFragment deleteDialogFragment=MyDeleteDialogFragment.newInstance("Delete Category",getTextCategoryName());
                        deleteDialogFragment.show(fragmentManager,"fragment_delete_category");
                        break;


                }
                return true;
            }
        });
    }

    /*@Override this fuck me up
    public void onDialogPositiveClick(DialogFragment dialogFragment) {

        List<Product_Categories>categories = inventory.getAllCategories();
        adapter= new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {

    }*/

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {
        List<Product_Categories>categories = inventory.getAllCategoriesLara();
        adapter= new CategoryAdapter(categories);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CLICK,HasbeenClicked);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT,recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putInt(KEY_RECYCLER_ID,positionView);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       // recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT));
    }
}
