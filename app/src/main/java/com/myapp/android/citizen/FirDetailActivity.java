package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class FirDetailActivity extends AppCompatActivity {


    TextView firId, name, mobile, date, firDetail;
    androidx.appcompat.widget.AppCompatImageButton callButton;
    Button status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_detail);

        firId = (TextView) findViewById(R.id.firDetailIdTextView);
        name = (TextView) findViewById(R.id.firDetailNameTextView);
        mobile = (TextView) findViewById(R.id.firDetailMobileTextView);
        date = (TextView) findViewById(R.id.firDetailDateTextView);
        firDetail = (TextView) findViewById(R.id.firDetailDTextView);
        callButton = (androidx.appcompat.widget.AppCompatImageButton) findViewById(R.id.firDetailCallButton);
        status = (Button) findViewById(R.id.firDetailStatusButton);

//        permissionDetailButton = (Button) findViewById(R.id.permissionDetailButton);
//        sharedPreferences=getSharedPreferences("com.example.policebureauapp",MODE_PRIVATE);
//        username=sharedPreferences.getString("username","");
//        databaseReference= FirebaseDatabase.getInstance().getReference("RegisteredStations").child(username);
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                HashMap<Object,Object> hp= (HashMap<Object, Object>) dataSnapshot.getValue();
//                Log.i("hello",hp.get("specialPassword").toString());
//                specialPassword= (String) hp.get("specialPassword");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        Intent intent = getIntent();
        FIR curFir = (FIR) intent.getSerializableExtra("FIR");
        //String curFirId = intent.getStringExtra("firId");

        //firId.setText(curFirId);
        name.setText(curFir.getApplicantName());
        mobile.setText(curFir.getApplicantMobileNo());
        date.setText(curFir.getIncindentDate());
        firDetail.setText(curFir.getFirDetails());

//        permissionIdTextView.setText(permissionId);
//        applicantNameTextView.setText(permissionClass.getApplicantName());
//        applicantMobileTextView.setText(permissionClass.getApplicantMobileNo());
//        permissionTypeTextView.setText(permissionClass.getType());
//        startDate.setText("Starting Date : " + permissionClass.getStartDate());
//        if (permissionClass.getEndDate().matches("")) {
//            EndDate.setText("Ending Date : " + permissionClass.getStartDate());
//        } else {
//            EndDate.setText("Ending Date : " + permissionClass.getEndDate());
//        }
//        permissionDetails.setText(permissionClass.getDetail());
//
//        status = permissionClass.getStatus1();
//        permissionDetailButton.setText(status);
//
//        if (status.matches("Granted")) {
//            permissionDetailButton.setBackgroundColor(Color.GREEN);
//        }
//
//        permissionDetailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (status.matches("Pending")) {
//                    showDialogBox();
//                } else {
//                    Toast.makeText(PermissionDetailActivity.this,
//                            "You can't change Granted Permission to Pending", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
