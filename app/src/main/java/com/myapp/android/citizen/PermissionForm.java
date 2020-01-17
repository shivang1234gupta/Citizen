package com.myapp.android.citizen;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
public class PermissionForm extends AppCompatActivity implements SelectPoliceStation.Station_Getter {
    String perType;
    String pid;
    boolean hasOrganization;

    TextInputEditText applicantName;
    //TextInputEditText applicantDob;
    TextInputEditText organization;
    TextInputEditText applicantMobile;
    TextInputEditText organizationMobile;
    TextInputEditText applicantAdress;
    TextInputEditText organizationAdress;
    TextInputEditText startDate;
    TextInputEditText endDate;
    TextInputEditText Detail;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_form);
        Intent intent=getIntent();
        perType=intent.getStringExtra(PermissionClass.permissionType);
        SelectPoliceStation station=new SelectPoliceStation();
        station.show(getSupportFragmentManager(),"Tag");
        initialize();
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker((EditText)v);
            }
        };
        startDate.setOnClickListener(listener);
        endDate.setOnClickListener(listener);
    }

    private void initialize() {
        applicantMobile=findViewById(R.id.permissionMobileEditText);
        applicantName=findViewById(R.id.permissionApplicantNameEditText);
        organization=findViewById(R.id.affiliatedOrganizationEditText);
        organizationMobile=findViewById(R.id.permissionOrganizationNoEditText);
        applicantAdress=findViewById(R.id.permissionApplicantAddEditText);
        organizationAdress=findViewById(R.id.permissionOrganizationAddEditText);
        startDate=findViewById(R.id.permissionStartDateEditText);
        endDate=findViewById(R.id.permissionEndDateEditText);
        Detail = findViewById(R.id.permissionDetailEditText);
    }

    private void DatePicker(final EditText editText){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(PermissionForm.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
        dialog.show();
    }

    public void onSubmit(View view) {
        if(applicantName.getText()==null||applicantName.getText().toString().trim().length()==0){
            //Toast.makeText(this,"Enter Applicant Name",Toast.LENGTH_SHORT);
            applicantName.requestFocus();
            applicantName.setError("Enter Applicant Name");
        }else if(applicantAdress.getText()==null||applicantAdress.getText().toString().trim().length()==0){
           // Toast.makeText(this,"Enter Applicant adress",Toast.LENGTH_SHORT);
            applicantAdress.requestFocus();
            applicantAdress.setError("Enter Applicant NAme");
        }else if(startDate.getText()==null || startDate.getText().toString().trim().length()==0){
            startDate.requestFocus();
            startDate.setError("This Field Cannot  be Empty");
        }else if(Detail.getText()==null||Detail.getText().toString().trim().length()==0){
            Detail.requestFocus();
            Detail.setError("This Field Cannot be empty");
        }else if((organization.getText().toString().trim().length()!=0 || organizationAdress.getText().toString().trim().length()!=0
        ||organizationMobile.getText().toString().trim().length()!=0)&&!(organization.getText().toString().trim().length()==0 &&
                organizationAdress.getText().toString().trim().length()==0)&&organizationMobile.getText().toString().trim().length()==0){
            if(organizationMobile.getText().toString().trim().length()!=10){
                organizationMobile.setError("Incorrect information");
                organizationMobile.requestFocus();
            }else if(organization.getText().toString().trim().length()==0){
                organization.setError("Incorrect Information");
                organization.requestFocus();
            }
            else if(organizationAdress.getText().toString().trim().length()==0){
                organizationAdress.requestFocus();
                organizationAdress.setError("Incorrect Information");
            }else{
                hasOrganization=true;
                startLoad();
            }
        }else
            startLoad();
    }

    private  void startLoad(){
        PermissionClass permission=new PermissionClass();
        permission.ApplicantName=applicantName.getText().toString().trim();
        permission.applicantAddress=applicantAdress.getText().toString().trim();
        permission.applicantMobileNo=applicantMobile.getText().toString().trim();;
        permission.containsOrg=hasOrganization;
        if(hasOrganization){
            permission.organisation=organization.getText().toString().trim();
            permission.organizationAddress=organizationAdress.getText().toString().trim();
            permission.organizationMobileNo=organizationMobile.getText().toString().trim();
        }
        permission.startDate=startDate.getText().toString().trim();
        permission.endDate=endDate.getText().toString().trim();
        permission.detail=Detail.getText().toString().trim();
        permission.status="Pending";
        permission.type=perType;
        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
        setDialogBox(permission);
    }

    public void setDialogBox(final PermissionClass currentPermission) {
        AlertDialog.Builder  builder=new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String permissionId=reference.push().getKey();
                reference.child(permissionId).setValue(currentPermission);
                ShowId(permissionId);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.setTitle("Register Permission  !!!");
        builder.setMessage("Are you sure?");
        builder.show();
    }
    public void ShowId(String permisssionId) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setTitle("Permission Registered  !!!");
        builder.setMessage("Save Permission Id For Future Reference \n\n\nFir Id :   "+permisssionId);
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
        pid=policeStationId;
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        reference=database.getReference("Registered Permissions").child(pid);
    }
    @Override
    public void OnCanceled() {
        onBackPressed();
    }
}
