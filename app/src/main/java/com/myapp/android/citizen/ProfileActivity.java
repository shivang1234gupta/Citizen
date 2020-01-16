package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
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
        phonenumber=findViewById(R.id.phone);
        email_verify=findViewById(R.id.verifyemail);
        otp=findViewById(R.id.otp);
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
        verifymail();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChoser();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*boolean x=verifycode();
                if (x)
                    Toast.makeText(ProfileActivity.this,"Mobile number verified",Toast.LENGTH_SHORT);
                else
                    Toast.makeText(ProfileActivity.this,"Mobile number not verified",Toast.LENGTH_SHORT);*/
                saveUserInfo();
            }
        });


    }
    private void verifymail(){
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(!user.isEmailVerified()){
            email_verify.setVisibility(View.VISIBLE);
            email_verify.setText("Email not verified: Tap to verify");
            email_verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ProfileActivity.this,"Verification Id send",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

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
    public boolean verifycode(){
        if(phonenumber.getText()!=null) {
            String mobile = phonenumber.getText().toString();
            if (mobile.length() < 10) {
                phonenumber.setError("10 digit mobile number is required");
                phonenumber.requestFocus();
            } else {
                otp.setVisibility(View.VISIBLE);
                int otp1 = (int) (Math.random() * 1000000);
                new QueryUtils().sendverificationcode(mobile, String.valueOf(otp1));
                  //  verifycode();
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
                        Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
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
        final StorageReference storageReference = mstoragereference.child("profilepics" + System.currentTimeMillis() + ".jpg");
        if (uriImage != null) {
            UploadTask uploadTask = storageReference.putFile(uriImage);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                        throw task.getException();
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();

                    }
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
