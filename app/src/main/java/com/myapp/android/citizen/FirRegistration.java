package com.myapp.android.citizen;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class FirRegistration extends AppCompatActivity implements SelectPoliceStation.Station_Getter {
    Toolbar toolbar;
    Button submitButton;
    EditText applicantNameEditText;
    EditText applicantDobEditText;
    EditText applicantAddressEditText;
    EditText applicantPermanentAddEditText;
    EditText applicantMobileNoEditText;
    EditText incidentDateEditText;
    EditText fireDetailEditText;
    Spinner genderSpinner;
    //Spinner statusSpinner;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    String username;
    LinearLayout linearLayout;
    AlertDialog.Builder builder;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_registration);
        SelectPoliceStation station=new SelectPoliceStation();
        station.show(getSupportFragmentManager(),"Tag");
        initialize();
        applicantDobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(applicantDobEditText);
            }
        });
        incidentDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(incidentDateEditText);
            }
        });
    }

    private void initialize() {
        submitButton = findViewById(R.id.submitButton);
        applicantNameEditText = findViewById(R.id.applicantNameEditText);
        applicantDobEditText = findViewById(R.id.DobEditText);
        applicantAddressEditText = findViewById(R.id.applicantAddressEditText);
        applicantPermanentAddEditText = findViewById(R.id.permanentAddEditText);
        applicantMobileNoEditText = findViewById(R.id.moblieEditText);
        incidentDateEditText = findViewById(R.id.incidentDateEditText);
        fireDetailEditText = findViewById(R.id.firDetailEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        //statusSpinner=findViewById(R.id.statusSpinner);
        sharedPreferences = getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
        //username = sharedPreferences.getString("username", "");
        //databaseReference = FirebaseDatabase.getInstance().getReference("Registered Fir").child(username);
        builder = new AlertDialog.Builder(this);
        scrollView=(ScrollView) findViewById(R.id.firScrollView);
    }

    private void DatePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(FirRegistration.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
        dialog.show();
    }


    public void onSubmit(View view) {
        if (applicantNameEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's Name", Toast.LENGTH_SHORT).show();
            applicantNameEditText.requestFocus();
            scrollView.smoothScrollTo(0,applicantNameEditText.getTop());

        } else if (applicantDobEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's DOB", Toast.LENGTH_SHORT).show();
            DatePicker(applicantDobEditText);
            scrollView.smoothScrollTo(0,applicantDobEditText.getTop());
        } else if (applicantAddressEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's Address", Toast.LENGTH_SHORT).show();
            applicantAddressEditText.requestFocus();
            scrollView.smoothScrollTo(0,applicantAddressEditText.getTop());
        } else if (applicantPermanentAddEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's Permanent Address", Toast.LENGTH_SHORT).show();
            applicantPermanentAddEditText.requestFocus();
            scrollView.smoothScrollTo(0,applicantPermanentAddEditText.getTop());
        } else if (applicantMobileNoEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's Mobile Number", Toast.LENGTH_SHORT).show();
            applicantMobileNoEditText.requestFocus();
            scrollView.smoothScrollTo(0,applicantMobileNoEditText.getTop());
        } else if (applicantMobileNoEditText.getText().toString().trim().length() != 10) {
            Toast.makeText(this, "Mobile Number should be of 10 digits", Toast.LENGTH_SHORT).show();
            applicantMobileNoEditText.requestFocus();
        } else if (genderSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            genderSpinner.performClick();
        }
        else if (incidentDateEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Incident Date", Toast.LENGTH_SHORT).show();
            DatePicker(incidentDateEditText);
        } else if (fireDetailEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Fir Detail", Toast.LENGTH_SHORT).show();
            fireDetailEditText.requestFocus();
        } else if (username.matches("")) {
            Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
        } else {
            String name = applicantNameEditText.getText().toString().trim();
            String dob = applicantDobEditText.getText().toString().trim();
            String add = applicantAddressEditText.getText().toString().trim();
            String perAdd = applicantPermanentAddEditText.getText().toString().trim();
            String mobileNo = applicantMobileNoEditText.getText().toString().trim();
            String date = incidentDateEditText.getText().toString().trim();
            String detail = fireDetailEditText.getText().toString().trim();
            String gender = (String) genderSpinner.getSelectedItem();
            String status = "Unsolved";


            View view1 = this.getCurrentFocus();
            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
            if (submitButton.getText().toString().matches("Submit")) {
                offFocus();
                submitButton.setText("Final Submit");
                setTitle("Check Fir Details");
                Toast.makeText(this, "Check FIR details", Toast.LENGTH_SHORT).show();
            } else {
                FIR currentFir = new FIR(name, dob, add, perAdd, gender,status, mobileNo, date, detail);
                String FirId = databaseReference.push().getKey();
                setDialogBox(FirId,currentFir);
            }
        }
    }

    public void offFocus() {
        applicantNameEditText.setFocusable(false);
        applicantDobEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        applicantAddressEditText.setFocusable(false);
        applicantPermanentAddEditText.setFocusable(false);
        applicantMobileNoEditText.setFocusable(false);
        incidentDateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        fireDetailEditText.setFocusable(false);
        genderSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
//        statusSpinner.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
    }

    public void setDialogBox(final String FirId, final FIR currFir){
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference.child(FirId).setValue(currFir);
                setDialogBoxToShowFirId(FirId);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.setTitle("Register Fir  !!!");
        builder.setMessage("Are you sure?");
        builder.show();
    }

    public void setDialogBoxToShowFirId(final String FirId){
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setTitle("Fir Registered  !!!");
        builder.setMessage("Save Fir Id For Future Reference \n\n\nFir Id :   "+FirId);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void getStation(String policeStationId) {
        username=policeStationId;
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Registered Fir").child(username);
    }

    @Override
    public void OnCanceled() {
        onBackPressed();
    }
}

