package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Manuel on 15/04/2017.
 */
public class MyBackPressDialog extends DialogFragment {
    private static final String EXTRA_TITLE="extra_title";
    DialogClickListener callback;
    public MyBackPressDialog()
    {
        //necessary constructor
    }
    public static MyBackPressDialog newInstance(String title){
        MyBackPressDialog frag = new MyBackPressDialog();
        Bundle args= new Bundle();
        args.putString(EXTRA_TITLE,title);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback =(DialogClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement NoticeDialogListener");

        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(EXTRA_TITLE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure you want to exit? All the information will be deleted");
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onDialogPositiveClick(MyBackPressDialog.this);
                dialog.dismiss();

            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    callback.onDialogNegativeClick(MyBackPressDialog.this);
                    dialog.dismiss();
                }

            }
        });
        return alertDialogBuilder.create() ;
    }
}
