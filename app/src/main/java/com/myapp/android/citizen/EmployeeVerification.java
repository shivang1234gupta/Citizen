
package com.myapp.android.citizen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class EmployeeVerification extends AppCompatActivity implements SelectPoliceStation.Station_Getter{
    TextInputEditText employeeName;
    TextInputEditText employerName;
    TextInputEditText employerMobile;
    TextInputEditText employeeMobile;
    TextInputEditText employerAdress;
    TextInputEditText employeePosition;
    TextInputEditText employeeDob;
    TextInputEditText employeePermanentAdress;
    TextView verDoc1;
    TextView verDoc2;
    TextView verDoc3;
    Button Upload1;
    Button Upload2;
    Button Upload3;
    Button Submit;
    String pid;



    void initialize(){
        employeeName=findViewById(R.id.verForm_et2);
        employerName=findViewById(R.id.verForm_et1);
        employeePosition=findViewById(R.id.verFormEmployeePosition);
        employerMobile=findViewById(R.id.verForm_et5);
        employeeMobile=findViewById(R.id.verForm_et4);
        employeeDob=findViewById(R.id.verForm_et3);
        employeePermanentAdress=findViewById(R.id.verForm_et6);
        employerAdress=findViewById(R.id.verForm_et7);
        verDoc1=findViewById(R.id.verForm_enterDoc1);
        verDoc2=findViewById(R.id.verForm_enterDoc2);
        verDoc3=findViewById(R.id.verForm_enterDoc3);
        Upload1=findViewById(R.id.verForm_Upload1);
        Upload2=findViewById(R.id.verForm_Upload2);
        Upload3=findViewById(R.id.verForm_Upload3);
        Submit=findViewById(R.id.verForm_submit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_verification);
        SelectPoliceStation station=new SelectPoliceStation();
        station.show(getSupportFragmentManager(),"tag");
        initialize();

    }

    public void SubmitClicked(View view) {
        if(employerAdress.getText().toString().trim().length()==0){
            employerAdress.requestFocus();
            employerAdress.setError("Field Incomplete");
        }else if(employeePermanentAdress.getText().toString().trim().length()==0){
            employeePermanentAdress.setError("Field Incomplete");
            employeePermanentAdress.requestFocus();
        }else if(employeeDob.getText().toString().trim().length()==0){
            employeeDob.setError("Field no complete");
            employeeDob.requestFocus();
        }else if(employeeMobile.getText().toString().trim().length()==0){
            employeeMobile.requestFocus();
            employeeMobile.setError("Field Not Complete");
        }else if(employeeName.getText().toString().trim().length()==0){
            employeeName.requestFocus();
            employeeName.setError("Field Not Complete");
        }else if(employerName.getText().toString().trim().length()==0){
            employerName.requestFocus();
            employerName.setError("Field Not complete");
        }else if(employerMobile.getText().toString().trim().length()==0){
            employerMobile.setError("View Not Complete");
            employerMobile.requestFocus();
        }else if(employeePosition.getText().toString().trim().length()==0){
            employeePosition.setError("View Not Complete");
            employeePosition.requestFocus();
        }


        else {

        }
    }

    @Override
    public void getStation(String policeStationId) {
        pid=policeStationId;
    }

    @Override
    public void OnCanceled() {
        onBackPressed();
    }
}
