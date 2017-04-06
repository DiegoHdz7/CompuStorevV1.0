package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aarón Madera on 01/04/2017.
 */

public class NewClientActivity extends AppCompatActivity{
    public static final int CODE_NEWCLIENT = 41;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_new);

        /*Configuración del intent*/
        Intent i = getIntent();
    }
}
