package com.fiuady.android.compustorevv10;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Diego Hernandez on 21/04/2017.
 */

public class MissingProductsAdapter {

    public static class ProductViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        private TextView IDtxt, DesTxt, priceTxt, qtyTxt;

        public ProductViewHolder(View itemView) {
            super(itemView);



        }
    }
}
