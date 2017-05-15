package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.clubconnect.isayyuhh.clubconnect.callbacks.CallbackActivity;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Base activity
 */
public abstract class BaseActivity extends AppCompatActivity implements CallbackActivity {

    protected static final String CLUB = "club_";
    protected static final String EVENT = "event_";
    private final static List<String> MONTHS = Arrays.asList("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");
    private static final List<String> SPECIAL_CHARS = Arrays.asList(" ", "'", "`", "~", "1", "2",
            "3", "4", "5", "6", "7", "8", "9", "0", "-", "_", "+", "!", "@", "#", "$", "%", "^",
            "&", "*", "(", ")", "[", "]", "{", "}", "\\", "|", ";", ":", "\"", "<", ">", ",", ".",
            "/", "?", "¡");

    protected Context mContext;
    protected ParseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mUser = ParseUser.getCurrentUser();
        this.mContext = getApplicationContext();
    }

    protected String str(int res) {
        return this.getResources().getString(res);
    }

    protected String[] arr(int res) {
        return this.getResources().getStringArray(res);
    }

    protected void subscribe(String type, String name, String id) {
        String title = type + this.removeSpecialChars(name) + "_" + id;
        ParsePush.subscribeInBackground(title, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) e.printStackTrace();
            }
        });
    }

    protected void unsubscribe(String type, String name, String id) {
        String title = type + this.removeSpecialChars(name) + "_" + id;
        ParsePush.unsubscribeInBackground(title, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) e.printStackTrace();
            }
        });
    }

    public void subscribeConnections() {
        ParseRelation<Club> relation = mUser.getRelation("savedClubs");
        ParseQuery<Club> query = relation.getQuery();
        query.findInBackground(new FindCallback<Club>() {
            @Override
            public void done(List<Club> list, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : list) {
                        Club club = (Club) parseObject;
                        club.set();
                        subscribe(CLUB, club.title, club.id);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void subscribeGoings() {
        ParseRelation<Event> relation = mUser.getRelation("savedEvents");
        ParseQuery<Event> query = relation.getQuery();
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : list) {
                        Event event = (Event) parseObject;
                        event.set();
                        subscribe(EVENT, event.title, event.id);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    protected String removeSpecialChars(String string) {
        String newString = string;
        int i = 0;
        for (String specialChar : SPECIAL_CHARS) {
            System.out.println(i+" : "+specialChar+ " "+newString);
            newString = newString.replace(specialChar, "");
            i++;
        }
        return newString;
    }

    @Override
    public String today() {
        Date date = new Date();
        String[] toks = date.toString().split(" ");
        return toks[1] + " " + toks[2] + ", " + toks[5];
    }

    @Override
    public int compareDates(String lhs, String rhs) {
        String[] ltok = lhs.split(" ");
        String[] rtok = rhs.split(" ");
        ltok[1] = ltok[1].replace(",", "");
        rtok[1] = rtok[1].replace(",", "");

        if (Integer.parseInt(ltok[2]) < Integer.parseInt(rtok[2])) return -1;
        else if (Integer.parseInt(ltok[2]) > Integer.parseInt(rtok[2])) return 1;

        Integer lmonth = 0, rmonth = 0;
        for (int i = 0; i < MONTHS.size(); i++) {
            String month = MONTHS.get(i);
            if (ltok[0].equals(month)) lmonth = i;
            if (rtok[0].equals(month)) rmonth = i;
        }

        if (lmonth < rmonth) return -1;
        else if (lmonth > rmonth) return 1;

        if (Integer.valueOf(ltok[1]) < Integer.valueOf(rtok[1])) return -1;
        else if (Integer.valueOf(ltok[1]) > Integer.valueOf(rtok[1])) return 1;

        return 0;
    }

    @Override
    public Events sort(Events toSort) {
        String today = this.today();
        List<Event> copy = new ArrayList<>(toSort.list());

        for (int i = 0; i < copy.size(); i++) {
            if (compareDates(copy.get(i).date, today) < 0) {
                copy.remove(i);
            }
        }

        Collections.sort(copy, new CompareDates());
        return new Events(copy);
    }

    public class CompareDates implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            return compareDates(lhs.date, rhs.date);
        }
    }
}
