package com.myapp.android.citizen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class TenantVerification extends AppCompatActivity {
    TextInputEditText applicantName;
    TextInputEditText tenantName;
    TextInputEditText tenantDOB;
    TextInputEditText tenantMobile;
    TextInputEditText applicantmobile;
    TextInputEditText applicantPerAdd;
    TextView Upload1;
    TextView Upload2;
    TextView Upload3;
    Button upButton1;
    Button upButton2;
    Button upButton3;
    Button submit;


    private void initialize(){
        applicantName=findViewById(R.id.verForm_et1);
        tenantName=findViewById(R.id.verForm_et2);
        tenantDOB=findViewById(R.id.verForm_et3);
        tenantMobile=findViewById(R.id.verForm_et4);
        applicantmobile =findViewById(R.id.verForm_et5);
        applicantPerAdd=findViewById(R.id.verForm_et6);
        Upload1=findViewById(R.id.verForm_Upload1);
        Upload2=findViewById(R.id.verForm_Upload2);
        Upload3=findViewById(R.id.verForm_Upload3);
        upButton1=findViewById(R.id.verForm_enterDoc1);
        upButton2=findViewById(R.id.verForm_enterDoc2);
        upButton3=findViewById(R.id.verForm_enterDoc3);
        submit=findViewById(R.id.verForm_submit);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_verification);
        initialize();

    }

    public void Submit(View view) {
        if(applicantName.getText().toString().trim().length()==0){
            applicantName.setError("Incomplete field");
            applicantName.requestFocus();
        }else if(tenantName.getText().toString().trim().length()==0){
            tenantName.setError("Incomplete field");
            tenantName.requestFocus();
        }else if(tenantDOB.getText().toString().trim().length()==0){
            tenantDOB.setError("Incomplete field");
            tenantDOB.requestFocus();
        }else if(tenantMobile.getText().toString().trim().length()==0){
            tenantMobile.setError("Incomplete field");
            tenantMobile.requestFocus();
        }else if(applicantmobile.getText().toString().trim().length()==0){
            applicantmobile.setError("Incomplete field");
            applicantmobile.requestFocus();
        }else if (applicantPerAdd.getText().toString().trim().length()==0){
            applicantPerAdd.setError("Incomplete field");
            applicantPerAdd.requestFocus();
        }



        else{

        }
    }
}
