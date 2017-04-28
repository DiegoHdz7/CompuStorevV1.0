package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Created by Manuel on 14/04/2017.
 */
public class MyNumberPickerDialog extends DialogFragment {
    private static final String EXTRA_CURRENT_QTY="current_quantity";
    private static final String SAVE_CURRENT_NUMBER="current_Number";
    private NumberPicker np;
    public interface NoticeDialogListener
    {
        void onDialogPositiveClick(int quantity);
    }
    NoticeDialogListener callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback=(NoticeDialogListener)context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement a NoticeDialogListener");

        }
    }
    public MyNumberPickerDialog()
    {
        //necessary constructor

    }
    public static MyNumberPickerDialog newInstance(int quantity){
        MyNumberPickerDialog fragment = new MyNumberPickerDialog();
        Bundle args= new Bundle();
        args.putInt(EXTRA_CURRENT_QTY,quantity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int currentquantity = bundle.getInt(EXTRA_CURRENT_QTY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();//get the layoutInflater
        View Dialogview = inflater.inflate(R.layout.dialog_edit_quantitypicker,null);
        np = (NumberPicker)Dialogview.findViewById(R.id.quantity_NumberPicker);
        np.setMinValue(1);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(false);
        if(savedInstanceState != null)
        {
            np.setValue(savedInstanceState.getInt(SAVE_CURRENT_NUMBER));
        }
        else {
            np.setValue(currentquantity);
        }
        builder.setView(Dialogview);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newquantity = np.getValue();
                callback.onDialogPositiveClick(newquantity);
                dialog.dismiss();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        //super.onCreateDialog(savedInstanceState)
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_CURRENT_NUMBER,np.getValue());
    }
}
