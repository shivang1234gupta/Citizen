package com.myapp.android.citizen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CompRegister extends AppCompatActivity implements SelectPoliceStation.Station_Getter {
    TextInputEditText applicantName;
    TextInputEditText applicantDob;
    TextInputEditText applicantAdress;
    TextInputEditText applicantMobile;
    Spinner complaintType;
    Spinner applicantGender;
    ScrollView scrollView;
    TextInputEditText complaintDet;
    Button submitButton;


    String pid;
    DatabaseReference databaseReference;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_register);
        SelectPoliceStation station=new SelectPoliceStation();
        station.show(getSupportFragmentManager(),"Tag");

        initialize();
        applicantDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(applicantDob);
            }
        });
    }

    private void initialize() {
        builder = new AlertDialog.Builder(this);
        complaintDet=findViewById(R.id.complaintDetailEditText);
        submitButton=findViewById(R.id.submitButton);
        scrollView=findViewById(R.id.permissionScrollView);
        applicantName=findViewById(R.id.applicantNameEditText);
        applicantAdress=findViewById(R.id.applicantAddressEditText);
        applicantDob=findViewById(R.id.applicantDobEditText);
        applicantMobile=findViewById(R.id.applicantmoblieEditText);
        applicantGender=findViewById(R.id.genderSpinner);
        complaintType=findViewById(R.id.complaintTypeSpinner);
    }
    private void DatePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(CompRegister.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
        dialog.show();
    }

    public void RegComp(View view) {
        if(applicantName.getText()==null || applicantName.getText().toString().trim().length()==0){
            Toast.makeText(this, "Enter Applicant's Name", Toast.LENGTH_SHORT).show();
            applicantName.requestFocus();
            scrollView.smoothScrollTo(0,applicantName.getTop());
            return;
        }else if(applicantDob.getText()==null || applicantDob.getText().toString().trim().length()==0){
            Toast.makeText(this, "Enter Applicant's DOB", Toast.LENGTH_SHORT).show();
            applicantDob.requestFocus();
            scrollView.smoothScrollTo(0,applicantDob.getTop());
            return;
        }else if(applicantGender.getSelectedItem().toString().contains("Select")){
            Toast.makeText(this, "Enter Applicant's Gender", Toast.LENGTH_SHORT).show();
            scrollView.smoothScrollTo(0,applicantGender.getTop());
            return;
        }else if(applicantMobile.getText()==null||applicantMobile.getText().length()!=10){
            Toast.makeText(this, "Enter Applicant's Mobile", Toast.LENGTH_SHORT).show();
            applicantMobile.requestFocus();
            scrollView.smoothScrollTo(0,applicantMobile.getTop());
            return;
        }else if(complaintType.getSelectedItem().toString().contains("Select")){
            Toast.makeText(this, "Enter Complaint's Type", Toast.LENGTH_SHORT).show();
            scrollView.smoothScrollTo(0,complaintType.getTop());
            return;
        } else if(applicantAdress.getText()==null || applicantAdress.getText().toString().length()==0){
            Toast.makeText(this, "Enter Applicant's Name", Toast.LENGTH_SHORT).show();
            applicantAdress.requestFocus();
            scrollView.smoothScrollTo(0,applicantAdress.getTop());
            return;
        }else if(complaintDet.getText()!=null&&complaintDet.getText().toString().trim().length()==0){
            Toast.makeText(this, "Enter Complaint's Detail", Toast.LENGTH_SHORT).show();
            complaintDet.requestFocus();
            scrollView.smoothScrollTo(0,complaintDet.getTop());
            return;
        }
        String applicant=applicantName.getText().toString().trim();
        String appDob=applicantDob.getText().toString().trim();
        String appAdress=applicantAdress.getText().toString().trim();
        String compType=complaintType.getSelectedItem().toString();
        String appGender=applicantGender.getSelectedItem().toString();
        String appMob=applicantMobile.getText().toString().trim();
        String comp=complaintDet.getText().toString().trim();
        Complaint currCopmlaint=new Complaint(applicant,pid,appMob,appAdress,appDob,appGender,compType,comp);
        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
        setDialogBox(currCopmlaint);

    }
    public void setDialogBox(final Complaint complaint){
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String compId=databaseReference.push().getKey();
                databaseReference=databaseReference.child(compId);
                databaseReference.setValue(complaint);
                setDialogBoxToShoComplaintId(compId);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.setTitle("Register  Complaint  !!!");
        builder.setMessage("Are you sure?");
        builder.show();
    }
    public void setDialogBoxToShoComplaintId(final String CompId){
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setTitle("Complaint Registered  !!!");
        builder.setMessage("Save Complaint Id For Future Reference \n\n\nFir Id :   "+CompId);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void offFocus() {
        applicantName.setFocusable(false);
        applicantDob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        applicantAdress.setFocusable(false);
        //applicantPermanentAddEditText.setFocusable(false);
        applicantMobile.setFocusable(false);

        complaintDet.setFocusable(false);
        applicantGender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void getStation(String policeStationId) {

        pid=policeStationId;
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Registered Complaint").child(pid);

    }

    @Override
    public void OnCanceled() {
        onBackPressed();
    }
}
