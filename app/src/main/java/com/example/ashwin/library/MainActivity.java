package com.example.ashwin.library;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "auth";
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    //dref;
    private DatabaseReference ref;
    EditText editTextUid,editTextPassword;
    Button buttonSignup;
    boolean isadmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();



        editTextUid = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText2_passwd);
        buttonSignup = (Button) findViewById(R.id.loginbttn);
        buttonSignup.setOnClickListener(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid = "";
            uid = currentFirebaseUser.getUid();
            ref = FirebaseDatabase.getInstance().getReference().child("student/" + uid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    data d = dataSnapshot.getValue(data.class);
                    isadmin = d.isIsadmin();
                    if (isadmin) {

                        Intent k = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(k);
                        //finish();
                    } else {
                        Intent i = new Intent(MainActivity.this, action.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        } ;
    }


    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextUid.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        Toast.makeText(MainActivity.this, email + password, Toast.LENGTH_LONG).show();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


//

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            // FirebaseUser user = firebaseAuth.getCurrentUser();
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = "";
                            uid = currentFirebaseUser.getUid();
                            ref = FirebaseDatabase.getInstance().getReference().child("student/" + uid);
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    data d = dataSnapshot.getValue(data.class);
                                    isadmin = d.isIsadmin();
                                    if (isadmin) {

                                        Intent k = new Intent(MainActivity.this, AdminActivity.class);
                                        startActivity(k);
                                        //finish();
                                    } else {
                                        Intent i = new Intent(MainActivity.this, action.class);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });

    }

//    private void loginUser(){
//
//    }


    @Override
    public void onClick(View view) {
        //calling register method on click
        Log.d("email", editTextUid.toString());
        registerUser();

    }

    public void forgot(View view) {
        Intent y = new Intent(MainActivity.this, forgotpassword.class);
        startActivity(y);
    }

    public void text_msg(View view) {
        Intent j = new Intent(MainActivity.this,signup.class);
        startActivity(j);
    }
}
