package com.fiuady.android.compustorevv10.Productos;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.Product;
import com.fiuady.android.compustorevv10.R;
import com.fiuady.android.compustorevv10.SalesPerMounthAdapter;
import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Product_Activity extends AppCompatActivity implements DialogAlerta.NoticeDialogListener
{


    Spinner spnCat;
    EditText desEdit;
    ImageButton btnSearch;
    RecyclerView rv;
    int Minqty;
    boolean isSearched = false;



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
       SetAdapterList();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

/////////////////////////////////////////////////////////
/////////////////Recycler Part







    public class MyProductAdapter  extends RecyclerView.Adapter<MyProductAdapter.ProductViewHolder>   {


        public List<Product> lista_productos;





        private View.OnClickListener listener;


        public MyProductAdapter(List<Product> list)
        {
            this.lista_productos = list;



        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_products_layout,parent,false);
            ProductViewHolder holder = new ProductViewHolder(v);
            return holder;

        }


        @Override
        public void onBindViewHolder( ProductViewHolder holder, int position) {
            holder.IDtxt.setText(String.valueOf(lista_productos.get(position).getId()));
            holder.DesTxt.setText(lista_productos.get(position).getDescription());
            holder.priceTxt.setText(String.valueOf(lista_productos.get(position).getPrice()));
            holder.qtyTxt.setText(String.valueOf(lista_productos.get(position).getQty()));
            holder.catDesTxt.setText(lista_productos.get(position).getCat_description());

        }


        public  Product GetProductOfRecycler (int index)
        {
            return lista_productos.get(index);
        }


        @Override
        public int getItemCount() {
            return lista_productos.size();
        }

        public class ProductViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/
        {





            private TextView IDtxt,DesTxt,priceTxt,qtyTxt,catDesTxt;

            public ProductViewHolder(View itemView) {
                super(itemView);

                //itemView.setOnClickListener(this);
                catDesTxt= (TextView) itemView.findViewById(R.id.txtPdes);
                IDtxt = (TextView) itemView.findViewById(R.id.pIdTxt);
                DesTxt = (TextView) itemView.findViewById(R.id.DesProductTxt);
                priceTxt = (TextView) itemView.findViewById(R.id.pPrecioTxt);
                qtyTxt = (TextView) itemView.findViewById(R.id.pQtyTxt);

            }

           /* @Override
            public void onClick(final View v) {


                PopupMenu popmenu = new PopupMenu(v.getContext(), v);
                popmenu.inflate(R.menu.menu_popup);
                popmenu.show();


                final  int pos = this.getAdapterPosition();
                Toast.makeText(v.getContext(),String.valueOf(lista_productos.get(pos).getId()),Toast.LENGTH_SHORT).show();






                popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Inventory manager = new Inventory(v.getContext());

                        if(item.toString().equals("Modificar")) {

                            int Id = lista_productos.get(pos).getId();
                            String des = lista_productos.get(pos).getDescription();
                            String pPrice = String.valueOf(lista_productos.get(pos).getPrice());
                            String catdes = lista_productos.get(pos).getCat_description();

                            int productId = lista_productos.get(pos).getId();
                            Intent i = new Intent(v.getContext(),UpdateActivity.class);
                            i.putExtra(UpdateActivity.EXTRA_ID,Id);
                            i.putExtra(UpdateActivity.EXTRA_Description, des);
                            i.putExtra(UpdateActivity.EXTRA_Precio,pPrice);
                            Log.i("eval = ",String.valueOf(String.valueOf(pPrice)));
                            i.putExtra(UpdateActivity.EXTRA_Category,catdes);

                            v.getContext().startActivity(i);

                        }


                        if(item.toString().equals("Eliminar")) {

                            int productId = lista_productos.get(pos).getId();
                            manager.DeleteProduct(productId);


                        }

                        if(item.toString().equals("Agregar a Stock")) {


                            int pId = lista_productos.get(pos).getId();
                            int productQty = lista_productos.get(pos).getQty();
                            DialogAlerta dialogo =  DialogAlerta.newInstance(pId,productQty);
                            dialogo.show(getFragmentManager(), "tagAlerta");




                            ////////////////////

                            //////////////////









                        }





                        return false;
                    }

                });



            }*/


        }







    }



    public  void UpdateRecycler()
    {
        Inventory manager = new Inventory(getApplicationContext());
        int length = desEdit.getText().toString().length();
        String SW = desEdit.getText().toString();
        String cat_Des = spnCat.getSelectedItem().toString();
        int Category_Id;

        //FragmentManager fragmentManager = getSupportFragmentManager();



        if(!spnCat.getSelectedItem().toString().equals("Todas")) {
            Category_Id = manager.GetCategoryId(spnCat.getSelectedItem().toString());

        }
        else {
            Category_Id =0;
        }




        if(manager.GetFilteredProducts(Category_Id,SW,cat_Des).size()==0)
        //if (manager.prueba3(Category_Id,SW,cat_Des).isEmpty())
        {
            Toast.makeText(getApplicationContext(),"No se encontraron coincidencias",Toast.LENGTH_SHORT).show();
        }

        else {//GetFilteredProducts
            final MyProductAdapter adapter = new MyProductAdapter(manager.GetFilteredProducts(Category_Id,SW,cat_Des));
            rv.setAdapter(adapter);
        }
    }





