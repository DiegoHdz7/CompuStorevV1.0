package com.fiuady.android.compustorevv10.Productos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.R;
import com.fiuady.android.compustorevv10.db.Inventory;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    }*/

    private Button btnUpdate, btnCancel;
    private Spinner spnUpCategory;
    private EditText txtDescription, txtPrice;
    private TextView txt;



    public static final String EXTRA_ID = "com.fiuady.android.compustorevv10.Productos.Product.extra_IdProduct";
    public static final String EXTRA_Description = "com.fiuady.android.compustorevv10.Productos.Product.extra_Description";
    public static final String EXTRA_Precio = "com.fiuady.android.compustorevv10.Productos.Product.extra_Precio";
    public static final String EXTRA_Category = "com.fiuady.android.compustorevv10.Productos.Product.extra_category_product";
    public static final int CODE_UPDATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        txt= (TextView) findViewById(R.id.desUpTag);
        btnCancel = (Button) findViewById(R.id.btnCancelUp);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        spnUpCategory = (Spinner) findViewById(R.id.spnUpdate);
        txtDescription = (EditText) findViewById(R.id.UpDescriptionTxt);
        txtPrice = (EditText) findViewById(R.id.UpPriceTxt);


        final Intent i = getIntent();
        final int  productID = i.getIntExtra(EXTRA_ID,0);
        final String productDescription = i.getStringExtra(EXTRA_Description);
        final String categoryDescription = i.getStringExtra(EXTRA_Category);
        final String precio = i.getStringExtra(EXTRA_Precio);


        final Inventory manager = new Inventory(this);

        final ArrayList<String> catDes = new ArrayList<String>();
        for (Proudct_Category pc: manager.getAllCategories()
                ) {

            catDes.add(pc.description);

        }

        int catpos=0;

        for (int x=0; x<catDes.size();x++)
        {
            catpos=x;
            if (catDes.get(x).equals(categoryDescription))
            {
                break;
            }
        }


        spnUpCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,catDes));
        txtDescription.setText(productDescription);
        txtPrice.setText(String.valueOf(precio));

        spnUpCategory.setSelection(catpos);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateActivity.this,"Cancelado",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String NewCategory= String.valueOf(manager.GetCategoryId(spnUpCategory.getSelectedItem().toString()));
                String NewDes= txtDescription.getText().toString();
                String NewPrice=String.valueOf(Double.valueOf(txtPrice.getText().toString())*100);

                manager.UpdateProduct(NewDes,productID,NewPrice,NewCategory);


                if (NewDes.length()>0 && NewPrice.length() >0 && !NewPrice.equals(".")) {

                        manager.UpdateProduct(NewDes,productID,NewPrice,NewCategory);
                        Toast.makeText(getApplicationContext(), "Producto modificado correctamente", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();

                    }

                    else  Toast.makeText(getApplicationContext(),"Campos vacios o inválidos",Toast.LENGTH_SHORT).show();

            }
        });


    }



}
