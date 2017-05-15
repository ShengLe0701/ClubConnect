package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.clubconnect.isayyuhh.clubconnect.callbacks.CallbackActivity;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.ClubInfo;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by joshuapena on 7/29/16.
 */

public abstract class BaseClubActivity extends AppCompatActivity implements CallbackActivity {
    private final static List<String> MONTHS = Arrays.asList("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");

    protected Context mContext;
    protected ClubInfo mClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
    }

    @Override
    public String today() {
        Date date = new Date();
        String[] toks = date.toString().split(" ");
        return toks[1] + " " + toks[2] + ", " + toks[5];
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

        Collections.sort(copy, new BaseClubActivity.CompareDates());
        return new Events(copy);
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

    public class CompareDates implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            return compareDates(lhs.date, rhs.date);
        }
    }

}
