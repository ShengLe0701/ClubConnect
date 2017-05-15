package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.fragments.Tutorial;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by isayyuhh on 5/1/16.
 */
public class TutorialViewPager extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.status_bar)
    FrameLayout statusBar;
    @Bind(R.id.interest_fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        this.setToolbar();
        this.setFab();
        this.setViewPager();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(this.str(R.string.label_interests));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT < 19) {
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.height = 0;
        }
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
        Intent intent = new Intent(mContext, SubInterestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void setViewPager() {
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mPager.setOffscreenPageLimit(2);
    }

    protected Fragment getFragment(int position) {
        Fragment fragment = new Tutorial();
        Bundle b = new Bundle();
        switch (position) {
            case 0:
                b.putInt("res", R.drawable.tutorial_interests);
                fab.hide();
                break;
            case 1:
                b.putInt("res", R.drawable.tutorial_foryou);
                fab.hide();
                break;
            case 2:
                b.putInt("res", R.drawable.tutorial_connect);
                fab.hide();
                break;
            case 3:
                b.putInt("res", R.drawable.tutorial_lit);
                fab.hide();
                break;
            case 4:
                b.putInt("res", R.drawable.tutorial_notifications);
                fab.hide();
                break;
            case 5:
                b.putInt("res", R.drawable.tutorial_discover);
                fab.show();
                break;
        }
        fragment.setArguments(b);
        return fragment;
    }

    protected class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
