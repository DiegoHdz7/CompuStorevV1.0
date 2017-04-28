package com.fiuady.android.compustorevv10;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.List;

public class Assemblies_Activity extends AppCompatActivity implements DialogClickListener{
    private static final int EXTRA_CODE=2;
    private static final int CODE_EDIT=3;
    private static final String TAG="deleteAssembly_option";
    private class AssembliesHolder extends RecyclerView.ViewHolder{
        private TextView txtAssembly;
        private ImageView imageOptions;
        public AssembliesHolder(View itemView){
            super(itemView);
            txtAssembly = (TextView)itemView.findViewById(R.id.assembly_text);
            imageOptions = (ImageView)itemView.findViewById(R.id.more_options);
        }
        public void bindAssembly(Assembly assembly)
        {
            txtAssembly.setText(assembly.getDescription());
        }
    }
    private class AssemblyAdapter extends RecyclerView.Adapter<AssembliesHolder>{
        private List<Assembly> assemblies;
        public AssemblyAdapter(List<Assembly>assemblies){
            this.assemblies = assemblies;
        }

        @Override
        public AssembliesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= getLayoutInflater().inflate(R.layout.assembly_item,parent,false);
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
    }
    private Inventory inventory;
    private RecyclerView recyclerView;
    private AssemblyAdapter adapter;
    private ImageView imageView;
    private TextView txt_ProductId;
    private int value_ProductId;
    private PopupMenu popupMenu;
    private TextView txt_AssemblyDescription;
    private String Assembly_Description;
    private int assemblyId;
    private Parcelable recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemblies);
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_assemblies_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assemblies");
        inventory = new Inventory(getApplicationContext());
        recyclerView=(RecyclerView)findViewById(R.id.Assemblies_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assembly>assemblies = inventory.getAllAssemblies();
        adapter= new AssemblyAdapter(assemblies);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), recyclerView, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                imageView = (ImageView)view.findViewById(R.id.more_options);
               txt_AssemblyDescription = (TextView)view.findViewById(R.id.assembly_text);
                Assembly_Description = txt_AssemblyDescription.getText().toString();
               // value_ProductId =Integer.valueOf(txt_ProductId.getText().toString());




                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        popupMenu = new PopupMenu(Assemblies_Activity.this, v);
                        popupMenu.getMenuInflater().inflate(R.menu.menuproducts,popupMenu.getMenu());
                        PopupItemClickListener(popupMenu);
                        popupMenu.show();
                    }
                });

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }
    private int ReturnTheIdAssembly(String assembly_Description)
    {
        int assemblyId=0;
        List<Assembly>assemblies= inventory.getAllAssemblies();
        for(Assembly assembly: assemblies)
        {
            if(assembly.getDescription().equalsIgnoreCase(assembly_Description))
            {
                assemblyId=assembly.getId();
            }
        }
        return assemblyId;
    }

    private SearchView searchView;
    private void PopupItemClickListener(PopupMenu popupMenu){
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.option_Edit:
                        Intent intent = new Intent(Assemblies_Activity.this, EditAssembly_Activity.class);
                        intent.putExtra(EditAssembly_Activity.EXTRA_ASSEMBLY_DESCRIPTION,Assembly_Description);
                        startActivityForResult(intent,CODE_EDIT);
                        break;
                    case R.id.option_Delete:
                        assemblyId = ReturnTheIdAssembly(Assembly_Description);
                        if(inventory.GetTheNumberOfOrdersRelatedToAssembly(assemblyId) > 0)
                        {
                            Toast.makeText(getApplicationContext(),"The assembly is related to a order and can not be deleted",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                            recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                            FragmentManager fragmentManager = getFragmentManager();
                            Fragment frag = fragmentManager.findFragmentByTag(TAG);
                            if (frag != null) {
                                fragmentManager.beginTransaction().remove(frag).commit();
                            }
                            MyDeleteGeneralDialog myDeleteGeneralDialog= MyDeleteGeneralDialog.newInstance("Delete Assembly","Are you sure you want to delete the assembly");
                            myDeleteGeneralDialog.show(fragmentManager, TAG);
                        }

                        break;

                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_assemblies_search:
                Intent intent = new Intent(Assemblies_Activity.this, AddAssembly_Activity.class );
                startActivityForResult(intent,EXTRA_CODE);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuassemblies,menu);

        final MenuItem searchItem = menu.findItem(R.id.search_assemblies_search);



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
           EditText searchPlate =(EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this,android.R.color.transparent));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case EXTRA_CODE:
            {
                if(resultCode == AppCompatActivity.RESULT_OK)
                {
                    List<Assembly>assemblies = inventory.getAllAssemblies();
                    adapter= new AssemblyAdapter(assemblies);
                    recyclerView.setAdapter(adapter);
                }

            }
            break;
            case CODE_EDIT:
            {
                    if(resultCode == AppCompatActivity.RESULT_OK)
                    {
                        List<Assembly>assemblies = inventory.getAllAssemblies();
                        adapter= new AssemblyAdapter(assemblies);
                        recyclerView.setAdapter(adapter);
                    }


            }
        }
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {
        Assembly assembly = new Assembly(assemblyId,Assembly_Description);
        inventory.DeleteAllTheProductsFromAssemblyById(assemblyId);
        inventory.DeleteAssembly(assembly);
        
        adapter= new AssemblyAdapter(inventory.getAllAssemblies());
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {

    }

    @Override
    public void onBackPressed() {

        if(!searchView.isIconified()){
            searchView.setIconified(true);


        }
        else {
            super.onBackPressed();
        }
    }
}
