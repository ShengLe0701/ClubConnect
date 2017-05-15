package com.clubconnect.isayyuhh.clubconnect.activities;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.R;

import com.google.firebase.auth.FirebaseUser;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailEvent extends BaseDetailActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.status_bar)
    FrameLayout statusBar;
    @Bind(R.id.event_image_iv)
    ImageView image;
    @Bind(R.id.event_name_tv)
    TextView title;
    @Bind(R.id.event_location_tv)
    TextView location;
    @Bind(R.id.event_date_tv)
    TextView date;
    @Bind(R.id.event_summary_tv)
    TextView summary;


    @Bind(R.id.contact_button)
    LinearLayout contactButton;
    @Bind(R.id.going_button)
    LinearLayout goingButton;
    @Bind(R.id.share_button)
    LinearLayout shareButton;

    @Bind(R.id.going_image)
    ImageView goingImage;

    private Event event;
    private ParseRelation<Event> relation;
    private ParseQuery eventQuery;

//    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

//        mDatabase = FirebaseDatabase.getInstance().getReference();

        this.setActionBar(toolbar, statusBar, "Event");
        this.initialize();
    }

    private void initialize() {
        this.relation = ParseUser.getCurrentUser().getRelation("savedEvents");
        this.eventQuery = this.relation.getQuery();
        this.getEvent();
    }

    private void getEvent() {
        Intent intent = getIntent();
        final String id = intent.getStringExtra("ID");
        ParseQuery<Event> query = ParseQuery.getQuery("Event");
//        FirebaseDatabase.getInstance().getReference().child("events").child(id)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        if(dataSnapshot.getValue(Event.class) != null){
//                            event = dataSnapshot.getValue(Event.class);
//                            Picasso.with(mContext).load(event.imageURL).into(image);
//                            title.setText(event.title);
//                            location.setText(event.location);
//                            summary.setText(event.summary);
//                            date.setText(event.fullDate);
//
//                            mDatabase.updateChildren();
//                        } else {
//                            Toast.makeText(mContext, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w("DetailEvent", "event:onCancelled", databaseError.toException());
//                        // [START_EXCLUDE]
//                        Toast.makeText(DetailEvent.this, "Failed to load post.",
//                                Toast.LENGTH_SHORT).show();
//                        // [END_EXCLUDE]
//                    }
//                });


        query.getInBackground(id, new GetCallback<Event>() {
            public void done(Event pEvent, ParseException e) {
                if (e == null) {
                    event = pEvent;
                    event.set();

                    Picasso.with(mContext).load(event.imageURL).into(image);
                    title.setText(event.title);
                    location.setText(event.location);
                    summary.setText(event.summary);
                    date.setText(event.fullDate);

                    setButtons();
                } else {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean find() {
        try {
            eventQuery.whereEqualTo("objectId", event.getObjectId());
            return eventQuery.count() > 0;
        } catch (ParseException e1) {
            return false;
        }
    }

    private void setButtons() {
        if (this.event == null) return;
        if (find()) {
            goingImage.setImageResource(R.drawable.going_selected);
        }
        this.contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContactButton();
            }
        });
        this.goingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {setGoingButton();
            }
        });
        this.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShareButton();
            }
        });
    }

    private void setContactButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailEvent.this);
        builder.setTitle(getResources().getString(R.string.dialog_contacttitle));
        builder.setItems(getResources().getStringArray(R.array.dialog_contactoptions),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + event.phone));
                                startActivity(intent);
                                break;
                            }
                            case 1: {
                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setType("*/*");
                                intent.setData(Uri.parse("mailto:" + event.email));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                try {
                                    startActivity(Intent.createChooser(intent,
                                            "Email using:"));
                                } catch (ActivityNotFoundException anfe) {
                                    Toast.makeText(mContext, "No email apps found",
                                            Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            case 2: {
                                ClipboardManager clipboard =
                                        (ClipboardManager) getSystemService(
                                                Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("phone", event.phone);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(mContext, "Phone number copied to clipboard",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 3: {
                                ClipboardManager clipboard =
                                        (ClipboardManager) getSystemService(
                                                Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("email", event.email);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(mContext, "Email address copied to clipboard",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            }
                            default:
                                Log.e("ERROR", "Invalid dialog choice");
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setGoingButton() {
        if (!find()) {
            relation.add(event);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseQuery<Event> query = new ParseQuery<>("Event");
                        try {
                            Event pEvent = query.get(event.id);
                            int score = ((int) pEvent.get("score"));
                            pEvent.put("score", ++score);
                            pEvent.saveInBackground();
                        } catch (ParseException pe) {
                            pe.printStackTrace();
                        }
                    }
                    else e.printStackTrace();
                }
            });
            Toast.makeText(mContext, "Going", Toast.LENGTH_SHORT).show();
            goingImage.setImageResource(R.drawable.going_selected);
            subscribe();
        } else {
            relation.remove(event);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        try {
                            ParseQuery<Event> query = new ParseQuery<>("Event");
                            Event pEvent = query.get(event.id);
                            int score = ((int) pEvent.get("score"));
                            pEvent.put("score", --score);
                            pEvent.saveInBackground();
                        } catch (ParseException pe) {
                            pe.printStackTrace();
                        }
                    }
                    else e.printStackTrace();
                }
            });
            Toast.makeText(mContext, "Not going", Toast.LENGTH_SHORT).show();
            goingImage.setImageResource(R.drawable.going_unselected);
            unsubscribe();
        }
    }

    private void setShareButton() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out this event "
                + event.title + " at " + event.fullDate + "!");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(
                R.string.share_via)));
    }

    private void subscribe() {
        String title = event.title;
        title = title.replace(" ", "");
        title = title.replace("'", "");
        title = title.replace("`", "");
        title = title.replace("~", "");
        title = title.replace("1", "");
        title = title.replace("2", "");
        title = title.replace("3", "");
        title = title.replace("4", "");
        title = title.replace("5", "");
        title = title.replace("6", "");
        title = title.replace("7", "");
        title = title.replace("8", "");
        title = title.replace("9", "");
        title = title.replace("0", "");
        title = title.replace("-", "");
        title = title.replace("_", "");
        title = title.replace("+", "");
        title = title.replace("!", "");
        title = title.replace("@", "");
        title = title.replace("#", "");
        title = title.replace("$", "");
        title = title.replace("%", "");
        title = title.replace("^", "");
        title = title.replace("&", "");
        title = title.replace("*", "");
        title = title.replace("(", "");
        title = title.replace(")", "");
        title = title.replace("[", "");
        title = title.replace("]", "");
        title = title.replace("{", "");
        title = title.replace("}", "");
        title = title.replace("\\", "");
        title = title.replace("|", "");
        title = title.replace(";", "");
        title = title.replace(":", "");
        title = title.replace("\"", "");
        title = title.replace("<", "");
        title = title.replace(">", "");
        title = title.replace(",", "");
        title = title.replace(".", "");
        title = title.replace("/", "");
        title = title.replace("?", "");
        title = title.replace("¡", "");
        ParsePush.subscribeInBackground("event_" + title + "_" + event.id, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("Error", e.toString());
                }
            }
        });
    }

    private void unsubscribe() {
        String title = event.title;
        title = title.replace(" ", "");
        title = title.replace("'", "");
        title = title.replace("`", "");
        title = title.replace("~", "");
        title = title.replace("1", "");
        title = title.replace("2", "");
        title = title.replace("3", "");
        title = title.replace("4", "");
        title = title.replace("5", "");
        title = title.replace("6", "");
        title = title.replace("7", "");
        title = title.replace("8", "");
        title = title.replace("9", "");
        title = title.replace("0", "");
        title = title.replace("-", "");
        title = title.replace("_", "");
        title = title.replace("+", "");
        title = title.replace("!", "");
        title = title.replace("@", "");
        title = title.replace("#", "");
        title = title.replace("$", "");
        title = title.replace("%", "");
        title = title.replace("^", "");
        title = title.replace("&", "");
        title = title.replace("*", "");
        title = title.replace("(", "");
        title = title.replace(")", "");
        title = title.replace("[", "");
        title = title.replace("]", "");
        title = title.replace("{", "");
        title = title.replace("}", "");
        title = title.replace("\\", "");
        title = title.replace("|", "");
        title = title.replace(";", "");
        title = title.replace(":", "");
        title = title.replace("\"", "");
        title = title.replace("<", "");
        title = title.replace(">", "");
        title = title.replace(",", "");
        title = title.replace(".", "");
        title = title.replace("/", "");
        title = title.replace("?", "");
        title = title.replace("¡", "");
        ParsePush.unsubscribeInBackground("event_" + title + "_" + event.id, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("Error", e.toString());
                }
            }
        });
    }
}
