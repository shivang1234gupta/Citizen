package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignInActivity extends AppCompatActivity {
    private EditText Email,password;
    private Button signin,signinlog;
    private ProgressBar progressBar;
    private FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Email=findViewById(R.id.EmailIdSignin);
        password=findViewById(R.id.PasswordSignin);
        signin=findViewById(R.id.RegisterSignin);
        progressBar=findViewById(R.id.progressbarSignin);
        signinlog=findViewById(R.id.signinlog);
        mfirebaseAuth=FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        signinlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignInActivity.this,LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void registerUser() {
        String email=Email.getText().toString();
        String pass=password.getText().toString();
        email=email.trim();
        pass=pass.trim();
        if (email.isEmpty())
        {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Valid email is required");
            Email.requestFocus();
            return;
        }
        if (pass.isEmpty())
        {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (pass.length()<6)
        {
            password.setError("Minimum 6 digit Password is required");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mfirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    finish();
                    Toast.makeText(getApplicationContext(),"You are registered",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignInActivity.this,ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(getApplicationContext(),"You are already registered",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Some error occured ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
