

package com.fiuady.android.compustorevv10.Productos;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.LoginFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.fiuady.android.compustorevv10.Product;
import com.fiuady.android.compustorevv10.R;
import com.fiuady.android.compustorevv10.db.Inventory;


 // Created by Diego Hernandez on 27/04/2017.


/*
public class DialogAlerta extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.stock_activity,null);

        builder.setView(view);

        NumberPicker np = (NumberPicker) view.findViewById(R.id.npStock);
        np.setMinValue();
        np.setMaxValue(100);

        builder.setView(inflater.inflate(R.layout.stock_activity,null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });




        return builder.create();
    }




}
*/



import com.fiuady.android.compustorevv10.R;
import com.fiuady.android.compustorevv10.db.Inventory;

public  class DialogAlerta extends DialogFragment {


    public static String EXTRA_ProductQty= "Get_Product_Qty";
    public static String EXTRA_ProductId= "Get_Product_Id";
    public static boolean btnDialog=false;


    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    Bundle bundle = getArguments();

    public static DialogAlerta newInstance(int id, int qty){
        DialogAlerta fragment = new DialogAlerta();
        Bundle args= new Bundle();
        args.putInt(EXTRA_ProductQty,qty);
        args.putInt(EXTRA_ProductId,id);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.stock_activity,null);





        final NumberPicker np = (NumberPicker) view.findViewById(R.id.npStock);

        Bundle bundle = getArguments();
        final int qty= bundle.getInt(EXTRA_ProductQty);
        final int Pid= bundle.getInt(EXTRA_ProductId);


        np.setMinValue(qty);
        np.setMaxValue(100);



        builder.setView(view);






        // Add action buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Inventory manager = new Inventory(getActivity().getApplicationContext());
                manager.AddStockProduct(Pid,String.valueOf(np.getValue()));
                mListener.onDialogPositiveClick(DialogAlerta.this);
                dialog.dismiss();



            }
        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });




        return builder.create();
    }








}


///////////////////////////




