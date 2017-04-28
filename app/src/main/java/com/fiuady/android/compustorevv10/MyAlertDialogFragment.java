package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.db.Inventory;

/**
 * Created by Manuel on 05/04/2017.
 */
public class MyAlertDialogFragment extends DialogFragment {

    private Inventory inventory;
    private boolean flag;
   private EditText txt_AddCategory;
     DialogClickListener callback;
    /*public interface MyAlertDialogListener{
        public void onDialogPositiveClick(DialogFragment dialogFragment);
        public void onDialogNegativeClick(DialogFragment dialogFragment);
    }
    MyAlertDialogListener mListener; //use this interface to deliver action events
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
             callback =(DialogClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement NoticeDialogListener");

        }
    }

    /*@Override//this fuck me up
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            callback= (DialogClickListener)getTargetFragment();
        }
        catch(ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement interface");

        }
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get the layout inflater
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        View viewInflated= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_category, (ViewGroup)getView(),false);

        //builder.setView(inflater.inflate(R.layout.dialog_add_category,null));
          txt_AddCategory = (EditText) viewInflated.findViewById(R.id.txtAddCategory);
        builder.setView(viewInflated);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //final EditText txt_AddCategory = (EditText) viewInflated.findViewById(R.id.txtAddCategory);
                String newCategoryName = txt_AddCategory.getText().toString();
                inventory = new Inventory(getActivity());
                Product_Categories newCategory = new Product_Categories(inventory.getLastIdCategory()+ 1,newCategoryName);
               // inventory.KnowIfExistsTheCategory(newCategory);
                if(newCategoryName.length()==0)
                {
                    Toast.makeText(getActivity(), "Invalid Category Name!", Toast.LENGTH_SHORT).show();

                }else {
                    if (inventory.KnowIfExistsTheCategory(newCategory)) {

                        inventory.InsertNewCategory(newCategory);
                        // mListener.onDialogPositiveClick(MyAlertDialogFragment.this);
                        //callback.onYesClick();
                        callback.onDialogPositiveClick(MyAlertDialogFragment.this);


                    } else {
                        Toast.makeText(getActivity(), "The category name already exists", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // mListener.onDialogNegativeClick(MyAlertDialogFragment.this);
              //  callback.onNoClick();
                callback.onDialogNegativeClick(MyAlertDialogFragment.this);


            }
        });
        return builder.create();
        //super.onCreateDialog(savedInstanceState);

    }
}
