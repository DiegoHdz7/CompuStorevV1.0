package com.fiuady.android.compustorevv10;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import java.util.List;

public class AddAssemblyToOrder_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener,View.OnClickListener{
    private static final String CODE_SEARCHQUERY="searchQuery";
    private static final String CODE_HASBEENPRESSED="hasbeenpressed";
    public  static final String EXTRA_ASSEMBLY_ID="assembly_id";
    private static  final String CODE_ASSEMBLY_ID="save_assembly_id";
    private class AssembliesHolder extends RecyclerView.ViewHolder{
        private TextView txtAssemblyName;
        private ImageView imageOptions;
        private TextView txtAssemblyId;
        public AssembliesHolder(View itemView){
            super(itemView);
            txtAssemblyName = (TextView)itemView.findViewById(R.id.assemblyName_description);
            txtAssemblyId=(TextView)itemView.findViewById(R.id.assemblyId_description);
            imageOptions = (ImageView)itemView.findViewById(R.id.addAssemblyOption_View);
        }
        public void bindAssembly(Assembly assembly)
        {
            txtAssemblyId.setText(String.valueOf(assembly.getId()));
            txtAssemblyName.setText(assembly.getDescription());
        }
    }
    private class AssemblyAdapter extends RecyclerView.Adapter<AssembliesHolder>{
        private List<Assembly> assemblies;
        public AssemblyAdapter(List<Assembly>assemblies){
            this.assemblies = assemblies;
        }

        @Override
        public AssembliesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.addassembly_item,parent,false);
            return new AssembliesHolder(view);
        }

        @Override
        public void onBindViewHolder(AssembliesHolder holder, int position) {
            holder.bindAssembly(assemblies.get(position));
        }

        @Override
        public int getItemCount() {
            return assemblies.size();
        }

        public List<Assembly> getAssemblies() {
            return assemblies;
        }
    }
    private Inventory inventory;
    private RecyclerView recyclerView;
    private AssemblyAdapter adapter;
    private SearchView searchView;
    private MenuItem searchItem;
    private Assembly assembly; //This is important
    private int assemblyId;
    private ImageView imageView;
    private PopupMenu popupMenu;


    private String mSearchQuery;
    private Boolean HasBeenPressed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assembly_to_order);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_AddAssemblyToOrder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Assembly");
        inventory = new Inventory(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_AddAssemblyToOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState!=null)
        {
            mSearchQuery = savedInstanceState.getString(CODE_SEARCHQUERY);


        }
        final List<Assembly>assemblies = inventory.getAllAssembliesOrderByName();
            adapter= new AssemblyAdapter(assemblies);
            recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(AddAssemblyToOrder_Activity.this, recyclerView, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                 assembly=adapter.getAssemblies().get(position);
                assemblyId = assembly.getId();
                imageView = (ImageView)view.findViewById(R.id.addAssemblyOption_View);
                imageView.setOnClickListener(AddAssemblyToOrder_Activity.this);


            }

            @Override
            public void onLongItemClick(View view, int position) {

                assembly=adapter.getAssemblies().get(position);
                assemblyId = assembly.getId();
                imageView = (ImageView)view.findViewById(R.id.addAssemblyOption_View);
                imageView.setOnClickListener(AddAssemblyToOrder_Activity.this);
            }
        }));



    }
    @Override
    public void onClick(View v) {//OnClick the image to show options

        popupMenu = new PopupMenu(AddAssemblyToOrder_Activity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_addassembliestoorder,popupMenu.getMenu());
        PopupItemClickListener(popupMenu);
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addassemblytoorder,menu);
        searchItem = menu.findItem(R.id.item_searchAssemblies);



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

                    HasBeenPressed = true;

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
                List<Assembly>assemblies = inventory.getAllAssembliesOnTextChange(mSearchQuery);
                adapter= new AssemblyAdapter(assemblies);
                recyclerView.setAdapter(adapter);
                //searchView.clearFocus();

            }
            EditText searchPlate =(EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search Assembly");
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
    private void PopupItemClickListener(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_addAssemblyOption:
                        //Toast.makeText(getApplicationContext(), "hello",Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent();

                        intent.putExtra(EXTRA_ASSEMBLY_ID,assemblyId);
                        setResult(AppCompatActivity.RESULT_OK,intent);
                        //Toast.makeText(getApplicationContext(),String.valueOf(assemblyId),Toast.LENGTH_SHORT).show();
                        finish();


                        break;
                }
                return false;
            }
        });

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
        UpdateSearch(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_SHORT).show();
        mSearchQuery=newText;
        UpdateSearch(newText);
        return false;
    }
    private void UpdateSearch(String searchText)
    {
       if(searchText.length()>0)
       {
           String NewString = searchText;
           List<Assembly>assemblies = inventory.getAllAssembliesOnTextChange(searchText);
           adapter= new AssemblyAdapter(assemblies);
           recyclerView.setAdapter(adapter);
       }
        else
       {
           List<Assembly>assemblies = inventory.getAllAssembliesOrderByName();
           adapter= new AssemblyAdapter(assemblies);
           recyclerView.setAdapter(adapter);
       }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CODE_SEARCHQUERY,mSearchQuery);


    }
    @Override
    public void onBackPressed() {
        //HasBeenPressed =false;

        if(!searchView.isIconified()){
            searchView.setIconified(true);


        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(CODE_ASSEMBLY_ID,assemblyId);
    }
}
