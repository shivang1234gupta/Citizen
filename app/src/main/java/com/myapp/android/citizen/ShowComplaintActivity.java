package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowComplaintActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Complaint> list_items;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_complaint);
        recyclerView=findViewById(R.id.recyclerShowComplain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list_items=new ArrayList<>();
        adapter = new ComplaintRecyclerAdapter(list_items);
        recyclerView.setAdapter(adapter);
        getFIR();
    }
    private void getFIR()
    {
        DatabaseReference databaseReference2;
        databaseReference2= FirebaseDatabase.getInstance().getReference("Citizen").
                child(FirebaseAuth.getInstance().getUid()).child("COMPLAINTS");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<UserComplain> a=new ArrayList<>();
                Log.e("Datasnapshot: ",dataSnapshot.toString());
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Log.e("Snapshot: ",snapshot.toString());
                        UserComplain f=snapshot.getValue(UserComplain.class);
                        a.add(f);
                    }
                    for (int i=0;i<a.size();i++){
                        final String FirId=a.get(i).getCompid();
                        final String pid=a.get(i).getPid();
                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().
                                getReference("Registered Complaint").
                                child(pid);
                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Complaint fir=new Complaint();
                                if (dataSnapshot.exists())
                                {
                                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                                    {
                                        if (snapshot.getKey().equals(FirId))
                                        {
                                            fir=snapshot.getValue(Complaint.class);
                                            break;
                                        }
                                    }
                                    list_items.add(fir);
                                    //firArrayList.add(newFir);
                                    adapter.notifyDataSetChanged();
                                    //set the adapter
                                    Log.e("FIR: ", fir.getApplicant());
                                }

                                Log.e("datasnapshot " ,dataSnapshot.toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
