package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.tabs.TabLayout;

/**
 * Base template for a ViewPager Fragment
 */
public abstract class BaseViewPagerFragment extends BaseFragment {

    protected ViewPager mPager;
    protected TabLayout mTabs;

    protected void setViewPagerAndTabs(int arrayRes) {
        this.mPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), arrayRes));
        this.mTabs.setDistributeEvenly(true);
        this.mTabs.setBackgroundColor(ContextCompat.getColor(context, R.color.ClubConnect));
        this.mTabs.setSelectedIndicatorColors(ContextCompat.getColor(context, R.color.White));
        this.mTabs.setViewPager(mPager);
        this.mPager.setOffscreenPageLimit(2);
    }

    protected abstract Fragment getTabFragment(int position);

    protected class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] tabs;

        public MyPagerAdapter(FragmentManager fm, int arrayRes) {
            super(fm);
            this.tabs = getResources().getStringArray(arrayRes);
        }

        @Override
        public Fragment getItem(int position) {
            return getTabFragment(position);
        }

        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
