package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_student_sign_up_b)
    Button studentSignUpB;
    @Bind(R.id.main_organization_sign_up_b)
    Button organizationSignUpB;
    @Bind(R.id.main_sign_in_b)
    TextView signInB;

    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/LeagueSpartan-Bold.otf");
        TextView tv=(TextView)findViewById(R.id.clubconnect);
        tv.setTypeface(face);
        Button btn1=(Button)findViewById(R.id.main_organization_sign_up_b);
        btn1.setTypeface(face);
        Button btn2=(Button)findViewById(R.id.main_student_sign_up_b);
        btn2.setTypeface(face);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (ParseUser.getCurrentUser() != null && !ParseAnonymousUtils.isLinked(
                ParseUser.getCurrentUser())) {
            if (ParseUser.getCurrentUser().get("isClub") != null && (boolean) ParseUser.getCurrentUser().get("isClub")) {
                Intent intent = new Intent(mContext, MaintenanceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        String token = "f37b115d6ef1423fa139bc32784c6df8";
        MixpanelAPI mix = MixpanelAPI.getInstance(this, token);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseInstallation install = ParseInstallation.getCurrentInstallation();
        install.saveInBackground();

        // set button up for student connection
        this.studentSignUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignUpSchool.class);
//                Intent intent = new Intent(mContext, SignUpAccount.class);
                startActivity(intent);
            }
        });

        // set button up for organization connection
        this.organizationSignUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClubSignUpAccount.class);
                startActivity(intent);
            }
        });

        // set button up for log in
        this.signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginAccount.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onBackPressed() {
        if (this.exit) {
            this.finish();
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            this.exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2 * 1000);
        }
    }
}
