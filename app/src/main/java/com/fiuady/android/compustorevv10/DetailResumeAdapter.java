package com.fiuady.android.compustorevv10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fiuady.android.compustorevv10.db.Inventory;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Diego Hernandez on 23/04/2017.
 */

public class DetailResumeAdapter extends RecyclerView.Adapter<DetailResumeAdapter.DetailResumeViewHolder> implements View.OnClickListener {

    List<DetailInfo> infoList;
    int rvId;
    private View.OnClickListener listener;

    public DetailResumeAdapter(List<DetailInfo> infoList, int RVId) {
        this.infoList = infoList;
        this.rvId = RVId;
    }



    @Override
    public DetailResumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (rvId == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_detail_resume, parent, false);
            DetailResumeAdapter.DetailResumeViewHolder holder = new DetailResumeAdapter.DetailResumeViewHolder(v);
            v.setOnClickListener(this);
            return holder;
        }

        if (rvId == 2) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_assemblies, parent, false);
            DetailResumeAdapter.DetailResumeViewHolder holder = new DetailResumeAdapter.DetailResumeViewHolder(v);
            return holder;
        }

        return null;

    }

    @Override
    public void onBindViewHolder(DetailResumeViewHolder holder, int position) {



        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String fecha = df.format(infoList.get(position).getDate());

        if (rvId == 1) {
            holder.txtAddress.setText(String.valueOf(infoList.get(position).getAddress()));
            holder.txtPrecio.setText(String.valueOf(infoList.get(position).getTotalPrice()));
            holder.txtAssembly.setText(String.valueOf(infoList.get(position).getAssemblyId()));
            holder.txtClient.setText(String.valueOf(infoList.get(position).getClientId()));
            holder.txtdate.setText(String.valueOf(infoList.get(position).getDate()));
            holder.txtdate.setText(fecha);
            holder.txtFstName.setText(String.valueOf(infoList.get(position).getFirstName()));
            holder.txtLstName.setText(String.valueOf(infoList.get(position).getLastName()));
            holder.txtSdescription.setText(String.valueOf(infoList.get(position).getStatus_Description()));
            holder.txtOrder.setText(String.valueOf(infoList.get(position).getOrderId()));

        }

        if(rvId==2)
        {
            holder.txtAssemblyPrice.setText(String.valueOf(infoList.get(position).getTotalPrice()));
            holder.txtAssemblyId.setText(String.valueOf(infoList.get(position).getAssemblyId()));


        }


    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public DetailInfo GetItemOfList(int index)
    {
        return infoList.get(index);

    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    public static class DetailResumeViewHolder extends RecyclerView.ViewHolder  {

        private TextView txtOrder,txtAssembly,txtClient,txtFstName,txtLstName,txtAddress,txtdate,txtSdescription;
        private TextView txtPrecio;


        private TextView txtAssemblyId, txtAssemblyPrice;

        public DetailResumeViewHolder(View itemView) {
            super(itemView);


            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            txtPrecio = (TextView) itemView.findViewById(R.id.txtPrecio);
            txtAssembly = (TextView) itemView.findViewById(R.id.txtAssemblyId);
            txtClient=(TextView) itemView.findViewById(R.id.txtCustomerId);
            txtdate=(TextView) itemView.findViewById(R.id.txtDate);
            txtFstName= (TextView) itemView.findViewById(R.id.txtFisrtName);
            txtLstName=(TextView)itemView.findViewById(R.id.txtLastName);
            txtSdescription = (TextView) itemView.findViewById(R.id.txtOrderStatus);
            txtOrder = (TextView) itemView.findViewById(R.id.txtOrderId);

            txtAssemblyId = (TextView) itemView.findViewById(R.id.txtAssembly_Id_a);
            txtAssemblyPrice = (TextView) itemView.findViewById(R.id.txtAssemblyPrice_a);


        }
    }
}
