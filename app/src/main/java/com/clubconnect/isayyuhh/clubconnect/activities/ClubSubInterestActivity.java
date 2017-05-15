package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.SubInterestAdapter;
import com.clubconnect.isayyuhh.clubconnect.objects.SubInterest;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubSubInterestActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.status_bar)
    FrameLayout statusBar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.interest_fab)
    FloatingActionButton fab;

    private ArrayList<String> categories;
    private ArrayList<String> subcategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_interest);
        ButterKnife.bind(this);

        this.setToolbar();
        this.setupGrid();
        this.setFab();
    }

    private void setToolbar() {
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(this.str(R.string.label_subinterests));
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT < 19) this.statusBar.getLayoutParams().height = 0;
    }

    private void setupGrid() {
        Intent intent = getIntent();

        this.categories = intent.getStringArrayListExtra(this.str(R.string.intent_categories));
        this.subcategories = intent.getStringArrayListExtra(this.str(R.string.intent_subcategories));

        ArrayList<SubInterest> subinterests = new ArrayList<>();
        for (String subcategory : this.subcategories) {
            subinterests.add(new SubInterest(subcategory));
        }

        SubInterestAdapter adapter = new SubInterestAdapter(this, subinterests, fab);
        this.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        this.recyclerView.setAdapter(adapter);
    }

    private void setFab() {
        this.fab.hide();
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAction();
            }
        });
    }

    private void fabAction() {
        if (ParseUser.getCurrentUser() == null || ParseAnonymousUtils.isLinked(
                ParseUser.getCurrentUser())) {
            Intent intent = new Intent(mContext, SignUpAccount.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(str(R.string.intent_selected_categories), this.categories);
            intent.putExtra(str(R.string.intent_selected_subcategories), this.subcategories);
            this.startActivity(intent);
        } else {
            ParseUser user = ParseUser.getCurrentUser();
            user.put(str(R.string.parse_categories), categories);
            user.put(str(R.string.parse_subcategories), subcategories);
            user.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Intent intent = new Intent(ClubSubInterestActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
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
