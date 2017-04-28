package com.fiuady.android.compustorevv10;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Manuel on 12/04/2017.
 */
public class MyShowCategoriesDialogFragment extends DialogFragment{

    private static int mSelectedIndex;

    private static OnDialogSelectorListener dialogSelectorListenerCallback;
    public interface OnDialogSelectorListener{
        void onSelectedOption(int selectedIndex);
    }

    public MyShowCategoriesDialogFragment(){
        //has to be left empty
    }

    public static MyShowCategoriesDialogFragment newInstance(ArrayList<String>categories,int selected){
        final MyShowCategoriesDialogFragment fragment= new MyShowCategoriesDialogFragment();
        Bundle args = new Bundle();
        args.putInt("index_selected",selected);
        args.putStringArrayList("categories_list",categories);
        //mResourceArray=categories;
        //mSelectedIndex=selected;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            dialogSelectorListenerCallback=(OnDialogSelectorListener)context;
        }
        catch (final ClassCastException e){
            throw new ClassCastException(context.toString()+" must implemente OnDialogSelectorListener");
        }
    }
    public int getCategoryId(String categoryName)
    {   int categoryId=0;
        Inventory inventory = new Inventory(getActivity());
        List<Product_Categories> product_categories= inventory.getAllCategoriesLara();//get the categories
        for(Product_Categories category:product_categories)
        {
            if(categoryName.trim().equalsIgnoreCase(category.getDescription().trim()))
            {
                categoryId = category.getId();
            }
        }
        return categoryId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedIndex = getArguments().getInt("index_selected");
        ArrayList<String> mResourceArray = getArguments().getStringArrayList("categories_list");
        final AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        Inventory inventory = new Inventory(getActivity());
       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mResourceArray);
        builder.setTitle("Select a category");

       /* Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams windowsManager = dialog.getWindow().getAttributes();
        windowsManager.gravity = Gravity.TOP|Gravity.LEFT;
        windowsManager.x = 100;
        windowsManager.y = 100;*/
       /* Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.TOP | Gravity.RIGHT;
        param.x = 100;
        param.y = 100;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);*/




        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                        dialogSelectorListenerCallback.onSelectedOption(-1);
                        break;
                    default:
                        String categoryName=arrayAdapter.getItem(which).toString();
                        int categoryId = getCategoryId(categoryName);
                        dialogSelectorListenerCallback.onSelectedOption(categoryId);
                        //Toast.makeText(getActivity(),String.valueOf(categoryId),Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;

                }

            }
        });
        return builder.create();
    }
}
