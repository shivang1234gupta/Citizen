package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LogInActivity extends AppCompatActivity {

    private FirebaseDatabase fd;
    private DatabaseReference dr;
    private ChildEventListener mchildEventListener;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    private EditText Email,password;
    private TextView Signin,forgot;
    private Button register;
    private ProgressBar progressBar;
    private Uri uriImage;
    private StorageReference mstoragereference;
    private String DownloadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Email=findViewById(R.id.EmailId);
        password=findViewById(R.id.Password);
        Signin=findViewById(R.id.SignIn);
        register=findViewById(R.id.Register);
        forgot=findViewById(R.id.forgot);
        progressBar=findViewById(R.id.progressbar);
        mfirebaseAuth=FirebaseAuth.getInstance();
        mstoragereference= FirebaseStorage.getInstance().getReference();
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(LogInActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Email.getText().toString().trim();
                if (email.isEmpty())
                {
                    Email.setError("Email is required");
                    Email.requestFocus();
                    return;
                }
                changepassword(email);
            }
        });
    }
    private  void changepassword(String mail){
        FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this,"mail sent for password changing",
                                    Toast.LENGTH_SHORT).show();
                        }
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
        mfirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    finish();
                    Toast.makeText(getApplicationContext(),"You are registered",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LogInActivity.this,MainActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            finish();
            Intent intent=new Intent(LogInActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}