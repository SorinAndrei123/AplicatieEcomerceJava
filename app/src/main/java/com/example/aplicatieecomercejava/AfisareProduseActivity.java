package com.example.aplicatieecomercejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AfisareProduseActivity extends AppCompatActivity {
   ListView listView;
    List<Produs> listaProduse=new ArrayList<>();
    DatabaseReference databaseReference;
    StorageReference storageReference;
    AdaptorListView adaptorListView;
    FloatingActionButton floatingActionButton;
    List<Produs>listaProduseShoppingCart=new ArrayList<>();
    int suma=0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afisare_produse);
        listView=findViewById(R.id.listViewProduse);
        databaseReference= FirebaseDatabase.getInstance().getReference("proiect");
        storageReference= FirebaseStorage.getInstance().getReference();
        floatingActionButton=findViewById(R.id.floatingActionButtonShoppingCart);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ShoppingCartActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("listaProduse", (Serializable) listaProduseShoppingCart);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProduse.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Produs produs=dataSnapshot.getValue(Produs.class);
                    listaProduse.add(produs);
                }
                adaptorListView=new AdaptorListView(getApplicationContext(),R.layout.layoutadaptorproduse,listaProduse,getLayoutInflater(),storageReference,databaseReference);
                listView.setAdapter(adaptorListView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ActivityAddProduse.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("produs",listaProduse.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               listaProduseShoppingCart.add(listaProduse.get(i));

               suma+=listaProduse.get(i).getPret();
            //   Toast.makeText(AfisareProduseActivity.this, "Nr produse in cos: "+String.valueOf(listaProduseShoppingCart.size()), Toast.LENGTH_SHORT).show();

           }
       });
    }
}