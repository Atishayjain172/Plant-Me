package com.example.plantme;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Review extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Uri imageuri;
    Button button,submit;
    ImageView photo;
    EditText comment;
    RadioButton r1,r2;
    DatabaseReference reference2;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        button = (Button) findViewById(R.id.button);
        photo = (ImageView) findViewById(R.id.photo);
        comment=(EditText) findViewById(R.id.comment);
        submit=(Button) findViewById(R.id.submit);
        r1=(RadioButton) findViewById(R.id.yes);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        r2=(RadioButton) findViewById(R.id.no);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(Review.this,MainActivity.class);
              startActivity(intent);
            }
        });
    }

    public void getimage(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                opencamera();
            }
        } else {
            opencamera();
        }
    }

    public void opencamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Captured Picture");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "for currency");
        imageuri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(camera, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    opencamera();
                } else {
                    Toast toast = Toast.makeText(Review.this, "Request Denied", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (imageuri != null) {
            photo.setImageURI(imageuri);
            button.setVisibility(View.GONE);
            photo.setVisibility(View.VISIBLE);
        }

    }

    public void uploadimage() {
        submit.setText("Uploading Image please wait");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                submit.setText("Image Uploaded");
            }
        }, 3500);

        StorageReference riversRef = mStorageRef.child("images/image.jpg");
        riversRef.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        button.setText("Image did not Upload");
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });


    }
    public void submitform(View view)
    {
        submit.setText("Uploading Image please wait");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                submit.setText("Review Uploaded");
            }
        }, 3500);

        String text2="";
        String s2=comment.getText().toString().trim();
        if(r1.isChecked())
        {
            text2="yes";
        }
        else if(r2.isChecked())
        {
            text2="no";
        }
        reference2 = FirebaseDatabase.getInstance().getReference().child("Review").child(text2.toLowerCase());
        submitdata selection = new submitdata("", "", s2, text2);
        reference2.setValue(selection);
    }

}
