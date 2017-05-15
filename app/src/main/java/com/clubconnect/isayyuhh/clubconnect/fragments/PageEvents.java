package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerEvent;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.clubconnect.isayyuhh.clubconnect.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class PageEvents extends BaseAirplaneFragment {
    protected RecyclerView rv;

    private ArrayList<Event> events;
    private RecyclerEvent rvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_events, container, false);
        ParseRelation<ParseObject> eventRelation = this.airplane.getEventArr();

        eventRelation.getQuery().findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                System.out.println("events size is :"+objects.size());
                Events clubEvents = new Events();
                for (ParseObject parseObject : objects) {
                    Event pEvent = (Event) parseObject;
                    pEvent.set();
                    clubEvents.add(pEvent);
                    System.out.println(parseObject);

                }
                System.out.println(clubEvents);
                rvAdapter = new RecyclerEvent(clubEvents, context, getActivity());
                rv = (RecyclerView) v.findViewById(R.id.recycler_view);
                rv.setLayoutManager(new LinearLayoutManager(context));
                rv.setAdapter(rvAdapter);
            }
        });
        return v;
    }
}
