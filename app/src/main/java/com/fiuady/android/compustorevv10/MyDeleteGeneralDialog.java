package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Manuel on 18/04/2017.
 */
public class MyDeleteGeneralDialog extends DialogFragment {
    private static final String EXTRA_TITLE="extra_title";
    private static final String EXTRA_MESSAGE="extra_message";
    DialogClickListener callback;
    public MyDeleteGeneralDialog()
    {
        //necessary constructor
    }
    public static MyDeleteGeneralDialog newInstance(String title,String message){
        MyDeleteGeneralDialog frag = new MyDeleteGeneralDialog();
        Bundle args= new Bundle();
        args.putString(EXTRA_TITLE,title);
        args.putString(EXTRA_MESSAGE, message);
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
        String message = getArguments().getString(EXTRA_MESSAGE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onDialogPositiveClick(MyDeleteGeneralDialog.this);
                dialog.dismiss();

            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    callback.onDialogNegativeClick(MyDeleteGeneralDialog.this);
                    dialog.dismiss();
                }

            }
        });
        return alertDialogBuilder.create() ;
    }
}
