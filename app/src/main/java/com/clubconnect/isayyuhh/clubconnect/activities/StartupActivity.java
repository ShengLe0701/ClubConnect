package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.clubconnect.isayyuhh.clubconnect.R;

/**
 * Created by isayyuhh on 4/30/16.
 */
public class StartupActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_startup);

        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
