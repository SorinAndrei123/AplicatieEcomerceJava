package com.example.aplicatieecomercejava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {
    Intent intent;
    List<Produs>listaProduse;
    ListView listViewShoppingCart;
    EditText editTextValoareComanda;
    float valoareComanda=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        intent=getIntent();

        listaProduse= (List<Produs>) intent.getSerializableExtra("listaProduse");
        AdaptorShoppingCart adaptorShoppingCart;
        if(listaProduse.size()>0){
            listViewShoppingCart=findViewById(R.id.listViewShoppingCart);
            editTextValoareComanda=findViewById(R.id.editTextValoareComanda);
            for(Produs produs:listaProduse){
                valoareComanda+=produs.getPret();
            }
            adaptorShoppingCart=new AdaptorShoppingCart(getApplicationContext(),R.layout.layoutshoopingcart,listaProduse,getLayoutInflater(),valoareComanda,editTextValoareComanda);
            listViewShoppingCart.setAdapter(adaptorShoppingCart);
            editTextValoareComanda.setText("Valoarea comenzii este de "+String.valueOf(valoareComanda)+" lei.");






        }
    }
}