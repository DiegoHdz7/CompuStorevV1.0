package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Manuel on 26/04/2017.
 */
public class MyGeneralDialog extends DialogFragment{
    private static final String EXTRA_TITLE="extra_title";
    private static final String EXTRA_MESSAGE="extra_message";
    private static final String DIALOG_CODE="dialog_code";
    DialogOnClickListener callback;
    public MyGeneralDialog()
    {
        //necessary constructor
    }
    public static MyGeneralDialog newInstance(String title,String message, int code){
        MyGeneralDialog frag = new MyGeneralDialog();
        Bundle args= new Bundle();
        args.putString(EXTRA_TITLE,title);
        args.putString(EXTRA_MESSAGE, message);
        args.putInt(DIALOG_CODE,code);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback =(DialogOnClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement NoticeDialogListener");

        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(EXTRA_TITLE);
        String message = getArguments().getString(EXTRA_MESSAGE);
        final int code = getArguments().getInt(DIALOG_CODE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onDialogPositiveClick(MyGeneralDialog.this,getArguments().getInt(DIALOG_CODE));
                dialog.dismiss();
            }

        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    callback.onDialogNegativeClick(MyGeneralDialog.this,getArguments().getInt(DIALOG_CODE));
                    dialog.dismiss();
                }

            }
        });
        return alertDialogBuilder.create() ;
    }
}
