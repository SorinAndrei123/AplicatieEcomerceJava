package com.example.aplicatieecomercejava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button butonAddProduse,butonVizualizareProduse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butonAddProduse=findViewById(R.id.buttonAdaugaProduse);
        butonVizualizareProduse=findViewById(R.id.buttonVizualizareProduse);
        butonAddProduse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ActivityAddProduse.class);
                startActivity(intent);
            }
        });
        butonVizualizareProduse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AfisareProduseActivity.class);
                startActivity(intent);

            }
        });


    }
}