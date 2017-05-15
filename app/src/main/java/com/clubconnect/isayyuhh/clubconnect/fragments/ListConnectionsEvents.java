package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerClub;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerEvent;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

public class ListConnectionsEvents extends BaseListFragment {

    private ArrayList<Event> events;
    private RecyclerEvent rvAdapter;
    private ParseRelation<Event> relation;
    private ParseQuery<Event> query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        this.events = new ArrayList<>();
        this.getEvents(v);

        this.rv = (RecyclerView)v.findViewById(R.id.recycler_view);
        return v;
    }

    private void getEvents(final View view) {
        this.relation = user.getRelation("savedEvents");
        this.query = this.relation.getQuery();
        this.query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : list) {
                        Event event = (Event) parseObject;
                        event.set();
                        events.add(event);

                        event.saveInBackground();

                        rv = (RecyclerView) view.findViewById(R.id.recycler_view);
                        rv.setLayoutManager(new LinearLayoutManager(context));
                        rvAdapter = new RecyclerEvent(events, context, getActivity());
                        rv.setAdapter(rvAdapter);
                    }
                } else {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
