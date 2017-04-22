package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fiuady.android.compustorevv10.db.Inventory;

/**
 * Created by Aarón Madera on 01/04/2017.
 */

public class NewClientActivity extends AppCompatActivity{
    public static final int CODE_NEWCLIENT = 41;
    private boolean newClient = true; //Se usará este booleano para saber si se agrega o modifica
    private Inventory inventory;


////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_new);

        final EditText edtFirstName = (EditText) findViewById(R.id.etx_clientFN);
        final EditText edtLastName = (EditText)findViewById(R.id.etx_fieldLN);
        final EditText edtAddress = (EditText)findViewById(R.id.etx_clientAd);
        final EditText edtPhone1A = (EditText)findViewById(R.id.etx_clientT1_a);
        final EditText edtPhone1B = (EditText)findViewById(R.id.etx_clientT1_b);
        final EditText edtPhone2A = (EditText)findViewById(R.id.etx_clientT2_a);
        final EditText edtPhone2B = (EditText)findViewById(R.id.etx_clientT2_b);
        final EditText edtPhone3A = (EditText)findViewById(R.id.etx_clientT3_a);
        final EditText edtPhone3B = (EditText)findViewById(R.id.etx_clientT3_b);
        final EditText edtEMail = (EditText)findViewById(R.id.etx_clientEm);

        Button btnSave = (Button) findViewById(R.id.btn_clientSave);
        Button btnCancel = (Button) findViewById(R.id.btn_clientCancel);

        //Inicializar los valores de los EditTexts con Hints
        edtFirstName.setHint("Campo Necesario");
        edtLastName.setHint("Campo Necesario");
        edtAddress.setHint("Campo Necesario");

        edtPhone1A.setHint("###");
        edtPhone2A.setHint("###");
        edtPhone3A.setHint("###");

        edtPhone1B.setHint("Opcional");
        edtPhone2B.setHint("Opcional");
        edtPhone3B.setHint("Opcional");

        edtEMail.setHint("Opcional");

        //Obtener los valores de los EditTexts

        inventory = new Inventory(getApplicationContext());


        /*Configuración del intent*/
        Intent i = getIntent();

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewClientActivity.this, "¡Acción Cancelada!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (edtFirstName.getText().toString().isEmpty()| edtLastName.getText().toString().isEmpty() |
                        edtAddress.getText().toString().isEmpty()) {
                    Toast.makeText(NewClientActivity.this, "Llene todos los campos necesarios, por favor", Toast.LENGTH_SHORT).show();
                    Log.i("Values", edtAddress.getText().toString() + edtAddress.getText().toString());
                }
                else{
                    String auxEdtFN = edtFirstName.getText().toString();
                    String auxEdtLN = edtLastName.getText().toString();
                    String auxEdtAd = edtAddress.getText().toString();

                    String auxEdtP1A = edtPhone1A.getText().toString();
                    String auxEdtP1B = edtPhone1B.getText().toString();

                    String auxEdtP2A = edtPhone2A.getText().toString();
                    String auxEdtP2B = edtPhone2B.getText().toString();

                    String auxEdtP3A = edtPhone3A.getText().toString();
                    String auxEdtP3B = edtPhone3B.getText().toString();



                    String auxEdtP1 = "NULL";
                    if(!(edtPhone1A.getText().toString().isEmpty() & edtPhone1B.getText().toString().isEmpty()))
                    {auxEdtP1 = edtPhone1A.getText().toString() + "-" + edtPhone1B.getText().toString();}

                    String auxEdtP2 = "NULL";
                    if(!(edtPhone2A.getText().toString().isEmpty() & edtPhone2B.getText().toString().isEmpty()))
                    {auxEdtP2 = edtPhone2A.getText().toString() + "-" + edtPhone2B.getText().toString();}

                    String auxEdtP3 = "NULL";
                    if(!(edtPhone3A.getText().toString().isEmpty() & edtPhone3B.getText().toString().isEmpty()))
                    {auxEdtP3 = edtPhone3A.getText().toString() + "-" + edtPhone3B.getText().toString();}

                    String auxEdtEM = edtEMail.getText().toString();
                    if(edtEMail.getText().toString().isEmpty()) {auxEdtEM="NULL";}


                    Log.i("ValuesOnSave=", edtFirstName.getText().toString() + edtLastName.getText().toString() + edtAddress.getText().toString() + auxEdtP1 + auxEdtP2 + auxEdtP3 + auxEdtEM);
                    Log.i("ValuesOnSave.getClass()", edtFirstName.getText().toString().getClass().toString() + edtLastName.getText().toString().getClass().toString() +
                            edtAddress.getText().toString().getClass().toString()
                            + auxEdtP1.getClass().toString() + auxEdtP2.getClass().toString() + auxEdtP3.getClass().toString());

                    //inventory.setNewClient(String.valueOf("Mad"),String.valueOf("Aa"),String.valueOf("C.121"),String.valueOf("NULL"),String.valueOf("NULL"),String.valueOf("NULL"),String.valueOf("NULL"));
                    inventory.setNewClient(edtLastName.getText().toString(),edtFirstName.getText().toString(),
                            edtAddress.getText().toString(),auxEdtP1,auxEdtP2,auxEdtP3,auxEdtEM);
                    //Log.i("ValuesOnSave=", auxEdtFN + auxEdtLN + auxEdtAd + auxEdtP1 + auxEdtP2 + auxEdtP3 + auxEdtEM);

                    Toast.makeText(NewClientActivity.this, "Nuevo Cliente Agregado Exitosamente", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


