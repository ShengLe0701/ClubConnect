package com.clubconnect.isayyuhh.clubconnect.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;

import com.clubconnect.isayyuhh.clubconnect.enums.InterestEnum;
import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.objects.SubInterest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterestAdapter extends BaseInterestAdapter {

    private static final List<InterestEnum> INTERESTS = Arrays.asList(InterestEnum.values());
    private static final int WIDTH = 100, HEIGHT = 100;

    public InterestAdapter(Context context, ArrayList<SubInterest> categories, FloatingActionButton fab) {
        super(context, categories, fab, R.layout.row_interest);
    }

    @Override
    protected void setEnums(final InterestViewHolder holder) {
        for (InterestEnum interest : INTERESTS) {
            if (!interest.name.equals(holder.title.getText().toString())) continue;
            Picasso.with(context).load(interest.res).resize(WIDTH, HEIGHT).centerCrop().into(holder.image);
            break;
        }
    }
}