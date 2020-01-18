package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<List_item> list_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_items=new ArrayList<>();
        list_items.add(new List_item("FIR"));
        list_items.add(new List_item("Search Fir"));
        list_items.add(new List_item("Complains"));
        list_items.add(new List_item("Verification"));
        list_items.add(new List_item("Permission for protest"));
        list_items.add(new List_item("Permission For Procession"));
        list_items.add(new List_item("Permission For Event"));
        list_items.add(new List_item("Permissions For Film Shooting"));

        adapter=new mainAdapter();
        recyclerView.setAdapter(adapter);

//        textView=findViewById(R.id.username);
//        imageView=findViewById(R.id.photo);
//        recyclerView=findViewById(R.id.recycler);
//        //recyclerView.setHasFixedSize();
//        //recyclerView.
//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        Log.i("user:  ","onStart");
//        if (user.getPhotoUrl()==null||user.getDisplayName()==null){
//            Log.i("photo:  ","intent");
//            Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }
//        loadUserInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Log.i("user:  ","onStart");
        if (user.getPhotoUrl()==null||user.getDisplayName()==null){
            Log.i("photo:  ","intent");
            Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        }
    }

    private void loadUserInfo(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user.getDisplayName()==null||user.getPhotoUrl()==null){
            Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (user!=null){
            if (user.getPhotoUrl()!=null){
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }
            if (user.getDisplayName()!=null){
                textView.setText(user.getDisplayName());
            }
        }
        else {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menus,menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        if(item.getItemId()==R.id.logout)
        {
            logout();
            return true;
        }
        else if(item.getItemId()==R.id.nearpolice)
        {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=Nearby Police Station");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
            return true;
        }
        else if (item.getItemId()==R.id.verify)
        {
            Intent intent=new Intent(MainActivity.this, VerificationActivity.class);////errror
            startActivity(intent);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem item = menu.getItem(1);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Citizen")
                .child(FirebaseAuth.getInstance().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status="false";
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        Log.e("Snapshot",snapshot.toString());
                        if (snapshot.getKey().equals("mobilestatus"))
                            status=snapshot.getValue().toString();
                    }
                    if (status.equals("true")&& FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
                    {

                        item.setEnabled(false);
                    }
                    else{
                        Intent intent=new Intent(MainActivity.this,VerificationActivity.class);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;

    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent=new Intent(MainActivity.this,LogInActivity.class);
        startActivity(intent);
    }
}