package com.clubconnect.isayyuhh.clubconnect.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerClub;
import com.clubconnect.isayyuhh.clubconnect.callbacks.CallbackHome;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageConnections;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageForYou;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageLit;
import com.clubconnect.isayyuhh.clubconnect.fragments.PageSearch;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Club_new;
import com.clubconnect.isayyuhh.clubconnect.objects.Clubs;
import com.clubconnect.isayyuhh.clubconnect.objects.Clubs_new;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.objects.Event_new;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.clubconnect.isayyuhh.clubconnect.objects.Events_new;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("CommitTransaction")
public class HomeActivity extends BaseHomeActivity implements CallbackHome {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.status_bar)
    FrameLayout statusBar;

    @Bind(R.id.tab_bar_popular)
    LinearLayout popularButton;
    @Bind(R.id.tab_bar_for_you)
    LinearLayout foryouButton;
    @Bind(R.id.tab_bar_search)
    LinearLayout searchButton;
    @Bind(R.id.tab_bar_connections)
    LinearLayout connectionsButton;

    @Bind(R.id.home_popular_iv)
    ImageView popularImage;
    @Bind(R.id.home_for_you_iv)
    ImageView foryouImage;
    @Bind(R.id.home_search_iv)
    ImageView searchImage;
    @Bind(R.id.home_connections_iv)
    ImageView connectionsImage;

    @Bind(R.id.home_popular_tv)
    TextView popularText;
    @Bind(R.id.home_for_you_tv)
    TextView foryouText;
    @Bind(R.id.home_search_tv)
    TextView searchText;
    @Bind(R.id.home_connections_tv)
    TextView connectionsText;
    @Bind(R.id.scan_fab)
    FloatingActionButton fab;


    private Clubs clubs;
    private Events events;
    private List<String> subcategories;
    private boolean init = true;

    private int index;
    private static int counter;

    private FragmentManager fm;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        System.out.println("In Ground Mode");

        this.setFab();
        this.initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!init) {
            FragmentTransaction ft = this.fm.beginTransaction();
            ft.remove(fragments.get(3));
            ft.add(R.id.fragment_container, fragments.get(3), this.str(R.string.tab_connections));
            ft.commit();
        }
    }

    private void initialize() {
        this.mUser = ParseUser.getCurrentUser();
        this.fragments = new ArrayList<>();
        this.clubs = new Clubs();
        this.events = new Events();
        this.subcategories = new ArrayList<>();
        this.fm = this.getSupportFragmentManager();
        this.index = 1;

        this.setToolbar();
        this.subscribeConnections();
        this.subscribeGoings();
        this.getUserSubcategories();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(this.str(R.string.tab_foryou));
            this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (Build.VERSION.SDK_INT < 19) this.statusBar.getLayoutParams().height = 0;
    }

    private void setFragments() {
        this.fragments = new ArrayList<>();
        this.fragments.add(new PageForYou());
        this.fragments.add(new PageLit());
        this.fragments.add(new PageSearch());
        this.fragments.add(new PageConnections());

        List<String> tags = new ArrayList<>();
        tags.add(this.str(R.string.tab_foryou));
        tags.add(this.str(R.string.tab_lit));
        tags.add(this.str(R.string.tab_search));
        tags.add(this.str(R.string.tab_connections));

        int counter = 0;
        for (Fragment fragment : this.fragments) {
            this.addFragment(fragment, tags.get(counter++));
        }
        this.hideFragments(index);
        this.setButtons();
    }

    private void setButtons() {
        List<LinearLayout> buttons = new ArrayList<>();
        buttons.add(this.foryouButton);
        buttons.add(this.popularButton);
        buttons.add(this.searchButton);
        buttons.add(this.connectionsButton);

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
                    foryouImage.setImageResource(index == 1 ? R.drawable.foryou_selected
                            : R.drawable.foryou_unselected);
                    popularImage.setImageResource(index == 2 ? R.drawable.lit_selected
                            : R.drawable.lit_unselected);
                    searchImage.setImageResource(index == 3 ? R.drawable.search_selected
                            : R.drawable.search_unselected);
                    connectionsImage.setImageResource(index == 4 ? R.drawable.clubconnect_selected
                            : R.drawable.clubconnect_unselected);

                    foryouText.setTextColor(ContextCompat.getColor(
                            mContext, index == 1 ? R.color.primary : R.color.md_grey_500));
                    popularText.setTextColor(ContextCompat.getColor(
                            mContext, index == 2 ? R.color.primary : R.color.md_grey_500));
                    searchText.setTextColor(ContextCompat.getColor(
                            mContext, index == 3 ? R.color.primary : R.color.md_grey_500));
                    connectionsText.setTextColor(ContextCompat.getColor(
                            mContext, index == 4 ? R.color.primary : R.color.md_grey_500));

                    switch (index) {
                        case 1:
                            toolbar.setTitle("For You");
                            fab.show();
                            break;
                        case 2:
                            toolbar.setTitle("Lit");
                            fab.hide();
                            break;
                        case 3:
                            toolbar.setTitle("Search");
                            fab.show();
                            break;
                        case 4:
                            toolbar.setTitle("Connections");
                            fab.hide();
                            break;
                        case 5:
                            toolbar.setTitle("Scan");
                            break;
                    }
                    hideFragments(index);
                }
            }
        });
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.add(R.id.fragment_container, fragment, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void hideFragments(int index) {
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        for (int i = 1; i <= 4; i++) {
            ft.hide(this.fragments.get(i - 1));
            if (i == index) {
                ft.show(this.fragments.get(i - 1));
            }
        }
        ft.commit();
    }

    private void getUserSubcategories() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo(this.str(R.string.parse_objectid), this.mUser.getObjectId());
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    ParseUser parseUser = objects.get(0);
                    JSONArray jsonSubcategories = parseUser.getJSONArray(str(R.string.parse_subcategories));
                    for (int i = 0; jsonSubcategories != null && i < jsonSubcategories.length(); i++) {
                        try {
                            subcategories.add(jsonSubcategories.get(i).toString());
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }

                    counter = 0;
                    getClub();
                    getEvent();
                } else {
                    Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getClub() {


        ParseQuery<Club> query = new ParseQuery<>("Club");
        query.whereEqualTo("school", this.mUser.get("school"));
        query.orderByAscending(this.str(R.string.parse_ascending));
        query.findInBackground(new FindCallback<Club>() {
            @Override
            public void done(List<Club> list, ParseException e) {
                if (e == null) {
                    for (String subcategory : subcategories) {
                        for (Club club : list) {
                            if (!club.getBoolean("approved")) continue;
                            club.set();
                            club.setMatching(subcategories);
                            for (String currentSubcategory : club.subcategories) {
                                if (currentSubcategory == null) continue;
                                if (currentSubcategory.equals(subcategory)) {
                                    boolean found = false;
                                    for (Club currentClub : clubs.list()) {
                                        if (currentClub.title.equals(club.title)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) clubs.add(club);
                                }
                            }
                        }
                    }
                    mUser.put("ForYouClubs", clubs.list().size());
                    mUser.saveInBackground();
                    incrementCounter();
                } else {
                    Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getEvent() {
        ParseQuery<Event> query = new ParseQuery<>("Event");
        query.whereEqualTo("school", this.mUser.get("school"));
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    for (String subcategory : subcategories) {
                        for (Event event : list) {
                            event.set();
                            if (!event.getBoolean("approved")) continue;
                            if (event.subcategories == null) continue;
                            for (String currentSubcategory : event.subcategories) {
                                if (currentSubcategory == null) continue;
                                if (currentSubcategory.equals(subcategory)) {
                                    boolean found = false;
                                    for (Event currentEvent : events.list()) {
                                        if (currentEvent.title.equals(event.title)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) events.add(event);
                                }
                            }
                        }
                    }
                    events = sort(events);
                    mUser.put("ForYouEvents", events.list().size());
                    mUser.saveInBackground();
                    incrementCounter();
                } else {
                    Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void incrementCounter() {
        if (++counter > 1 && init) {
            setFragments();
            init = false;
        }
    }

    @Override
    public Clubs getClubArr() {
        return this.clubs;
    }

    @Override
    public Events getEventsArr() {
        return this.events;
    }


    private void setFab() {
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAction();
            }
        });
    }

    private void fabAction() {
        Intent intent = new Intent(mContext, ScanActivity.class);
        startActivity(intent);
    }

    // URL must include path
    private Clubs_new getClubsForYou(String url) throws IOException {
        Clubs_new nClub = new Clubs_new();
        InputStream is = null;

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("clubGET", "The response is: " + response);

            if(response == 200) {
                try {
                    is = conn.getInputStream();
                    if (inputStreamToString(is, conn.getContentLength()) != null) {
                        JSONObject j = new JSONObject(inputStreamToString(is, conn.getContentLength()));
                        JSONArray clubs = j.getJSONArray("clubs");
                        for (int i = 0; i < clubs.length(); i++) {
                            // API is incomplete, can't really finish
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            Log.d("clubGet", "Unable to retrieve web page. URL may be invalid.");
        }
        return nClub;
    }

    // URL must include path
    private Events_new getEventsForYou(String url) throws IOException {
        Events_new nEvent = new Events_new();
        InputStream is = null;

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("eventGET", "The response is: " + response);

            if(response == 200) {
                try {
                    is = conn.getInputStream();
                    if (inputStreamToString(is, conn.getContentLength()) != null) {
                        JSONObject j = new JSONObject(inputStreamToString(is, conn.getContentLength()));
                        JSONArray events = j.getJSONArray("events");
                        for (int i = 0; i < events.length(); i++) {
                            // API is incomplete, can't really finish
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            Log.d("clubGet", "Unable to retrieve web page. URL may be invalid.");
        }

        return nEvent;
    }

    private String inputStreamToString(InputStream stream, int len)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
