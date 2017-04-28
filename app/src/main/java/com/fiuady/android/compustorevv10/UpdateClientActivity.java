package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fiuady.android.compustorevv10.db.Inventory;

public class UpdateClientActivity extends AppCompatActivity {


    EditText txtNum1,txtNum2,txtNum3,txtAddress,txtFName,txtLName,txtLada1,txtLada2,txtLada3,txtEmail;
    Button btnAdd, btnCancel;

    public static String EXTRA_CLIENT_ID_TO_UP = "EXTRA_CLIENT_ID_TO_UP";
    public static String EXTRA_FIRST_NAME_CLIENT = "EXTRA_FIRST_NAME_CLIENT";
    public static String EXTRA_LAST_NAME_CLIENT = "EXTRA_LAST_NAME_CLIENT";
    public static String EXTRA_PHONE1 = "EXTRA_PHONE1";
    public static String EXTRA_PHONE2 = "EXTRA_PHONE2";
    public static String EXTRA_PHONE3 = "EXTRA_PHONE3";
    public static String EXTRA_LADA1 = "EXTRA_LADA1";
    public static String EXTRA_LADA2 = "EXTRA_LADA2";
    public static String EXTRA_LADA3 = "EXTRA_LADA3";
    public static String EXTRA_EMAIL_CLIENT = "EXTRA_EMAIL_CLIENT";
    public static String EXTRA_ADDRRES_CLIENT= "EXTRA_ADDRRES";
    public static int CODE_UPDATE_CUSTOMER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);
        txtNum1 = (EditText) findViewById(R.id.etx_clientT1_b_up);
        txtNum2 = (EditText) findViewById(R.id.etx_clientT2_b_up);
        txtNum3 = (EditText) findViewById(R.id.etx_clientT3_b_up);

        txtLada1 = (EditText) findViewById(R.id.etx_clientT1_a_up);
        txtLada2= (EditText) findViewById(R.id.etx_clientT2_a_up);
        txtLada3 = (EditText) findViewById(R.id.etx_clientT3_a_up);

        txtAddress = (EditText) findViewById(R.id.etx_clientAd_up);
        txtFName = (EditText)findViewById(R.id.etx_clientFN_up);
        txtLName = (EditText)findViewById(R.id.etx_fieldLN_up);
        txtEmail = (EditText) findViewById(R.id.etx_clientEm_up);

        btnAdd = (Button) findViewById(R.id.btn_clientSave_up);
        btnCancel=(Button) findViewById(R.id.btn_clientCancel_up);


        final Inventory manager = new Inventory(getApplicationContext());


        Intent i = getIntent();

        final int ClientId = i.getIntExtra(EXTRA_CLIENT_ID_TO_UP,0);
        final  String FNAME = i.getStringExtra(EXTRA_FIRST_NAME_CLIENT);
        final  String LNAME = i.getStringExtra(EXTRA_LAST_NAME_CLIENT);


        final String ADDRESS = i.getStringExtra(EXTRA_ADDRRES_CLIENT);
        final String EMAIL = i.getStringExtra(EXTRA_EMAIL_CLIENT);


        txtAddress.setText(ADDRESS);
        txtFName.setText(FNAME);
        txtLName.setText(LNAME);
        txtEmail.setText(EMAIL);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel1=txtLada1.getText().toString()+"-"+txtNum1.getText().toString();
                String tel2=txtLada2.getText().toString()+"-"+txtNum2.getText().toString();
                String tel3=txtLada3.getText().toString()+"-"+txtNum3.getText().toString();

                String FName = txtFName.getText().toString();
                String LName = txtLName.getText().toString();
                String Address = txtAddress.getText().toString();
                String Email = txtEmail.getText().toString();
                /*String Phone1 = txtNum1.getText().toString();
                String Phone2 = txtNum2.getText().toString();
                String Phone3 = txtNum3.getText().toString();
                String Lada1 = txtLada1.getText().toString();
                String Lada2 = txtLada2.getText().toString();
                String Lada3 = txtLada3.getText().toString();*/




                manager.UpdateClient(ClientId,FName,LName,Address,tel1,tel2,tel3,Email);
                setResult(RESULT_OK);
                finish();



            }
        });






    }
}
