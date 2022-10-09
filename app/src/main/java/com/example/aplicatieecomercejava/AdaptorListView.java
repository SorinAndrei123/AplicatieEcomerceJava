package com.example.aplicatieecomercejava;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class AdaptorListView extends ArrayAdapter {
    Context context;
    List<Produs> listaProduse;
    int resource;
    LayoutInflater inflater;
    StorageReference storageReference;
    public AdaptorListView(@NonNull Context context, int resource, @NonNull List<Produs> objects, LayoutInflater inflater,StorageReference storageReference) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listaProduse=objects;
        this.inflater=inflater;
        this.storageReference=storageReference;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listviewView = inflater.inflate(R.layout.layoutadaptorproduse,null,true);

        TextView nume=listviewView.findViewById(R.id.tviewNumeProdus);
        TextView descriere=listviewView.findViewById(R.id.tviewDescriereProdus);
        TextView pret=listviewView.findViewById(R.id.tviewPret);
        CheckBox checkBox=listviewView.findViewById(R.id.checkBoxEsteDisponibil);
        ImageView imageView=listviewView.findViewById(R.id.imageViewAdaptorPoza);

        Produs produs=listaProduse.get(position);
        nume.setText("Nume produs: "+produs.getNume());
        descriere.setText("Descriere: "+produs.getDescriere());
        pret.setText("Pret: "+ String.valueOf(produs.getPret()));
        if(produs.isEsteDisponibil()==true){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);
        }
        checkBox.setEnabled(false);
        Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.BLACK).borderWidthDp(3).cornerRadiusDp(30).oval(false).build();
        storageReference.child(this.listaProduse.get(position).getNume()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().transform(transformation).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
     

        return listviewView;
    }

}
