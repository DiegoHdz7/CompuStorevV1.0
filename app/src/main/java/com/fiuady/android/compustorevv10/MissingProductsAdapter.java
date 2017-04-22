package com.fiuady.android.compustorevv10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Diego Hernandez on 21/04/2017.
 */

public class MissingProductsAdapter extends RecyclerView.Adapter<MissingProductsAdapter.MissingProductViewHolder>{

    private List<ProductoFaltante> MissingProductsList;

    public MissingProductsAdapter(List<ProductoFaltante> missingProductsList) {
        this.MissingProductsList=missingProductsList;
    }

    @Override
    public MissingProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_missing_products,parent,false);
        MissingProductViewHolder holder = new MissingProductViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(MissingProductViewHolder holder, int position) {
       // holder.AssemblyIdCol.setText(String.valueOf(MissingProductsList.get(position).getAssemblyId()));
        holder.ProductIdCol.setText(String.valueOf(MissingProductsList.get(position).getProductId()));
        holder.MissingProductCol.setText( String.valueOf(MissingProductsList.get(position).getQty()));

    }

    @Override
    public int getItemCount() {
        return MissingProductsList.size();
    }

    public static class MissingProductViewHolder extends RecyclerView.ViewHolder  {

        private TextView AssemblyIdCol, ProductIdCol, MissingProductCol;

        public MissingProductViewHolder(View itemView) {
            super(itemView);


            ProductIdCol = (TextView) itemView.findViewById(R.id.ProductIDTxt);
            MissingProductCol = (TextView) itemView.findViewById(R.id.MissingProduct);

        }
    }
}
