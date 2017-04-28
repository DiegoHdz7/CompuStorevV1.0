/*package com.fiuady.android.compustorevv10.Productos;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;



import com.fiuady.android.compustorevv10.Product;
import com.fiuady.android.compustorevv10.R;
import com.fiuady.android.compustorevv10.db.Inventory;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Diego Hernandez on 06/04/2017.
 */


/*
public class MyProductAdapter  extends RecyclerView.Adapter<MyProductAdapter.ProductViewHolder>  {


    private static List<Product> lista_productos;





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






    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{





        private TextView IDtxt,DesTxt,priceTxt,qtyTxt,catDesTxt;

        public ProductViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
           catDesTxt= (TextView) itemView.findViewById(R.id.txtPdes);
            IDtxt = (TextView) itemView.findViewById(R.id.pIdTxt);
            DesTxt = (TextView) itemView.findViewById(R.id.DesProductTxt);
            priceTxt = (TextView) itemView.findViewById(R.id.pPrecioTxt);
            qtyTxt = (TextView) itemView.findViewById(R.id.pQtyTxt);

        }

        @Override
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


                            int productId = lista_productos.get(pos).getId();








                        }





                        return false;
                    }

                });



        }

    }





}


*/
