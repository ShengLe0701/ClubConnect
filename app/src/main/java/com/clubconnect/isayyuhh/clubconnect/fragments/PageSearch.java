package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.tabs.TabLayout;

public class PageSearch extends BaseViewPagerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager, container, false);
        this.mTabs = (TabLayout) v.findViewById(R.id.tabs);
        this.mPager = (ViewPager) v.findViewById(R.id.pager);
        this.setViewPagerAndTabs(R.array.tabs_search);
        return v;
    }

    @Override
    protected Fragment getTabFragment(int position) {
        Fragment fragment = null;
        if (position == 0) fragment = new ListSearchClubs();
        else if (position == 1) fragment = new ListSearchEvents();
        return fragment;
    }
}
