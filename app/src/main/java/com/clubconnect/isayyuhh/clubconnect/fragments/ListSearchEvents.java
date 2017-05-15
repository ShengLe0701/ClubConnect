package com.clubconnect.isayyuhh.clubconnect.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerEvent;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.clubconnect.isayyuhh.clubconnect.parse.PEvent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ListSearchEvents extends BaseFragment {

    private EditText searchET;
    private ImageView searchIV;
    private Events found;
    private RecyclerEvent rvAdapter;
    private LinearLayoutManager llManager;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        this.searchET = (EditText) v.findViewById(R.id.search_et);
        this.searchIV = (ImageView) v.findViewById(R.id.search_iv);
        this.llManager = new LinearLayoutManager(context);
        this.found = new Events();

        this.rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        this.rv.setLayoutManager(llManager);
        this.rvAdapter = new RecyclerEvent(found, context, getActivity());
        this.rv.setAdapter(rvAdapter);

        setSearchView();
        return v;
    }

    private void setSearchView() {
        this.searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        this.searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        || (keyEvent.getKeyCode() == KeyEvent.KEYCODE_NAVIGATE_NEXT)))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    doSearch();
                }
                return false;
            }
        });
    }

    private void doSearch() {
        this.found.clear();
        this.getEvent(searchET.getText().toString());
        this.rvAdapter = new RecyclerEvent(found, context, getActivity());
        this.rv.setAdapter(rvAdapter);

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getEvent(final String search) {
        ParseQuery<Event> query = new ParseQuery<>("Event");
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    for (Event pEvent : list) {
                        pEvent.set();
                        if (!pEvent.getBoolean("approved")) continue;
                        if (pEvent.title.toLowerCase().contains(search.toLowerCase())) {
                            found.add(pEvent);
                        } else if (pEvent.location.toLowerCase().contains(search.toLowerCase())) {
                            found.add(pEvent);
                        }
                    }
                    found = activity.sort(found);
                    Toast.makeText(getActivity(), "Search: " + String.valueOf(found.list().size())
                            + " results", Toast.LENGTH_SHORT).show();
                    rvAdapter = new RecyclerEvent(found, context, getActivity());
                    rv.setAdapter(rvAdapter);
                } else {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
