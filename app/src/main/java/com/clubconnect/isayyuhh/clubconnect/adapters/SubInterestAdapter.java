package com.clubconnect.isayyuhh.clubconnect.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.enums.SubInterestEnum;
import com.clubconnect.isayyuhh.clubconnect.objects.SubInterest;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class SubInterestAdapter extends BaseInterestAdapter {

    private static final List<SubInterestEnum> SUBINTERESTS = Arrays.asList(SubInterestEnum.values());
    private static final int WIDTH = 480, HEIGHT = 480;

    public SubInterestAdapter(Context context, List<SubInterest> subcategories, FloatingActionButton fab) {
        super(context, subcategories, fab, R.layout.row_sub_interest);
    }

    @Override
    protected void setEnums(final InterestViewHolder holder) {
        for (SubInterestEnum subinterest : SUBINTERESTS) {
            if (!subinterest.name.equals(holder.title.getText().toString())) continue;
            Picasso.with(context).load(subinterest.res).resize(WIDTH, HEIGHT).centerCrop().into(holder.image);
            break;
        }
    }
}
