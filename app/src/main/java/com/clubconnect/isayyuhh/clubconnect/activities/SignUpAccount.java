package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpAccount extends BaseActivity {

    @Bind(R.id.sign_up_b)
    Button signUpB;
    @Bind(R.id.sign_up_name)
    EditText nameET;
    @Bind(R.id.sign_up_email)
    EditText emailET;
    @Bind(R.id.sign_up_password)
    EditText passwordET;
    @Bind(R.id.sign_up_school)
    Spinner schoolET;

    ArrayList<String> subArr;
    ArrayList<String> catArr;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        this.subArr = new ArrayList<>();
        Intent intent = getIntent();
        this.subArr = intent.getStringArrayListExtra("SUB-CATEGORIES-SELECTED");
        this.catArr = intent.getStringArrayListExtra("CATEGORIES-SELECTED");

        this.signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpListener();
            }
        });
        this.getSchools();
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
    private void getSchools() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("control_panel");
        query.getInBackground("BTnYgZqvhN", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    JSONArray values = new JSONArray();
                    values = object.getJSONArray("student_schools");
                    String[] schools = new String[values.length()];
                    for (int i = 0; i < values.length(); i++) {
                        try {
                            schools[i] = values.getString(i);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    setUpSpinner(schools);
                }
            }
        });
    }
    private void setUpSpinner(String[] schools) {
        ArrayAdapter<String> schoolSpinnerArray = new ArrayAdapter<String>(
                this,
                R.layout.custom_spinner,
                schools
        );
        schoolSpinnerArray.setDropDownViewResource(R.layout.custom_spinner);
        schoolET.setAdapter(schoolSpinnerArray);
    }
    private void signUpListener(){
        boolean invalidform = false;
        if (nameET.getText().toString().equals("")) {
            Toast.makeText(SignUpAccount.this, "Error : Please enter a name.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }
        if (!emailET.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            Toast.makeText(SignUpAccount.this, "Error : Please enter a valid email.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }
        if (passwordET.getText().toString().equals("")) {
            Toast.makeText(SignUpAccount.this, "Error : Please enter a password.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }
        if (schoolET.getSelectedItem().toString().equals("Choose a School")) {
            Toast.makeText(SignUpAccount.this, "Error : Please choose a School.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }
        if (invalidform) {
            return;
        }

        parseSignUp();
        mAuth.createUserWithEmailAndPassword(emailET.getText().toString().trim(), passwordET.getText().toString())
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
        user.put("first_name", nameET.getText().toString());
        user.put("school", schoolET.getSelectedItem().toString().toLowerCase());
        user.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(SignUpAccount.this, LoginAccount.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("EMAIL", emailET.getText().toString());
                    intent.putExtra("PASSWORD", passwordET.getText().toString());
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
        user.setUsername(emailET.getText().toString().trim());
        user.setPassword(passwordET.getText().toString());
        user.setEmail(emailET.getText().toString().trim());


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpAccount.this,
                            "Signed up successfully",
                            Toast.LENGTH_SHORT)
                            .show();
                    addCategories();
                    finish();
                } else {
                    Toast.makeText(SignUpAccount.this,
                            "Error : " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
