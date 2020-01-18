package com.myapp.android.citizen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowFullFIRActivity extends AppCompatActivity {

    private TextView name,status,eventdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_fir);
        Intent intent=getIntent();
        FIR f= (FIR) intent.getSerializableExtra("FIR");
        name=findViewById(R.id.nameshow);
        status=findViewById(R.id.statusshow);
        eventdate=findViewById(R.id.dateshow);
        name.setText(f.getApplicantName());
        status.setText(f.getStatus());
        eventdate.setText(f.getIncindentDate());

    }
}
