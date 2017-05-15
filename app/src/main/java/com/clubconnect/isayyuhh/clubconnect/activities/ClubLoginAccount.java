package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubLoginAccount extends BaseActivity {

    @Bind(R.id.log_in_b)
    Button login;
    @Bind(R.id.log_in_email)
    EditText email;
    @Bind(R.id.log_in_password)
    EditText password;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        this.checkAccountCreated();
        this.setLoginButton();
    }

    private void checkAccountCreated() {
        Intent intent = getIntent();
        this.email.setText("ftorres2@ucsc.edu");
        this.password.setText("banana");
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
                                if (user != null) {
                                    if (user.get("isClub") == null || !((boolean) user
                                            .get("isClub"))) {
                                        Toast.makeText(ClubLoginAccount.this, "Not a Club Account",
                                                Toast.LENGTH_SHORT).show();
                                        ParseUser.logOut();
                                        return;
                                    }
                                    Intent intent = new Intent(mContext, MaintenanceActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    clubLogin();
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ClubLoginAccount.this, "Error", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
            }
        });
    }

    private void clubLogin() {
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser club = mAuth.getCurrentUser();
                        // club != null take to main page
                        if (task.isSuccessful()) {
                            finish();
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
