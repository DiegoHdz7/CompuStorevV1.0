package com.fiuady.android.compustorevv10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ReportsActivity extends AppCompatActivity {

    private ImageButton btnFaltantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        btnFaltantes=(ImageButton) findViewById(R.id.btnMissing);

        btnFaltantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportsActivity.this,MissingProductsActivity.class);
                startActivity(i);
            }
        });
    }
}
