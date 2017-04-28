package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel on 19/04/2017.
 */
public class MyShowOrdersClientsDialog extends DialogFragment {
    private static int mSelectedIndex;
    private static final String EXTRA_CUSTOMER="com.fiuady.android.project1.customers_list";

    private static OnDialogSelectorListener dialogSelectorListenerCallback;
    public interface OnDialogSelectorListener{
        void onSelectedOption(int selectedIndex);
    }

    public MyShowOrdersClientsDialog(){
        //has to be left empty
    }

    public static MyShowOrdersClientsDialog newInstance(ArrayList<String> categories, int selected){
        final MyShowOrdersClientsDialog ordersFragment= new MyShowOrdersClientsDialog();
        Bundle args = new Bundle();
        args.putInt("index_selected",selected);
        args.putStringArrayList("categories_list",categories);
        //mResourceArray=categories;
        //mSelectedIndex=selected;
        ordersFragment.setArguments(args);
        return ordersFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            dialogSelectorListenerCallback=(OnDialogSelectorListener)context;
        }
        catch (final ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implemente OnDialogSelectorListener");
        }
    }
    public int getTheCustomerId(String customerName)
    {   int customerId=0;
        Inventory inventory = new Inventory(getActivity());
        List<Customer> customers= inventory.getAllCustomers();
        for(Customer customer:customers)
        {
            if(customerName.equalsIgnoreCase(customer.getFirst_Name().trim()+" "+customer.getLast_Name().trim()))
            {
                customerId = customer.getId();
            }
        }
        return customerId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedIndex = getArguments().getInt("index_selected");
        final ArrayList<String> mResourceArray = getArguments().getStringArrayList("categories_list");
        final AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        Inventory inventory = new Inventory(getActivity());
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mResourceArray);
        builder.setTitle("Select a client");






        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {case 0:
                    dialogSelectorListenerCallback.onSelectedOption(-1);//to select all
                    dialog.dismiss();
                    break;
                    default:

                       // dialogSelectorListenerCallback.onSelectedOption(which);
                        //mResourceArray.get(which);
                        String customerName=arrayAdapter.getItem(which).toString();
                        int customerId = getTheCustomerId(customerName);
                        dialogSelectorListenerCallback.onSelectedOption(customerId);
                        //Toast.makeText(getActivity(),customerName,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;

                }

            }
        });
        return builder.create();
    }
}
