package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PageLit extends BaseFragment {

    private ViewPager mPager;
    private MyPagerAdapter pagerAdapter;
    private ArrayList<Event> eventArr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lit, container, false);
        this.mPager = (ViewPager) v.findViewById(R.id.popular_pager);
        this.eventArr = new ArrayList<>();
        this.pagerAdapter = new MyPagerAdapter(getChildFragmentManager(), eventArr);
        this.mPager.setAdapter(pagerAdapter);
        this.getEvent();
        return v;
    }

    private void getEvent() {
        ParseQuery<Event> query = new ParseQuery<>("Event");
        query.orderByDescending("score");
        query.whereEqualTo("approved", true);
        query.whereEqualTo("happened", false);
        query.setLimit(10);
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    int count = 0;
                    for (Event pEvent : list) {
                        pEvent.set();
                        if (count <= 10 && activity.compareDates(activity.today(), pEvent.date) <= 0) {
                            count++;
                            eventArr.add(pEvent);
                        } else if (count > 10) break;
                    }
                    pagerAdapter.notifyDataSetChanged();
                    mPager.setAdapter(pagerAdapter);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Event> array;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Event> array) {
            super(fm);
            this.array = array;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ViewLit();
            Event event = array.get(position);
            Bundle args = new Bundle();
            args.putString("EVENT", event.id);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return array.size();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}

