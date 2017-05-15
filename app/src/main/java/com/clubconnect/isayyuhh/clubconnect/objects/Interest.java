package com.clubconnect.isayyuhh.clubconnect.objects;

import android.util.Log;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.parse.PCategory;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interest extends SubInterest {
    public List<String> subcategories;

    public Interest(String object, String[] subcategories) {
        if (subcategories != null) {
            this.subcategories = new ArrayList<String>(Arrays.asList(subcategories));
        } else {
            this.subcategories = null;
        }

        this.name = object;
        this.added = false;
    }
}