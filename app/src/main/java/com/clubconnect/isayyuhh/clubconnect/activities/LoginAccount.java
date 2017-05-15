package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginAccount extends BaseActivity {

    @Bind(R.id.log_in_b)
    Button login;
    @Bind(R.id.log_in_email)
    EditText email;
    @Bind(R.id.log_in_password)
    EditText password;
    @Bind(R.id.forgotpassword)
    TextView forgotpassowrd;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.checkAccountCreated();
        this.setLoginButton();

        forgotpassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPassword();
            }
        });
    }

    private void checkAccountCreated() {
        Intent intent = getIntent();
        this.email.setText("");
        this.password.setText("");
        if (intent != null) {
            String email = intent.getStringExtra("EMAIL");
            String password = intent.getStringExtra("PASSWORD");
            this.email.setText(email);
            this.password.setText(password);
        }

    }

    private void setLoginButton() {
        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(email.getText().toString().trim(),
                        password.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                System.out.println("Done");
                                if (user != null) {
                                    System.out.println("I'm not null");
                                    // if a club redirect to club sign up page
                                    if (user.get("isClub") != null && ((boolean) user
                                            .get("isClub"))) {
                                        System.out.println("I'm a club");
                                        Intent intent = new Intent(mContext, MaintenanceActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    // if student go to student side
                                    else {
                                        System.out.println("I'm a student");
                                        Intent intent = new Intent(mContext, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        userLogin();
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(LoginAccount.this, "Error", Toast.LENGTH_SHORT)
                                            .show();
                                    System.out.println(e);
                                }

                            }
                        });
            }
        });
    }

    private void userLogin() {
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            // todo: check if accessed by a club
                            // if club/user != null take to main page
//                            if (mDatabase.child("clubs").child(user.getUid()).getKey() == "clubs") {
//                                startActivity(new Intent(LoginAccount.this, ClubLoginAccount.class));
//                                finish();
//                            } else {

//                            }
                            finish();
                        }
//                        } else {
//                            Toast.makeText(LoginAccount.this, "Error", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
    }


    public void onResetPassword()
    {
        if (email.getText().toString().equals("")) {
            Toast.makeText(LoginAccount.this, "Error : Please enter a email.", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseUser.requestPasswordResetInBackground(email.getText().toString(),
                new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(LoginAccount.this, "An email was successfully sent with reset.", Toast.LENGTH_SHORT).show();
                            // An email was successfully sent with reset instructions.
                        } else {
                            // Something went wrong. Look at the ParseException to see what's up.
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
