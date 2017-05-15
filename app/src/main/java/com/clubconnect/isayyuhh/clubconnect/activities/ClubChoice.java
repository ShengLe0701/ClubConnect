package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by isayyuhh on 5/8/16.
 */
public class ClubChoice extends BaseActivity {

    @Bind(R.id.club_login_name)
    EditText login_username;
    @Bind(R.id.club_login_password)
    EditText login_password;
    @Bind(R.id.sign_up)
    Button signUpB;
    @Bind (R.id.club_remember_password)
    Button rememberB;
    @Bind(R.id.login)
    Button logInB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_i_am_a_club);
        ButterKnife.bind(this);

        this.checkAccountCreated();
        this.setLoginButton();
        this.setSignUpButton();
    }

    private void checkAccountCreated() {
        Intent intent = getIntent();
        this.login_username.setText("");
        this.login_password.setText("");

        if (intent != null) {
            String email = intent.getStringExtra("EMAIL");
            String password = intent.getStringExtra("PASSWORD");
            this.login_username.setText(email);
            this.login_password.setText(password);
        }
    }

    private void setSignUpButton() {
        this.signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClubSignUpAccount.class);
                startActivity(intent);
            }
        });
    }
    private void setLoginButton() {
        this.logInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.printf("username : %s\n", login_username.getText().toString().trim());
                System.out.printf("password : %s\n", login_password.getText().toString());
                ParseUser.logInInBackground(login_username.getText().toString().trim(),
                        login_password.getText().toString(), new LogInCallback() {
                           @Override
                            public void done(ParseUser user, ParseException e) {
                               if (user != null) {
                                   System.out.printf("isClub : %s\n", user.get("isClub"));
                                   if (user.get("isClub") != null && !((boolean) user.get("isClub"))) {
                                       Toast.makeText(ClubChoice.this, "Not A User Account",
                                               Toast.LENGTH_SHORT).show();
                                       ParseUser.logOut();
                                       return;
                                   }
                                   Intent intent = new Intent(mContext, AirplaneActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                           Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);
                               } else {
                                   Toast.makeText(ClubChoice.this, "Error", Toast.LENGTH_SHORT).show();
                               }
                           }
                        });
            }
        });
    }
}
