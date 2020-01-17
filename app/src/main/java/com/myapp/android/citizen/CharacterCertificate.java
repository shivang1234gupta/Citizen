package com.myapp.android.citizen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class CharacterCertificate extends AppCompatActivity {
    TextInputEditText applicantName;
    TextInputEditText applicantDOB;
    TextInputEditText applicantMobile;
    TextInputEditText applicantAdress;
    TextInputEditText enterDoc1;
    TextInputEditText enterDoc2;
    TextInputEditText enterDoc3;
    Button upload1;
    Button upload2;
    Button upload3;
    Button submit;


    void initialize(){
        applicantName=findViewById(R.id.verForm_et1);
        applicantDOB=findViewById(R.id.verForm_et2);
        applicantMobile=findViewById(R.id.verForm_et3);
        applicantAdress=findViewById(R.id.verForm_et4);
        enterDoc1=findViewById(R.id.verForm_enterDoc1);
        enterDoc2=findViewById(R.id.verForm_enterDoc2);
        enterDoc3=findViewById(R.id.verForm_enterDoc3);
        upload1=findViewById(R.id.verForm_Upload1);
        upload2=findViewById(R.id.verForm_Upload2);
        upload3=findViewById(R.id.verForm_Upload3);
        submit=findViewById(R.id.verForm_submit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_verification);
        initialize();

    }

    public void Submit(View view) {
        if(applicantName.getText().toString().trim().length()==0){
            applicantName.requestFocus();
            applicantName.setError("View Incomplete");
        }else if(applicantAdress.getText().toString().trim().length()==0){
            applicantAdress.setError("Data Incomplete");
            applicantAdress.requestFocus();
        }else  if(applicantDOB.getText().toString().trim().length()==0){
            applicantDOB.requestFocus();
            applicantDOB.setError("Data Not Complete");
        }else if(applicantMobile.getText().toString().trim().length()!=10){
            applicantMobile.requestFocus();
            applicantMobile.setError("Data Incopmplete");
        }


        else{

        }
    }
}
