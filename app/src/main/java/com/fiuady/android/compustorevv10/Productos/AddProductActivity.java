package com.fiuady.android.compustorevv10.Productos;

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

public class AddProductActivity extends AppCompatActivity {


    Spinner spnCat;
    EditText txtNewPrice, txtNewDes;
    Button btnAdd, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        spnCat = (Spinner) findViewById(R.id.spinnerAdd);
        txtNewPrice = (EditText) findViewById(R.id.addPriceTxt);
        txtNewDes=(EditText) findViewById(R.id.addDesTxt);
        btnAdd = (Button) findViewById(R.id.AddButton);
        btnCancel = (Button) findViewById(R.id.cancelButton);




        final Inventory manager = new Inventory(getApplicationContext());

        final ArrayList<String> catDes = new ArrayList<String>();
        for (Proudct_Category pc: manager.getAllCategories()
                ) {

            catDes.add(pc.description);

        }

        spnCat.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,catDes));



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String NewDescription = txtNewDes.getText().toString();
                final String NewPrice = String.valueOf(Double.valueOf(txtNewPrice.getText().toString())*100);
                final String category_Id = String.valueOf(manager.GetCategoryId(spnCat.getSelectedItem().toString()));


                if (NewDescription.length()>0 && NewPrice.length() >0 && !NewPrice.equals(".")) {
                    if (manager.CheckDescription(NewDescription)) {
                        manager.AddProduct(category_Id, NewDescription, NewPrice);
                        Toast.makeText(getApplicationContext(), "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    else  Toast.makeText(AddProductActivity.this,"Descripción ya existente, por favor ingrese una diferente",Toast.LENGTH_SHORT).show();
                }

                else
                {

                    Toast.makeText(AddProductActivity.this,"Campos vacios o incorrectos",Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddProductActivity.this,"Cancelado",Toast.LENGTH_SHORT).show();finish();
            }
        });



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_SPN_ADD_PRODUCT_POS, spnCat.getSelectedItemPosition());




    }

    public static String KEY_SPN_ADD_PRODUCT_POS= "KEY_SPN_ADD_PRODUCT_POS";


}
