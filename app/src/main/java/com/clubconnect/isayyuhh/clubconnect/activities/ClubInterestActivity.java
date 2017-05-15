package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.InterestAdapter;
import com.clubconnect.isayyuhh.clubconnect.objects.Interest;
import com.clubconnect.isayyuhh.clubconnect.objects.SubInterest;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubInterestActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.status_bar)
    FrameLayout statusBar;
    @Bind(R.id.recycler_view)
    RecyclerView lv;
    @Bind(R.id.interest_fab)
    FloatingActionButton fab;

    private InterestAdapter rvAdapter;
    private ArrayList<SubInterest> interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_interest);
        ButterKnife.bind(this);

        this.setToolbar();
        this.setRecyclerView();
        this.setFab();
        this.getCategories();
    }

    private void setRecyclerView() {
        this.interests = new ArrayList<>();
        this.rvAdapter = new InterestAdapter(mContext, interests, fab);
        this.lv.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void setToolbar() {
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(this.str(R.string.label_interests));
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT < 19) statusBar.getLayoutParams().height = 0;
    }

    private void setFab() {
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabListener();
            }
        });
    }

    public void fabListener() {
        ArrayList<String> categoryList = new ArrayList<>();
        ArrayList<String> subcategoryList = new ArrayList<>();
        for (SubInterest subinterest : this.interests) {
            Interest interest = (Interest) subinterest;
            if (interest.added) {
                categoryList.add(interest.name);
                for (String subcategory : interest.subcategories) {
                    subcategoryList.add(subcategory);
                }
            }
        }
        Intent intent = new Intent(mContext, SubInterestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putStringArrayListExtra(this.str(R.string.intent_categories), categoryList);
        intent.putStringArrayListExtra(this.str(R.string.intent_subcategories), subcategoryList);
        startActivity(intent);
    }

    public void getCategories() {
        String[] categories = getResources().getStringArray(R.array.categories);
        for (int i = 0; i < categories.length; i++) {
            String[] subcategories = getResources().getStringArray(getResources().getIdentifier(categories[i].replace("&", "").replace(" ", ""), "array", "strings"));

            Interest interest = new Interest(categories[i], subcategories);
            interests.add(interest);
        }
        Interest everythingElse = (Interest) interests.get(3);
        interests.remove(3);
        interests.add(everythingElse);
        lv.setAdapter(rvAdapter);
        /*
        ParseQuery<ParseObject> query = new ParseQuery<>(this.str(R.string.label_category));
        query.orderByAscending(this.str(R.string.parse_ascending));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject object : list) {
                        if (object == null) continue;
                        Interest interest = new Interest(object);
                        interests.add(interest);
                    }
                    Interest everythingElse = (Interest) interests.get(3);
                    interests.remove(3);
                    interests.add(everythingElse);
                    lv.setAdapter(rvAdapter);
                } else {
                    Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
