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
import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerClub;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Clubs;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ListSearchClubs extends BaseFragment {

    private EditText searchET;
    private ImageView searchIV;
    private List<Club> found;
    private RecyclerClub rvAdapter;
    private RecyclerView rv;
    private LinearLayoutManager llManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        this.searchET = (EditText) v.findViewById(R.id.search_et);
        this.searchIV = (ImageView) v.findViewById(R.id.search_iv);
        this.llManager = new LinearLayoutManager(context);
        this.found = new ArrayList<>();

        this.rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        this.rvAdapter = new RecyclerClub(found, context, getActivity());
        this.rv.setLayoutManager(llManager);
        this.rv.setAdapter(rvAdapter);

        this.setSearchView();

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
        this.getClub(searchET.getText().toString());
        this.rvAdapter.notifyDataSetChanged();
        this.rv.setAdapter(rvAdapter);

        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getClub(final String search) {
        ParseQuery<Club> query = new ParseQuery<>("Club");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Club>() {
            @Override
            public void done(List<Club> list, ParseException e) {
                if (e == null) {
                    for (Club pClub : list) {
                        pClub.set();
                        if (!pClub.getBoolean("approved")) continue;
                        if (pClub.title.toLowerCase().contains(search.toLowerCase())) {
                            found.add(pClub);
                            Log.d("SEARCH CLUB TITLE", pClub.title);
                        } else if (pClub.location.toLowerCase().contains(search.toLowerCase())) {
                            found.add(pClub);
                            Log.d("SEARCH CLUB LOCATION", pClub.location);
                        }
                    }
                    found = sortClubs(found);
                    Toast.makeText(getActivity(), "Search: " + String.valueOf(found.size())
                            + " results", Toast.LENGTH_SHORT).show();
                    rvAdapter.notifyDataSetChanged();
                    rv.setAdapter(rvAdapter);
                } else {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<Club> sortClubs(List<Club> toSort) {
        Collections.sort(toSort, new CompareAlpha());
        return toSort;
    }

    public class CompareAlpha implements Comparator<Club> {
        @Override
        public int compare(Club lhs, Club rhs) {
            if (lhs.title.toLowerCase().compareTo(rhs.title.toLowerCase()) < 0) return -1;
            else if (lhs.title.toLowerCase().compareTo(rhs.title.toLowerCase()) > 0) return 1;
            return 0;
        }
    }
}
