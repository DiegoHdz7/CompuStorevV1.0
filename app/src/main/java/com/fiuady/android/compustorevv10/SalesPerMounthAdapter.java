package com.fiuady.android.compustorevv10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.List;

/**
 * Created by Diego Hernandez on 23/04/2017.
 */



public class SalesPerMounthAdapter extends RecyclerView.Adapter<SalesPerMounthAdapter.SalesViewHolder> implements View.OnClickListener{

    List<String> meses;
    private View.OnClickListener listener;
   final  String año;





    public SalesPerMounthAdapter(List<String> meses, String Año) {
        this.meses = meses;
        this.año=Año;

    }




    @Override
    public SalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_sales_per_moutn,parent,false);
        SalesPerMounthAdapter.SalesViewHolder holder = new SalesPerMounthAdapter.SalesViewHolder(v);
        v.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(SalesViewHolder holder, int position) {


        Inventory manager = new Inventory( holder.itemView.getContext());
        holder.txtPrecio.setText(String.valueOf(manager.GetTotalSalesPerMounth(position,año)));
        holder.txtMeses.setText(String.valueOf(meses.get(position)));


    }

    @Override
    public int getItemCount() {
        return meses.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public static class SalesViewHolder extends RecyclerView.ViewHolder  {

            private TextView txtMeses, txtPrecio,txtTotalTag;

            public SalesViewHolder(View itemView) {
                super(itemView);


                txtMeses = (TextView) itemView.findViewById(R.id.rvMounth);
                txtPrecio = (TextView) itemView.findViewById(R.id.rvPrecioMes);
                txtTotalTag=(TextView)itemView.findViewById(R.id.rvTotalTag);

            }
    }
}

