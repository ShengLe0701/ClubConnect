package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.activities.BaseAirplaneActivity;

public class PageAnalytics extends BaseAirplaneFragment {
    private TextView potentialText;
    private TextView connectionsText;
    private TextView peopleText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_analytics, container, false);
        System.out.println("In Club Analytics");
        this.potentialText = (TextView) v.findViewById(R.id.club_analytics_potential);
        this.connectionsText = (TextView) v.findViewById(R.id.club_analytics_connections);
        this.peopleText = (TextView) v.findViewById(R.id.club_analytics_people);
        this.getPotential();
        this.getConnections();

        return v;
    }

    private void getPotential() {
        System.out.println("marketSize: "+this.airplane.getMarketSize());
        this.potentialText.setText(""+this.airplane.getMarketSize()+"");
    }
    private void getConnections() {
        System.out.println("connections: "+this.airplane.getConnected());
        this.connectionsText.setText(""+this.airplane.getConnected()+"");
    }
}
