package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.objects.Club_new;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubSignUpAccount extends BaseActivity {

    @Bind(R.id.sign_up_name)
    EditText nameET;
    @Bind(R.id.sign_up_email)
    EditText emailET;
    @Bind(R.id.sign_up_phone)
    EditText phoneET;
    @Bind(R.id.sign_up_password)
    EditText passwordET;
    @Bind(R.id.sign_up_school)
    Spinner schoolS;
    @Bind(R.id.sign_up_b)
    Button signUpB;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_sign_up);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        this.setSignUpButton();
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
                    values = object.getJSONArray("schools");
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
        schoolS.setAdapter(schoolSpinnerArray);
    }

    private void setSignUpButton() {
        this.signUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser user = new ParseUser();
                user.setUsername(emailET.getText().toString().trim());
                user.setEmail(emailET.getText().toString().trim());
                user.setPassword(passwordET.getText().toString());

                user.put("isClub", true);
                user.put("clubname", nameET.getText().toString().trim());
                user.put("school", schoolS.getSelectedItem().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            final ParseObject club = ParseObject.create("Club");
                            club.put("title", nameET.getText().toString().trim());
                            club.put("email", emailET.getText().toString().trim());
                            club.put("days", new ArrayList<String>());
                            club.put("times", new ArrayList<String>());
                            club.put("location", "");
                            club.put("categories", new ArrayList<String>());
                            club.put("subcategories", new ArrayList<String>());
                            club.put("phone", phoneET.getText().toString().trim());
                            club.put("email", emailET.getText().toString().trim().toLowerCase());
                            club.put("summary", "");
                            club.put("connected", 0);
                            club.put("approved", false);
                            club.put("sentNotifications", new ArrayList<String>());
                            club.put("prevconnections", 0);
                            club.put("marketSize", 0);
                            club.put("school", schoolS.getSelectedItem().toString());
                            club.put("receivedNotifications", new ArrayList<String>());
                            club.put("event_receiver", new ArrayList<String>());

                            final Club_new nClub = new Club_new();
                            nClub.title = nameET.getText().toString().trim();
                            nClub.email = emailET.getText().toString().trim().toLowerCase();
                            nClub.days = new ArrayList<String>();
                            nClub.times = new ArrayList<String>();
                            nClub.location = "";
                            nClub.categories = new ArrayList<String>();
                            nClub.subcategories = new ArrayList<String>();
                            nClub.phone = phoneET.getText().toString().trim();
                            nClub.summary = "";
                            nClub.connected = 0;
                            nClub.approved = false;
                            nClub.sentNotifications = new ArrayList<String>();
                            nClub.prevConnected = 0;
                            nClub.marketSize = 0;
                            nClub.school = schoolS.getSelectedItem().toString();
                            nClub.receivedNotifications = new ArrayList<String>();
                            nClub.eventReceivers = new ArrayList<String>();

                            club.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    user.put("clubid", club.getObjectId());
                                    try {
                                        user.save();
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }
//                                        set channel name
//                                        set club receiver
                                    try {
                                        club.save();
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }
//                                    Toast.makeText(ClubSignUpAccount.this, "Signed up successfully",
//                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ClubSignUpAccount.this,
                                            FinishMaintenanceActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                                            Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    FirebaseUser club = mAuth.getCurrentUser();
                                    mDatabase.child("clubs").child(club.getUid()).setValue(nClub);
                                    signUpClub(nClub);
                                    startActivity(intent);
                                    finish();
                                    }
                                });
                            } else {
                                Toast.makeText(ClubSignUpAccount.this,
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }

    private void signUpClub(Club_new n) {
        mAuth.createUserWithEmailAndPassword(emailET.getText().toString().trim(), passwordET.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ClubSignUpAccount.this, "Signed up successfully",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
