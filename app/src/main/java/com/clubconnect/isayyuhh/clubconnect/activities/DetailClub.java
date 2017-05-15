package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.adapters.RecyclerEvent;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailClub extends BaseDetailActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.status_bar)
    FrameLayout statusBar;
    @Bind(R.id.club_image_iv)
    ImageView image;

    @Bind(R.id.club_name_tv)
    TextView title;
    @Bind(R.id.club_location_tv)
    TextView location;
    @Bind(R.id.club_date_tv)
    TextView date;
    @Bind(R.id.club_summary_tv)
    TextView summary;
    @Bind(R.id.recycler_view)
    RecyclerView rv;

    @Bind(R.id.contact_button)
    LinearLayout contactButton;
    @Bind(R.id.connect_button)
    LinearLayout connectButton;
    @Bind(R.id.share_button)
    LinearLayout shareButton;

    @Bind(R.id.connect_image)
    ImageView connectImage;

    private static final String SHARE_MSG_ONE = "Hey! Check out this amazing club ";
    private static final String SHARE_MSG_TWO = "! Also, check out Club Connect at"
            + "www.clubconnectucsc.com!";

    private Club mClub;
    private Events mEvents;
    private ParseRelation<Club> mUserClubRelation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_club);
        ButterKnife.bind(this);

        this.setActionBar(this.toolbar, this.statusBar, this.str(R.string.title_club));
        this.initialize();
    }

    private void initialize() {
        this.mEvents = new Events();
        this.mUserClubRelation = this.mUser.getRelation(this.str(R.string.parse_savedclubs));
        this.getClub();
    }

    private void getClub() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(this.str(R.string.intent_id));
        (new ParseQuery<Club>(this.str(R.string.parse_class_club))).getInBackground(id, new GetCallback<Club>() {
            public void done(Club club, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }

                mClub = club;
                mClub.set();

                Picasso.with(mContext).load(mClub.imageURL).into(image);
                title.setText(mClub.title);
                location.setText(mClub.location);
                summary.setText(mClub.summary);
                date.setText(mClub.date);

                setEventRelation();
                setButtons();
            }
        });
    }

    private void setEventRelation() {
        this.mClub.events.orderByAscending(str(R.string.parse_date));
        this.mClub.events.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }

                for (ParseObject parseObject : list) {
                    Event pEvent = (Event) parseObject;
                    pEvent.set();
                    mEvents.add(pEvent);
                }
                mEvents = sort(mEvents);
                RecyclerEvent rvAdapter = new RecyclerEvent(mEvents, mContext, DetailClub.this);
                rv.setLayoutManager(new LinearLayoutManager(mContext));
                rv.setAdapter(rvAdapter);
            }
        });
    }

    private void setButtons() {
        if (this.mClub == null) return;
        if (this.find(this.mClub.getObjectId(), this.mUserClubRelation.getQuery())) {
            this.connectImage.setImageResource(R.drawable.clubconnect_selected);
        }
        this.contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContactButton(mClub.phone, mClub.email, new AlertDialog.Builder(DetailClub.this));
            }
        });
        this.connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConnectButton();
            }
        });
        this.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShareButton(mClub.title, SHARE_MSG_ONE, SHARE_MSG_TWO);
            }
        });
    }

    protected void setConnectButton() {
        if (!find(this.mClub.getObjectId(), this.mUserClubRelation.getQuery())) {
            this.mUserClubRelation.add(this.mClub);
            this.mUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                        return;
                    }

                    try {
                        Club pClub = (new ParseQuery<Club>(str(R.string.parse_class_club))).get(mClub.id);
                        int connected = ((int) pClub.get(str(R.string.parse_connected)));
                        pClub.put(str(R.string.parse_connected), ++connected);
                        pClub.saveInBackground();
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                }
            });
            this.mClub.users.add(this.mUser);
            this.mClub.saveInBackground();
            Toast.makeText(mContext, this.str(R.string.toast_connected), Toast.LENGTH_SHORT).show();
            this.connectImage.setImageResource(R.drawable.clubconnect_selected);
            this.subscribe(CLUB, this.mClub.title, this.mClub.id);
        } else {
            this.mUserClubRelation.remove(mClub);
            this.mUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                        return;
                    }

                    try {
                        Club pClub = (new ParseQuery<Club>(str(R.string.parse_class_club))).get(mClub.id);
                        int connected = ((int) pClub.get(str(R.string.parse_connected)));
                        pClub.put(str(R.string.parse_connected), --connected);
                        pClub.saveInBackground();
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                }
            });
            this.mClub.users.remove(this.mUser);
            this.mClub.saveInBackground();
            Toast.makeText(mContext, this.str(R.string.toast_unconnected), Toast.LENGTH_SHORT).show();
            this.connectImage.setImageResource(R.drawable.clubconnect_unselected);
            this.unsubscribe(CLUB, this.mClub.title, this.mClub.id);
        }
    }
}
