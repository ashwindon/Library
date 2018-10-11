package com.example.ashwin.library;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;



public class Menu3 extends Fragment {

    View v;

    Button uploadFile, chooseFile;
    TextView notification;

    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    Uri pdfUri;
    //Uri downloadUrl = pdfUri;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.menu3, container, false);
        //v = inflater.inflate(R.layout.menu3,container,false);
        //v = inflater.inflate(R.layout.menu3, container, false);
        Log.d("herE", "hahahha");
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        Log.d("herE", "hahahha2");

        chooseFile = v.findViewById(R.id.chooseFile);
        uploadFile = v.findViewById(R.id.uploadFile);

        notification = v.findViewById(R.id.notification);
        context = container.getContext();
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                    Log.d("herE", "hahahha3");


                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 69);
                    Toast.makeText(v.getContext(), "Yo whats up???", Toast.LENGTH_SHORT).show();

                }
            }


        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pdfUri != null)
                    uploadFile(pdfUri);
                else
                    Toast.makeText(view.getContext(), "Select a file", Toast.LENGTH_SHORT).show();

            }
        });


        //return inflater.inflate(R.layout.menu3, container, false);
        return v;


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 69 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else {
            Toast.makeText(getActivity(), "Please Provide the permission first", Toast.LENGTH_SHORT).show();
        }


    }


    private void uploadFile(Uri pdfUri) {

        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File....");
        progressDialog.setProgress(0);
        progressDialog.show();


        final String filename = pdfUri.getLastPathSegment().toString();

        StorageReference storageReference = storage.getReference();   //returns a root path

        storageReference.child("Uploads").child(filename).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                String url = taskSnapshot.getStorage().getDownloadUrl().toString();

                DatabaseReference reference = database.getReference();

                reference.child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(v.getContext(), "File is successfully uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "File is not successfully uploaded", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(v.getContext(), "File is not successfully uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);


            }
        });

    }


    private void selectPdf() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
//        intent.setType("application/docx");
//        intent.setType("application/images");
//        intent.setType("application/audio");
//        intent.setType("application/video");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 99);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 99 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            Log.d("Filename", String.valueOf(pdfUri));
            notification.setText("File Selected. Now Upload it");
        } else {
            Toast.makeText(getView().getContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }

    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Add Document");
    }
}