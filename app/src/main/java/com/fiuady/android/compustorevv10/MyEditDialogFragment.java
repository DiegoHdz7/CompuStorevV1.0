package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.List;

/**
 * Created by Manuel on 05/04/2017.
 */
public class MyEditDialogFragment extends DialogFragment {
    private Inventory inventory;
    private EditText txt_EditCategory;
    private int IdOfCategory;
    DialogClickListener callback;
    private int GetIdCategory(List<Product_Categories> categories, String category_name){
        int value = 0;

        for(Product_Categories cat:categories)
        {
            if(cat.getDescription().equalsIgnoreCase(category_name))
            {
                value = cat.getId();
            }
        }
       // Toast.makeText(getActivity(),String.valueOf(value),Toast.LENGTH_SHORT).show();
        return value;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback =(DialogClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement NoticeDialogListener");

        }

    }

    public MyEditDialogFragment()
    {
        //necessary constructor

    }
    public static MyEditDialogFragment newInstance(String category_id){
        MyEditDialogFragment fragment = new MyEditDialogFragment();
        Bundle args= new Bundle();
        args.putString("category_description",category_id);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        //EditText txt_EditCategory;
        String category_id = getArguments().getString("category_description");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // String category_id = getArguments().getString("category_id");

        //View viewInflated= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_category, (ViewGroup)getView(),false);
        LayoutInflater inflater= getActivity().getLayoutInflater();//this is the correct method
        View content = inflater.inflate(R.layout.dialog_edit_category,null);
        alertDialog.setView(content);
        txt_EditCategory =(EditText)content.findViewById(R.id.txtEditCategory);
        txt_EditCategory.setText(category_id);
        String category_description = txt_EditCategory.getText().toString();
        txt_EditCategory.setSelection(txt_EditCategory.getText().length());//set the cursor at the end
        inventory = new Inventory(getActivity());
        List<Product_Categories> categories = inventory.getAllCategoriesLara();
         IdOfCategory = GetIdCategory(categories,category_description);
        //txt_EditCategory= (EditText) viewInflated.findViewById(R.id.txtEditCategory);
        //AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
       // LayoutInflater inflater = getActivity().getLayoutInflater();
        //inflater.inflate(R.layout.dialog_edit_category,null);

        //alertDialog.setView(viewInflated);
       // txt_EditCategory.setText(category_id);

        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //inventory = new Inventory(getActivity());
                //int IdOfCategory = GetIdCategory(inventory.getAllCategories(),getArguments().getString("category_id"));
                //Toast.makeText(getActivity(),IdOfCategory,Toast.LENGTH_SHORT).show();
                //Product_Categories category = new Product_Categories(IdOfCategory,txt_EditCategory.getT);
                if(txt_EditCategory.getText().toString().length()==0){
                    Toast.makeText(getActivity(),"Invalid Category Name!",Toast.LENGTH_SHORT).show();
                }
                 else{
                   // inventory = new Inventory(getActivity());
                    //int IdOfCategory = GetIdCategory(inventory.getAllCategories(),getArguments().getString("category_id"));
                    Product_Categories category = new Product_Categories(IdOfCategory,txt_EditCategory.getText().toString());
                    if(inventory.KnowIfExistsTheCategory(category))
                    {
                        inventory.UpdateCategory(category);
                        callback.onDialogPositiveClick(MyEditDialogFragment.this);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"This Category already exists!",Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null ) {
                    callback.onDialogNegativeClick(MyEditDialogFragment.this);
                    dialog.dismiss();
                }

            }
        });

       return alertDialog.create();

    }


}
