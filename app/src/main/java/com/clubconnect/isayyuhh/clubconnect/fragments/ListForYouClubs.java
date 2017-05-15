package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerClub;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Clubs;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListForYouClubs extends BaseListFragment {

    private Clubs mClubs;
    private RecyclerClub rvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        this.mClubs = this.home.getClubArr();
        this.mClubs = sortClubs(this.mClubs);

        this.rv = (RecyclerView)v.findViewById(R.id.recycler_view);
        this.rv.setLayoutManager(new LinearLayoutManager(this.context));
        this.rvAdapter = new RecyclerClub(this.mClubs, this.context, this.getActivity());
        this.rv.setAdapter(this.rvAdapter);
        return v;
    }

    private Clubs sortClubs(Clubs toSort) {
        List<Club> copy = new ArrayList<>(toSort.list());
        Collections.sort(copy, new CompareMatching());
        return new Clubs(copy);
    }

    public class CompareMatching implements Comparator<Club> {
        @Override
        public int compare(Club lhs, Club rhs) {
            if (lhs.matching > rhs.matching) return -1;
            else if (lhs.matching < rhs.matching) return 1;
            return 0;
        }
    }
}
