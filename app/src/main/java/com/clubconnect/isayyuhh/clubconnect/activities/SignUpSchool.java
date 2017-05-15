package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.datas.StudentSignUpData;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
//This class is for inputing the school name in progressing signup.
public class SignUpSchool extends BaseActivity {

    @Bind(R.id.sign_up_b)
    Button signUpB;
    @Bind(R.id.sign_up_school)
    Spinner schoolET;

//    ArrayList<String> subArr;
//    ArrayList<String> catArr;

//    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_school);
        ButterKnife.bind(this);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        schoolET.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long id) {
                TextView selectedText = (TextView) arg0.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.WHITE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // do stuff

            }
        });


        this.getSchools();
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

        if (schoolET.getSelectedItem().toString().equals("Choose a School")) {
            Toast.makeText(SignUpSchool.this, "Error : Please choose a School.", Toast.LENGTH_SHORT).show();
            invalidform = true;
        }
        if (invalidform) {
            return;
        }

        Intent intent = new Intent(mContext, SignUpEmail.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        StudentSignUpData.getInstance().m_strSchool = schoolET.getSelectedItem().toString().toLowerCase();

        startActivity(intent);

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

    //This method gets all the school from Parse database.
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

}
