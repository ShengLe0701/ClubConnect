package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.datas.StudentSignUpData;

import butterknife.Bind;
import butterknife.ButterKnife;

//This class is for inputing the email and password in progressing signup.
public class SignUpEmail extends BaseActivity {

    @Bind(R.id.sign_up_b)
    Button signUpB;
    @Bind(R.id.sign_up_email)
    EditText emailET;
    @Bind(R.id.sign_up_password)
    EditText passwordET;

//    ArrayList<String> subArr;
//    ArrayList<String> catArr;

//    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);
        ButterKnife.bind(this);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        this.setFab();
    }

    private void setFab() {
     //   signUpB.hide();
        signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabListener();
            }
        });
    }

    //This method will be called when user click the next button.
    public void fabListener() {
        boolean invalidform = false;

//        if (!emailET.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
//            Toast.makeText(SignUpEmail.this, "Error : Please enter a valid email.", Toast.LENGTH_SHORT).show();
//            invalidform = true;
//        }
        if (passwordET.getText().toString().equals("")) {
            Toast.makeText(SignUpEmail.this, "Error : Please enter a password.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }

        if (invalidform) {
            return;
        }

        Intent OutIntent = new Intent(mContext, SignUpName.class);
        OutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        StudentSignUpData.getInstance().m_strEmail = emailET.getText().toString();
        StudentSignUpData.getInstance().m_strPassword = passwordET.getText().toString();

        startActivity(OutIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_activity_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
