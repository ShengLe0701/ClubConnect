package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.callbacks.CallbackAirplane;
import com.clubconnect.isayyuhh.clubconnect.callbacks.CallbackHome;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageEvents;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageMessages;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageAnalytics;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageEditProfile;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.ClubInfo;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.clubconnect.isayyuhh.clubconnect.objects.Notification;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AirplaneActivity extends BaseAirplaneActivity implements CallbackAirplane {

    @Bind(R.id.airplane_toolbar)
    Toolbar toolbar;
    @Bind(R.id.airplane_status_bar)
    FrameLayout statusBar;

    @Bind(R.id.tab_bar_events)
    LinearLayout eventsButton;
    @Bind(R.id.airplane_events_iv)
    ImageView eventsImage;
    @Bind(R.id.airplane_events_tv)
    TextView eventsText;

    @Bind(R.id.tab_bar_messages)
    LinearLayout messagesButton;
    @Bind(R.id.airplane_messages_iv)
    ImageView messagesImage;
    @Bind(R.id.airplane_messages_tv)
    TextView messagesText;

    @Bind(R.id.tab_bar_analytics)
    LinearLayout analyticsButton;
    @Bind(R.id.airplane_analytics_iv)
    ImageView analyticsImage;
    @Bind(R.id.airplane_analytics_tv)
    TextView analyticsText;

    @Bind(R.id.tab_bar_edit_profile)
    LinearLayout editProfileButton;
    @Bind(R.id.airplane_edit_profile_iv)
    ImageView editProfileImage;
    @Bind(R.id.airplane_edit_profile_tv)
    TextView editProfileText;

    private Events events;
    private List<Notification> messages;
    private boolean init = true;

    private int index;
    private static int counter;

    private FragmentManager fm;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane);
        ButterKnife.bind(this);
        System.out.println("In Airplane Mode");

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<Club> query = new ParseQuery<>("Club");
        query.getInBackground(user.getString("clubid"), new GetCallback<Club>() {
            @Override
            public void done(Club object, ParseException e) {
                if (e == null) {
                    mClub = new ClubInfo(object);
                    System.out.println("mClub is: "+mClub);
                    System.out.println("events: "+mClub.title);

                    initialize();
                } else {
                    System.out.println("Error : "+e);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!init) {
            FragmentTransaction ft = this.fm.beginTransaction();
            /*
            ft.remove(fragments.get(3));
            ft.add(R.id.airplane_fragment_container, fragments.get(3), this.str(R.string.tab_events));
            ft.commit();
            */
        }
    }

    private void initialize() {
        this.fragments = new ArrayList<>();
        this.events = new Events();
        this.fm = this.getSupportFragmentManager();
        this.index = 1;

        this.setToolbar();
        this.incrementCounter();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(this.str(R.string.tab_events));
            this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (Build.VERSION.SDK_INT < 19) this.statusBar.getLayoutParams().height = 0;
    }

    private void setFragments() {
        this.fragments = new ArrayList<>();
        this.fragments.add(new PageEvents());
        this.fragments.add(new PageMessages());
        this.fragments.add(new PageAnalytics());
        this.fragments.add(new PageEditProfile());

        List<String> tags = new ArrayList<>();
        tags.add(this.str(R.string.tab_events));
        tags.add(this.str(R.string.tab_messages));
        tags.add(this.str(R.string.tab_analytics));
        tags.add(this.str(R.string.tab_edit_profile));

        int counter = 0;
        for (Fragment fragment : this.fragments) {
            this.addFragment(fragment, tags.get(counter++));
        }
        this.hideFragments(index);
        this.setButtons();
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(R.id.airplane_fragment_container, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }
    private void hideFragments(int index) {
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        for (int i = 1; i <= this.fragments.size(); i++) {
            ft.hide(this.fragments.get(i - 1));
            if(i == index) {
                ft.show(this.fragments.get(i - 1));
            }
        }
        ft.commit();
    }
    private void setButtons() {
        List<LinearLayout> buttons = new ArrayList<>();
        buttons.add(this.eventsButton);
        buttons.add(this.messagesButton);
        buttons.add(this.analyticsButton);
        buttons.add(this.editProfileButton);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();

        int counter = 1;
        for (LinearLayout button : buttons) {
            this.setButtonListener(button, counter);
            counter++;
        }
    }
    private void setButtonListener(LinearLayout button, int counter) {
        final int check = counter;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != check) {
                    index = check;
                    eventsImage.setImageResource(index == 1 ? R.drawable.events_selected
                        : R.drawable.events_unselected);
                    messagesImage.setImageResource(index == 2 ? R.drawable.messages_selected
                        : R.drawable.messages_unselected);
                    analyticsImage.setImageResource(index == 3 ? R.drawable.analytics_selected
                        : R.drawable.analytics_unselected);
                    editProfileImage.setImageResource(index == 4 ? R.drawable.edit_profile_selected
                        : R.drawable.edit_profile_unselected);

                    eventsText.setTextColor(ContextCompat.getColor(
                            mContext, index == 1 ? R.color.primary : R.color.md_grey_500));
                    messagesText.setTextColor(ContextCompat.getColor(
                            mContext, index == 2 ? R.color.primary : R.color.md_grey_500));
                    analyticsText.setTextColor(ContextCompat.getColor(
                            mContext, index == 3 ? R.color.primary : R.color.md_grey_500));
                    editProfileText.setTextColor(ContextCompat.getColor(
                            mContext, index == 4 ? R.color.primary : R.color.md_grey_500));

                    switch (index) {
                        case 1:
                            toolbar.setTitle("Events");
                            break;
                        case 2:
                            toolbar.setTitle("Messages");
                            break;
                        case 3:
                            toolbar.setTitle("Analytics");
                            break;
                        case 4:
                            toolbar.setTitle("Edit Profile");
                            break;
                        default:
                            break;
                    }
                    hideFragments(index);
                }
            }
        });
    }

    @Override
    public ParseRelation<ParseObject> getEventArr() {
        return this.mClub.clubEvents;
    }
    public List<Notification> getNotificationArr() {
        return this.mClub.sentNotifications;
    }
    public int getConnected() { return this.mClub.connected; }
    public int getMarketSize(){ return this.mClub.marketSize; }
    public List<ParseObject> getInterested() { return this.mClub.interested; }
    public String getImageURL() { return this.mClub.imageURL; }
    public String getDate() { return this.mClub.day; }
    public String getTime() { return this.mClub.time; }
    public String getClubTitle() { return this.mClub.title; }
    public String getLocation() { return this.mClub.location; }
    public String getStatus() { return this.mClub.approved ? "Approved" : "Waiting Approval"; }

    private void incrementCounter() {
        System.out.println("mClub "+this.mClub);
        if (++counter > 0 && init) {
            setFragments();
            init = false;
        }
    }

    public void showPeople(View v) {
        Intent intent = new Intent(mContext, ClubPeopleActivity.class);
        List<String> peopleNames = new ArrayList<String>();
        for (ParseObject obj : this.mClub.interested) {
            peopleNames.add(obj.get("first_name").toString());
        }
        System.out.println("inital names: "+peopleNames);
        intent.putStringArrayListExtra("PEOPLENAMES", (ArrayList<String>)peopleNames);
        startActivity(intent);
    }
}
