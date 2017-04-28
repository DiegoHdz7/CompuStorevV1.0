package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Manuel on 27/04/2017.
 */
public class MyChangeLog2 extends DialogFragment{

    private static final String EXTRA_NEW_STATUS_DESCRIPTION_CONFIRMADO="extra_new_status_confirmado";
    private static final String EXTRA_NEW_STATUS_DESCRIPTION_CANCELADO="extra_new_status_cancelado";
    private static final String SAVE_POSITION = "save_position";
    public interface DialogListener
    {
        void onPositiveClick(int newStatusId, String newStatusDescription);
    }
    DialogListener callback;
    private Spinner spinner;
    private EditText editTxtComments;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback=(DialogListener)context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement a NoticeDialogListener");

        }
    }
    public MyChangeLog2()
    {
        //necessary constructor

    }
    public static MyChangeLog2 newInstance(String statusConfirmado,String statusCancelado){
        MyChangeLog2 fragment = new MyChangeLog2();
        Bundle args= new Bundle();
        args.putString(EXTRA_NEW_STATUS_DESCRIPTION_CONFIRMADO,statusConfirmado);
        args.putString(EXTRA_NEW_STATUS_DESCRIPTION_CANCELADO,statusCancelado);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String statusCancelado = bundle.getString(EXTRA_NEW_STATUS_DESCRIPTION_CANCELADO);
        String statusConfirmado = bundle.getString(EXTRA_NEW_STATUS_DESCRIPTION_CONFIRMADO);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();//get the layoutInflater
        View Dialogview = inflater.inflate(R.layout.change_log2,null);
         spinner = (Spinner)Dialogview.findViewById(R.id.spinner_ChangeLogDescription);
         editTxtComments = (EditText)Dialogview.findViewById(R.id.editText_CommentsDescription);
        String[]status = new String[]{statusConfirmado,statusCancelado};
        ArrayAdapter<String> statusArray= new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,status);
        statusArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusArray);
        if(savedInstanceState!=null)
        {
            spinner.setSelection(savedInstanceState.getInt(SAVE_POSITION));//SET THE SELECTED POSITION;
        }

        builder.setView(Dialogview);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //must override this method

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
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d!=null)
        {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String changeLogComment = editTxtComments.getText().toString();
                    if(changeLogComment.length()==0)
                    {
                        Toast.makeText(getActivity(),"You must type a commment",Toast.LENGTH_SHORT).show();
                    }else {
                        int position = spinner.getSelectedItemPosition();
                        int newStatusId;
                        switch (position) {
                            case 0://status confirmado
                                newStatusId = 2;
                                break;
                            default://status cancelado
                                newStatusId = 1;
                                break;

                        }
                        callback.onPositiveClick(newStatusId, changeLogComment);
                        d.dismiss();
                    }
                }
            });

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_POSITION,spinner.getSelectedItemPosition());
    }
}
