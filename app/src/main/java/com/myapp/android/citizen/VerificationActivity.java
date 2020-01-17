package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerificationActivity extends AppCompatActivity {
    private EditText mobile,otp;
    private Button submit;
    private TextView email,message,later;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mobile=findViewById(R.id.mobileVerify);
        otp=findViewById(R.id.otpVerify);
        submit=findViewById(R.id.submitVerify);
        email=findViewById(R.id.EmailVerify);
        message=findViewById(R.id.setVerify);
        later=findViewById(R.id.verifylater);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp.setVisibility(View.VISIBLE);
                if(verifycode())
                {
                    message.setVisibility(View.VISIBLE);
                    message.setText("Mobile number verified");
                }
                else
                {
                    message.setVisibility(View.VISIBLE);
                    message.setText("Wrong otp");
                }
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifymail();
                Toast.makeText(VerificationActivity.this,"Please Login again",Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(VerificationActivity.this,LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerificationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void verifymail(){
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(!user.isEmailVerified()){
            email.setVisibility(View.VISIBLE);
            email.setText("Email not verified: Tap to verify");
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(VerificationActivity.this,"Verification Id send",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

    }
    public boolean verifycode(){
        if(mobile.getText()!=null) {
            String mobile1 = mobile.getText().toString();
            if (mobile.length() < 10) {
                mobile.setError("10 digit mobile number is required");
                mobile.requestFocus();
            } else {
                otp.setVisibility(View.VISIBLE);
                int otp1 = (int) (Math.random() * 1000000);
                new QueryUtils().sendverificationcode(mobile1, String.valueOf(otp1));
                if (otp.getText() == null) {
                    otp.setError("otp is necessary");
                    otp.requestFocus();
                } else {
                    String code = otp.getText().toString();
                    if (code.equals(otp1))
                        return true;

                }
            }
        }
        return false;
    }
    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                otp.setText(message);
                //Do whatever you want with the code here
            }
        }
    };
}
