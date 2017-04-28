package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel on 19/04/2017.
 */
public class MyShowStatusOrdersDialog extends DialogFragment implements MySpinnerAdapter.OnCheckedChange{
    private static final String STATUS_ARRAY_KEY="status_array_key";
    private static OnPositiveClick mCallback;
    private Inventory inventory;
    MySpinnerAdapter mySpinnerAdapter;
    ArrayList<StateView> Views= new ArrayList<StateView>();
    private ArrayList mSelectedItems= new ArrayList();
    public interface OnPositiveClick
    {
        void OnPositiveClickSelection(ArrayList mItemsSelected);
        void OnCancelClickSelection();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback=(OnPositiveClick) context;
        }
        catch (final ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implemente OnDialogSelectorListener");
        }
    }
    private ArrayList<StateView>getAllViews()
    {
        ArrayList<StateView> listViews= new ArrayList<StateView>();

        List<Order_Status> statusList = inventory.getAllOrderStatus();
        for(Order_Status order_status: statusList)
        {
            StateView stateView = new StateView(order_status.getDescription(),false);
            listViews.add(stateView);
        }
        return listViews;



    }
    public MyShowStatusOrdersDialog(){
        //has to be left empty
    }

    public static MyShowStatusOrdersDialog newInstance(ArrayList<StateView> stateViews){
        final MyShowStatusOrdersDialog statusOrdersFrag= new MyShowStatusOrdersDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STATUS_ARRAY_KEY,stateViews);
        statusOrdersFrag.setArguments(args);
        return statusOrdersFrag;
    }
    public void OnCheckedChange(int value)//this is to fill the array
    {
        //Toast.makeText(getActivity(),String.valueOf(value),Toast.LENGTH_SHORT).show();
        mSelectedItems.add(value);
    }
    public void OnCheckedNegativeChange(int value)//This is to fill the array
    {
        //Toast.makeText(getActivity(),String.valueOf(value),Toast.LENGTH_SHORT).show();
        if (mSelectedItems.contains(value)) {
            // Else, if the item is already in the array, remove it
            mSelectedItems.remove(Integer.valueOf(value));
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       //Bundle bundle = new Bundle();

        //final ArrayList statusPicked = new ArrayList();
       //ArrayList<String>mResourceArray=bundle.getStringArrayList(STATUS_ARRAY_KEY);


        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        inventory = new Inventory(getActivity());
        Views = getArguments().getParcelableArrayList(STATUS_ARRAY_KEY);
        mySpinnerAdapter = new MySpinnerAdapter(getActivity(), 0, Views);
        mySpinnerAdapter.setOnCheckedChange(this);
        builder.setTitle("Select a status");
        builder.setAdapter(mySpinnerAdapter,null);
       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mResourceArray);
        /*builder.setMultiChoiceItems(R.array.status, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    statusPicked.add(which);
                } else if (statusPicked.contains(which)) {
                    // Else, if the item is already in the array, remove it
                    statusPicked.remove(Integer.valueOf(which));
                }

            }
        });*/
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK, so save the mSelectedItems results somewhere
                // or return them to the component that opened the dialog
                VerifyIfIsOnTheList();
                mCallback.OnPositiveClickSelection(mSelectedItems);
                dialog.dismiss();

            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        mCallback.OnCancelClickSelection();
                        dialog.cancel();

                    }
                });

        return builder.create();
    }
    private void VerifyIfIsOnTheList()
    {
        ArrayList<StateView> states= mySpinnerAdapter.getListState();
        for(StateView state:states)
        {
            int index = states.indexOf(state);
            if(state.isSelected()&& (mSelectedItems.contains(index)==false))
            {
                mSelectedItems.add(index);

            }
        }

    }
}
