package com.example.aplicatieecomercejava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdaptorShoppingCart extends ArrayAdapter {
    Context context;
    List<Produs> listaProduse;
    int resource;
    LayoutInflater inflater;
    float valoareComanda;
    EditText editText;

    public AdaptorShoppingCart(@NonNull Context context, int resource, @NonNull  List<Produs> objects, LayoutInflater inflater,float valoareComanda,EditText editText) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listaProduse=objects;
        this.inflater=inflater;
        this.editText=editText;
        this.valoareComanda=valoareComanda;



    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listviewView = inflater.inflate(R.layout.layoutshoopingcart,null,true);

        TextView numeProdus=listviewView.findViewById(R.id.tvNumeCart);
        TextView cantitate=listviewView.findViewById(R.id.tvCantitateCart);
        @SuppressLint("MissingInflatedId") ImageView plus=listviewView.findViewById(R.id.imageViewAddButton);
        @SuppressLint("MissingInflatedId") ImageView minus=listviewView.findViewById(R.id.imageViewMinusButton);
        numeProdus.setText(listaProduse.get(position).getNume());
       cantitate.setText("1");
       plus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                       valoareComanda+=listaProduse.get(position).getPret();
                       int new_value= Integer.valueOf((String) cantitate.getText())+1;
                      cantitate.setText(String.valueOf(new_value));
                       editText.setText("Valoarea comenzii este de "+String.valueOf(valoareComanda)+" lei.");







           }
       });
       minus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               List<Produs>produsSters=new ArrayList<>();
                       valoareComanda-=listaProduse.get(position).getPret();

                       int new_value= Integer.valueOf((String) cantitate.getText())-1;
                       if(new_value<=0){
                           produsSters.add(listaProduse.get(position));

                       }

                       cantitate.setText(String.valueOf(new_value));
                       editText.setText("Valoarea comenzii este de "+String.valueOf(valoareComanda)+" lei.");




               listaProduse.removeAll(produsSters);




           }
       });
        return listviewView;
    }

}
