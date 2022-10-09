package com.example.aplicatieecomercejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class ActivityAddProduse extends AppCompatActivity {
    EditText nume,descriere,pret;
    Button adaugaPoza,adaugaProdus,alegePoza;
    DatabaseReference databaseReference;
    CheckBox checkBox;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produse);
        databaseReference=FirebaseDatabase.getInstance().getReference("proiect");
        nume=findViewById(R.id.editTextTextNumeProdus);
        descriere=findViewById(R.id.editTextDescriereProdus);
        pret=findViewById(R.id.editTextPret);
        adaugaPoza=findViewById(R.id.buttonAdaugaPoza);
        adaugaProdus=findViewById(R.id.buttonAdaugaProdus);
        checkBox=findViewById(R.id.checkBoxDisponibilitate);
        alegePoza=findViewById(R.id.buttonAlegePoza);
        storage = FirebaseStorage.getInstance();
        imageView=findViewById(R.id.imageView);
        storageReference = storage.getReference();
        alegePoza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        adaugaProdus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeP=nume.getText().toString();
                String descriereP=descriere.getText().toString();
                Float pretP=Float.valueOf(pret.getText().toString());
                Boolean disponibilitate;
                if(checkBox.isChecked()==true){
                    disponibilitate=true;
                }
                else{
                    disponibilitate=false;
                }
                String id=databaseReference.push().getKey();
                Produs produs=new Produs(numeP,descriereP,pretP,disponibilitate,id);
                databaseReference.child(id).setValue(produs);
                golireDate();


            }
        });
        adaugaPoza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
alegePoza.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        chooseImage();
    }
});





    }
    void golireDate(){
        nume.getText().clear();
        descriere.getText().clear();
        pret.getText().clear();
        checkBox.setChecked(false);

    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child( nume.getText().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ActivityAddProduse.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ActivityAddProduse.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}