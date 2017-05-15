package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.datas.StudentSignUpData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;

import butterknife.ButterKnife;

//This class will be called as user want to save the information he inputed.
public class SignUpProcessingActivity extends BaseActivity {

    ArrayList<String> subArr;
    ArrayList<String> catArr;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String emailET = null;
    private String passwordET = null;
    private String nameET = null;
    private String schoolET = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_progressing);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        this.subArr = new ArrayList<>();
        Intent intent = getIntent();
        this.subArr = intent.getStringArrayListExtra("SUB-CATEGORIES-SELECTED");
        this.catArr = intent.getStringArrayListExtra("CATEGORIES-SELECTED");

        emailET = StudentSignUpData.getInstance().m_strEmail;
        passwordET = StudentSignUpData.getInstance().m_strPassword;
        nameET = StudentSignUpData.getInstance().m_strName;
        schoolET = StudentSignUpData.getInstance().m_strSchool;

        parseSignUp();
        mAuth.createUserWithEmailAndPassword(emailET.trim(), passwordET.toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            mDatabase.child("users").child(user.getUid()).setValue(user);
                        }
                    }
                });

    }


    private void addCategories(){
        ParseUser user = ParseUser.getCurrentUser();
        user.put("userSub_categories", subArr);
        user.put("user_categories", catArr);
        user.put("first_name", nameET.toString());
        user.put("school", schoolET.toLowerCase());

        user.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(SignUpProcessingActivity.this, LoginAccount.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("EMAIL", emailET.toString());
                    intent.putExtra("PASSWORD", passwordET.toString());
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("SUB NOT SAVED", e.getMessage()+"");
                }
            }
        });
    }

    private void parseSignUp() {
        ParseUser user = new ParseUser();
        user.setUsername(emailET.toString().trim());
        user.setPassword(passwordET.toString());
        user.setEmail(emailET.toString().trim());


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpProcessingActivity.this,
                            "Signed up successfully",
                            Toast.LENGTH_SHORT)
                            .show();
                    addCategories();
                    finish();
                } else {
                    Toast.makeText(SignUpProcessingActivity.this,
                            "Error : " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
