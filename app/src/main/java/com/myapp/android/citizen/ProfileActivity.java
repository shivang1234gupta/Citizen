package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ImageView imageView;
    private EditText name,dob,phonenumber,otp;
    private Button submit,signin;
    private TextView email_verify;
    private static final int CHOSE_IMAGE = 101;
    private ProgressBar progressBar;
    private Uri uriImage;
    private StorageReference mstoragereference;
    private Uri downloadUri;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        imageView=findViewById(R.id.profilephoto);
        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        submit=findViewById(R.id.Continue);
        mauth=FirebaseAuth.getInstance();
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker=new DatePickFragment();
                datepicker.show(getSupportFragmentManager(),"datepicker");
            }
        });
        mstoragereference=FirebaseStorage.getInstance().getReference();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChoser();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CHOSE_IMAGE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
            uriImage= data.getData();
        Log.i("URI : ",uriImage.toString());

        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriImage);
            Log.i("Image ","Bitmap");
            imageView.setImageBitmap(bitmap);
            Log.i("Imgage set: ","set");
            uploadImagetoFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveUserInfo()
    {
        String citizen_name=name.getText().toString();
        if (citizen_name.isEmpty())
        {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        String citizen_dob=dob.getText().toString();
        if (citizen_dob.isEmpty())
        {
            dob.setError("Date of birth required");
            dob.requestFocus();
            return;
        }
        FirebaseUser mfirebaseUser=mauth.getCurrentUser();
        if (mfirebaseUser!=null&&downloadUri!=null){
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(citizen_name)
                    .setPhotoUri(downloadUri)
                    .build();
            mfirebaseUser.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ProfileActivity.this,VerificationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });
        }
    }
    public void showImageChoser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),CHOSE_IMAGE);
    }
    private void uploadImagetoFirebase() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        final StorageReference storageReference = mstoragereference.child("profilepics" + System.currentTimeMillis() + ".jpg");
        if (uriImage != null) {
            UploadTask uploadTask = storageReference.putFile(uriImage);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                    //Uri url=null;
                    while (!uri.isComplete());
                       Uri url=uri.getResult();

                    downloadUri=url;
                    Toast.makeText(getApplicationContext(),"File uploaded",Toast.LENGTH_SHORT);
                    progressDialog.dismiss();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String sowdate= sdf.format(calendar.getTime());
        dob.setText(sowdate);
    }
}
