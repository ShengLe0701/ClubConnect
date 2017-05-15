package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clubconnect.isayyuhh.clubconnect.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FinishMaintenanceActivity extends AppCompatActivity {
    @Bind(R.id.site_button)
    Button site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_maintenance);
        ButterKnife.bind(this);

        this.setSiteButton();
    }

    private void setSiteButton() {
        this.site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.novateurapp.xyz/#/login";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
