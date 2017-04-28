package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.List;

/**
 * Created by Manuel on 05/04/2017.
 */
public class MyDeleteDialogFragment extends DialogFragment {
    private Inventory inventory;
    DialogClickListener callback; //implements the callback to refresh the recyclerView
    private int GetIdCategory(List<Product_Categories> categories, String category_name){
        int value = 0;
       // Toast.makeText(getActivity(),category_name,Toast.LENGTH_SHORT).show();
        for(Product_Categories cat:categories)
        {
            if(cat.getDescription().equalsIgnoreCase(category_name))
            {
                value = cat.getId();
            }
        }
        return value;
    }
    public MyDeleteDialogFragment(){
//constructor necessary
    }
    public static MyDeleteDialogFragment newInstance(String title, String category_id){
        MyDeleteDialogFragment frag = new MyDeleteDialogFragment();
        Bundle args= new Bundle();
        args.putString("category",category_id);
        args.putString("title",title);
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
        String title = getArguments().getString("title");
       /* String category_description= getArguments().getString("category");//obtain the category
        inventory = new Inventory(getActivity());
        final int IdOfCategory = GetIdCategory(inventory.getAllCategories(),category_description); //to obtain the id
        final Product_Categories category = new Product_Categories(IdOfCategory,category_description);*/

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure you want to delete the category?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category_description= getArguments().getString("category");//obtain the category
                inventory = new Inventory(getActivity());
                int IdOfCategory = GetIdCategory(inventory.getAllCategoriesLara(),category_description); //to obtain the id
                Product_Categories category = new Product_Categories(IdOfCategory,category_description);

                int NumberOfProducts = inventory.GettheNumberOfProductsinaCategory(IdOfCategory);
                if (NumberOfProducts > 0)
                {
                    //inventory.DeleteCategory(category);
                    Toast.makeText(getActivity(),"The category can not be deleted because it has products",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(),String.valueOf(IdOfCategory),Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //Toast.makeText(getActivity(),"The category can not be deleted because it has products",Toast.LENGTH_SHORT).show();
                    inventory.DeleteCategory(category);
                    callback.onDialogPositiveClick(MyDeleteDialogFragment.this);
                }

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    callback.onDialogNegativeClick(MyDeleteDialogFragment.this);
                    dialog.dismiss();
                }
            }
        });
        return alertDialogBuilder.create();
    }
}
