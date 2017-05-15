package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.clubconnect.isayyuhh.clubconnect.callbacks.CallbackActivity;
import com.clubconnect.isayyuhh.clubconnect.callbacks.CallbackHome;
import com.parse.ParseUser;

/**
 * Created by isayyuhh on 4/21/16.
 */
public abstract class BaseFragment extends Fragment {

    protected CallbackHome home;
    protected CallbackActivity activity;
    protected Context context;
    protected ParseUser user;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.home = (CallbackHome) activity;
        this.activity = (CallbackActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this.getActivity().getApplicationContext();
        this.user = ParseUser.getCurrentUser();
    }
}
