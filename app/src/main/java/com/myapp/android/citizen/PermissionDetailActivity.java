package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PermissionDetailActivity extends AppCompatActivity {

    TextView permissionIdTextView;
    TextView applicantNameTextView;
    TextView applicantMobileTextView;
    TextView permissionTypeTextView;
    TextView startDate, EndDate, permissionDetails;
    String status;
    Button permissionDetailButton;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    String specialPassword;
    String username;
    String permissionId;
    PermissionClass permissionClass;
    Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_detail);
        toolbar=(Toolbar) findViewById(R.id.permissionDetailToolbar);
        setSupportActionBar(toolbar);
        setTitle("Permission Detail");

        permissionIdTextView = (TextView) findViewById(R.id.permissionDetailIdTextView);
        applicantNameTextView = (TextView) findViewById(R.id.permissionDetailApplicantNameTextView);
        applicantMobileTextView = (TextView) findViewById(R.id.permissionDetailApplicantMobileTextView);
        permissionTypeTextView = (TextView) findViewById(R.id.permissionDetailPermissionTypeTextView);
        startDate = (TextView) findViewById(R.id.permissionDetailStartDateTextView);
        EndDate = (TextView) findViewById(R.id.permissionDetailEndDateTextView);
        permissionDetails = (TextView) findViewById(R.id.permissionDetailTextView);
        permissionDetailButton = (Button) findViewById(R.id.permissionDetailButton);
        sharedPreferences=getSharedPreferences("com.example.policebureauapp",MODE_PRIVATE);
        username=sharedPreferences.getString("username","");
        databaseReference= FirebaseDatabase.getInstance().getReference("RegisteredStations").child(username);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<Object,Object>hp= (HashMap<Object, Object>) dataSnapshot.getValue();
                Log.i("hello",hp.get("specialPassword").toString());
                specialPassword= (String) hp.get("specialPassword");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent = getIntent();
        permissionClass = (PermissionClass) intent.getSerializableExtra("permission");
        permissionId = intent.getStringExtra("permissionId");

        permissionIdTextView.setText(permissionId);
        applicantNameTextView.setText(permissionClass.getApplicantName());
        applicantMobileTextView.setText(permissionClass.getApplicantMobileNo());
        permissionTypeTextView.setText(permissionClass.getType());
        startDate.setText("Starting Date : " + permissionClass.getStartDate());
        if (permissionClass.getEndDate().matches("")) {
            EndDate.setText("Ending Date : " + permissionClass.getStartDate());
        } else {
            EndDate.setText("Ending Date : " + permissionClass.getEndDate());
        }
        permissionDetails.setText(permissionClass.getDetail());

        status = permissionClass.getStatus1();
        permissionDetailButton.setText(status);

        if (status.matches("Granted")) {
            permissionDetailButton.setBackgroundColor(Color.GREEN);
        }

        permissionDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.matches("Pending")) {
                    showDialogBox();
                } else {
                    Toast.makeText(PermissionDetailActivity.this,
                            "You can't change Granted Permission to Pending", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialogBox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialogBoxForConfirmation();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Change Status to Granted!!!");
        builder.setMessage("Are you sure?");
        builder.show();
    }

    private void showDialogBoxForConfirmation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.login_alert_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.passwordTextView);
        textView.setText("Special Password");
        final EditText editText=(EditText) view.findViewById(R.id.passwordEditText);
        editText.setHint("Special Password");


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText().toString().matches(specialPassword)){
                    databaseReference = FirebaseDatabase.getInstance().getReference("Registered Permissions").child(username);
                    databaseReference.child(permissionId).removeValue();
                    permissionClass.setStatus1("Granted");
                    databaseReference.child(permissionId).setValue(permissionClass);
                    Toast.makeText(PermissionDetailActivity.this, "Status Changed", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(PermissionDetailActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Change Status to Granted");
        builder.setView(view);
        builder.show();
    }
}
