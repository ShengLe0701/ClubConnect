package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.activities.BaseAirplaneActivity;
import com.clubconnect.isayyuhh.clubconnect.objects.Notification;

import java.util.List;

public class PageMessages extends BaseAirplaneFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messages, container, false);


        List<Notification> notifications = this.airplane.getNotificationArr();
        System.out.println("messages are :");
        System.out.println(notifications);

        ArrayAdapter adapter = new ArrayAdapter<Notification>(getActivity(), R.layout.row_messages, notifications);

        ListView listView = (ListView) v.findViewById(R.id.club_messages_list);
        listView.setAdapter(adapter);
        return v;
    }
}
