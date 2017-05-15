package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.parse.PEvent;
import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerEvent;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListForYouEvents extends BaseListFragment {

    private ArrayList<Event> events;
    private RecyclerEvent rvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        this.events = new ArrayList<>(home.getEventsArr().list());

        this.rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        this.rv.setLayoutManager(new LinearLayoutManager(context));
        this.rvAdapter = new RecyclerEvent(events, context, getActivity());
        this.rv.setAdapter(rvAdapter);
        return v;
    }
}
