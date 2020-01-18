package com.myapp.android.citizen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CharacterCertificate extends AppCompatActivity {
    private static final int CHOSE_DOCUMENT =101 ;
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
    private Uri uriImage;
    private Uri downloadUri;
    private StorageReference mstorageReference;


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
        mstorageReference= FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_verification);
        initialize();
        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpdfchoser();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CHOSE_DOCUMENT&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
            uriImage= data.getData();
        Log.i("URI : ",uriImage.toString());

        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriImage);
            Log.i("Image ","Bitmap");
            enterDoc1.setText(uriImage.toString());
            //mageView.setImageBitmap(bitmap);
            Log.i("Imgage set: ","set");
            uploadImagetoFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showpdfchoser() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Document"),CHOSE_DOCUMENT);
    }
    private void uploadImagetoFirebase() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        final StorageReference storageReference = mstorageReference.child("Verifydocs" + System.currentTimeMillis() + ".pdf");
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
