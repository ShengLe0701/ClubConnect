package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.tabs.TabLayout;

/**
 * Created by isayyuhh on 5/1/16.
 */
public class Tutorial extends BaseFragment {

    private int res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tutorial, container, false);
        res = getArguments().getInt("res");
        ImageView iv = (ImageView)v.findViewById(R.id.image);
        iv.setImageResource(res);
        return v;
    }
}
