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

//This class is for inputing the phone number in progressing signup.
public class SignUpPhone extends BaseActivity{

    @Bind(R.id.sign_up_b)
    Button signUpB;
    @Bind(R.id.sign_up_phone)
    EditText phoneET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_phone);
        ButterKnife.bind(this);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();

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

    public void fabListener() {
        boolean invalidform = false;

        if (phoneET.getText().toString().equals("")) {
            Toast.makeText(SignUpPhone.this, "Error : Please enter a digits.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }

        if (invalidform) {
            return;
        }

        Intent OutIntent = new Intent(mContext, InterestCategoryActivity.class);

        StudentSignUpData.getInstance().m_strPhone = phoneET.getText().toString();

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
