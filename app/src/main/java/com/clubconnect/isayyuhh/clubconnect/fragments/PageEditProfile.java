package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import java.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.activities.ClubInterestActivity;
import com.clubconnect.isayyuhh.clubconnect.activities.InterestActivity;
import com.clubconnect.isayyuhh.clubconnect.activities.EditSummaryActivity;
import com.squareup.picasso.Picasso;

import com.github.jjobes.slidedaytimepicker.SlideDayTimePicker;
import com.github.jjobes.slidedaytimepicker.SlideDayTimeListener;


public class PageEditProfile extends BaseAirplaneFragment {
    private ImageView editProfileIV;
    private TextView dateTimeView;
    private Button dateTimeButton;
    private TextView currDateTime;
    private TextView currTitle;
    private TextView currLocation;
    private TextView currStatus;
    private EditText locationText;
    private Button categoriesButton;
    private Button summaryButton;
    private Button saveButton;

    private int localDay;
    private int localHour;
    private int localMinute;
    private String[] weekdays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Sunday"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        this.editProfileIV = (ImageView) v.findViewById(R.id.edit_profile_iv);

        this.setUpImage();

        this.dateTimeView = (TextView) v.findViewById(R.id.edit_profile_date_time);
        this.dateTimeView.setText(this.airplane.getDate()+" "+this.airplane.getTime());

        this.locationText = (EditText) v.findViewById(R.id.edit_profile_location);
        this.locationText.setText(this.airplane.getLocation());

        this.currDateTime = (TextView) v.findViewById(R.id.edit_profile_curr_date_time);
        this.currDateTime.setText(this.airplane.getDate()+" "+this.airplane.getTime());

        this.currTitle = (TextView) v.findViewById(R.id.edit_profile_curr_title);
        this.currTitle.setText(this.airplane.getClubTitle());

        this.currLocation = (TextView) v.findViewById(R.id.edit_profile_curr_location);
        this.currLocation.setText(this.airplane.getLocation());

        this.currStatus = (TextView) v.findViewById(R.id.edit_profile_curr_status);
        this.currStatus.setText(this.airplane.getStatus());

        this.dateTimeButton = (Button) v.findViewById(R.id.edit_profile_button_picker);
        this.categoriesButton = (Button) v.findViewById(R.id.edit_profile_categories);
        this.summaryButton = (Button) v.findViewById(R.id.edit_profile_summary);
        this.saveButton = (Button) v.findViewById(R.id.edit_profile_save_button);

        this.setUpButtons();

        return v;
    }

    final SlideDayTimeListener listener = new SlideDayTimeListener() {

        @Override
        public void onDayTimeSet(int day, int hour, int minute)
        {
            // Do something with the day, hour and minute
            // the user has selected.
            localDay = day;
            localHour = hour % 12 != 0 ? hour % 12 : 12;
            localMinute = minute;
            dateTimeView.setText(weekdays[day-1]+" "+localHour+":"+localMinute);
        }

        @Override
        public void onDayTimeCancel()
        {
            // The user has canceled the dialog.
            // This override is optional.
        }
    };

    private void setUpButtons() {
        this.dateTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SlideDayTimePicker.Builder(getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDay(1)
                        .setInitialHour(13)
                        .setInitialMinute(30)
                        .build()
                        .show();
            }
        });

        this.categoriesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("categories");
                Intent intent = new Intent(getActivity(), InterestActivity.class);
                startActivity(intent);
            }
        });

        this.summaryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("summary");
                Intent intent = new Intent(getActivity(), EditSummaryActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        this.saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("saving");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            System.out.println("summary works");
            System.out.println(data.getStringExtra("SUMMARY"));
        }
    }


    private void setUpImage() {
        Picasso.with(getActivity()).load(this.airplane.getImageURL()).resize(600, 240).into(editProfileIV);

        editProfileIV.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // your code here
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
    }
}
