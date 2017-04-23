package com.fiuady.android.compustorevv10;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.fiuady.android.compustorevv10.db.Inventory;
import com.fiuady.android.compustorevv10.db.InventoryHelper;

public class MainActivity extends AppCompatActivity {
/////
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private InventoryHelper inventoryHelper;
    private  ImageButton ReportsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuración del ToolBar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.tlb_main);
        setSupportActionBar(mainToolbar);

        ReportsBtn = (ImageButton) findViewById(R.id.ibt_reports);

        /* Petición para escribir en el almacenamiento externo -->*/
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
        /* <-- Petición para escribir en el almacenamiento externo */

         InventoryHelper.backupDatabaseFile(getApplicationContext());

        ImageButton imbClients = (ImageButton) findViewById(R.id.ibt_clients);

        imbClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClientsActivity.class);
                startActivityForResult(i, ClientsActivity.CODE_CLIENTS);
            }
        });
        //Log.d("MainActivity", "OnCreate()....");



        ReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),ReportsActivity.class);
                startActivity(i);

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            {
                //Si la petición se cancela, los arrays resultantes estarán vacíos.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {

                }
                return;
            }
        }
    }


}