//////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_products,menu);
        return super.onCreateOptionsMenu(menu);
    }

     MyProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_products);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");


        spnCat = (Spinner) findViewById(R.id.spnProducts);
        desEdit = (EditText) findViewById(R.id.eTxtSearch);
        btnSearch = (ImageButton) findViewById(R.id.iBtnSearch);
        rv = (RecyclerView) findViewById(R.id.recyclerProducts);


        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);



        final Inventory manager = new Inventory(getApplicationContext());

        final ArrayList<String> catDes = new ArrayList<String>();
        for (Proudct_Category pc: manager.getAllCategories()
             ) {

            catDes.add(pc.description);

        }

        catDes.add("Todas");




        spnCat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,catDes));



        if (savedInstanceState != null) {
            isSearched = savedInstanceState.getBoolean(KEY_SEARCHED, false);
            if (isSearched) {
               SetAdapterList();
            }
        }






        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            isSearched=true;
             SetAdapterList();//Busqueda por filtrado


            }
        });


        rv.addOnItemTouchListener(new RecyclerItemOnClickListener(Product_Activity.this, rv, new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(final View v, final int position) {

                final PopupMenu popmenu = new PopupMenu(v.getContext(), v);
                popmenu.inflate(R.menu.menu_popup);
                popmenu.show();

                popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Inventory manager = new Inventory(v.getContext());

                        if(item.toString().equals("Modificar")) {

                            int Id = adapter.GetProductOfRecycler(position).getId();
                            String des = adapter.GetProductOfRecycler(position).getDescription();
                            String pPrice = String.valueOf( adapter.GetProductOfRecycler(position).getPrice());
                            String catdes =  adapter.GetProductOfRecycler(position).getCat_description();


                            Intent i = new Intent(v.getContext(),UpdateActivity.class);
                            i.putExtra(UpdateActivity.EXTRA_ID,Id);
                            i.putExtra(UpdateActivity.EXTRA_Description, des);
                            i.putExtra(UpdateActivity.EXTRA_Precio,pPrice);
                            Log.i("eval = ",String.valueOf(String.valueOf(pPrice)));
                            i.putExtra(UpdateActivity.EXTRA_Category,catdes);

                           // v.getContext().startActivity(i);
                            startActivityForResult(i,UpdateActivity.CODE_UPDATE);
                            popmenu.dismiss();

                        }


                        if(item.toString().equals("Eliminar")) {

                            int productId = adapter.GetProductOfRecycler(position).getId();
                            manager.DeleteProduct(productId);
                            SetAdapterList();
                            popmenu.dismiss();


                        }

                        if(item.toString().equals("Agregar a Stock")) {


                            int pId = adapter.GetProductOfRecycler(position).getId();
                            int productQty = adapter.GetProductOfRecycler(position).getQty();
                            DialogAlerta dialogo =  DialogAlerta.newInstance(pId,productQty);
                            dialogo.show(getFragmentManager(), "tagAlerta");
                            SetAdapterList();
                            popmenu.dismiss();




                            ////////////////////

                            //////////////////









                        }





                        return false;
                    }

                });







            }

            @Override
            public void onLongItemClick(View v, int position) {


            }
        }));












    }


    public void SetAdapterList()
    {
        Inventory manager = new Inventory(Product_Activity.this);
        String SW = desEdit.getText().toString();
        String cat_Des = spnCat.getSelectedItem().toString();
        int Category_Id;




        if(!spnCat.getSelectedItem().toString().equals("Todas")) {
            Category_Id = manager.GetCategoryId(spnCat.getSelectedItem().toString());

        }
        else {
            Category_Id =0;
        }




        if(manager.GetFilteredProducts(Category_Id,SW,cat_Des).size()==0)
        //if (manager.prueba3(Category_Id,SW,cat_Des).isEmpty())
        {
            Toast.makeText(getApplicationContext(),"No se encontraron coincidencias",Toast.LENGTH_SHORT).show();
        }

        else {//GetFilteredProducts
            adapter = new MyProductAdapter(manager.GetFilteredProducts(Category_Id, SW, cat_Des));
            rv.setAdapter(adapter);


        }






    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.add_product:
                Intent i = new Intent(Product_Activity.this,AddProductActivity.class);
                startActivity(i);

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            SetAdapterList();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_SEARCHED, isSearched);
        outState.putString(KEY_Description_EditTxt,desEdit.getText().toString());
        outState.putInt(KEY_SPN_CAT_POS,spnCat.getSelectedItemPosition());



    }

    public static String KEY_SEARCHED = "KEY_SERCHED";
    public static String KEY_Description_EditTxt = "KEY_Description_EditTxt";
    public static String KEY_SPN_CAT_POS = "KEY_SPN_CAT_POS";






}


