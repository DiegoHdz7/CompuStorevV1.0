package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Manuel on 27/04/2017.
 */
public class MyChangeLog1 extends DialogFragment
{

    private static final String EXTRA_NEW_STATUS_ID="extra_new_status_Id";
    private static final String EXTRA_NEW_STATUS_DESCRIPTION="extra_new_status_Description";
    private int newStatusId;
    EditText editTxtComments;
    public interface NoticeDialogListener
    {
        void onDialogPositiveClick(int newStatusId, String newStatusDescription);
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
    public MyChangeLog1()
    {
        //necessary constructor

    }
    public static MyChangeLog1 newInstance(int newStatusId,String newStatusDescription){
        MyChangeLog1 fragment = new MyChangeLog1();
        Bundle args= new Bundle();
        args.putInt(EXTRA_NEW_STATUS_ID,newStatusId);
        args.putString(EXTRA_NEW_STATUS_DESCRIPTION,newStatusDescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        newStatusId = bundle.getInt(EXTRA_NEW_STATUS_ID);
        String newStatusDescription = bundle.getString(EXTRA_NEW_STATUS_DESCRIPTION);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();//get the layoutInflater
        View Dialogview = inflater.inflate(R.layout.change_log1,null);
        final TextView txtStatusDescription= (TextView)Dialogview.findViewById(R.id.txt_ChangeLogDescription);
        editTxtComments = (EditText)Dialogview.findViewById(R.id.editText_CommentsDescription);
        txtStatusDescription.setText(newStatusDescription);

        builder.setView(Dialogview);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


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
                        Toast.makeText(getActivity(),"You must type a comment",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        callback.onDialogPositiveClick(newStatusId, changeLogComment);
                        d.dismiss();
                    }
                }
            });

        }
    }
}
