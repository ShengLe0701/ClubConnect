package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.SubInterestAdapter;
import com.clubconnect.isayyuhh.clubconnect.objects.SubInterest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubPeopleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_people);
        ButterKnife.bind(this);

        this.setUpNames();
    }

    private void setUpNames() {
        Intent intent = getIntent();
        List<String> peopleNames = intent.getStringArrayListExtra("PEOPLENAMES");

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.row_people_list, peopleNames);

        ListView listView = (ListView) findViewById(R.id.club_analytics_people_list);
        listView.setAdapter(adapter);
    }

}
