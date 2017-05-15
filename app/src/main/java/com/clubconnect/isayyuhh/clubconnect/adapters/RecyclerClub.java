package com.clubconnect.isayyuhh.clubconnect.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.activities.DetailClub;
import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Clubs;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerClub extends RecyclerView.Adapter<RecyclerClub.ClassClubViewHolder> {
    private List<Club> itemArr;
    private Context context;
    private Activity activity;

    public RecyclerClub(List<Club> itemArr, Context context, Activity activity) {
        this.itemArr = itemArr;
        this.context = context;
        this.activity = activity;
    }

    public RecyclerClub(Clubs itemArr, Context context, Activity activity) {
        this.itemArr = itemArr.list();
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ClassClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_club, viewGroup, false);
        return new ClassClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassClubViewHolder holder, final int i) {
        final Club club = itemArr.get(i);
        holder.nameTV.setText(club.title);
        holder.locationTV.setText(club.location);
        holder.dateTV.setText(club.day + " @ " + club.time);
        Picasso.with(context).load(club.imageURL).resize(600, 240).centerCrop().into(holder.imageIV);
        holder.rowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailClub.class);
                intent.putExtra("ID", itemArr.get(i).id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        holder.rowLL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArr.size();
    }

    public static class ClassClubViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView dateTV;
        TextView locationTV;
        RelativeLayout rowLL;
        ImageView imageIV;

        public ClassClubViewHolder(View v) {
            super(v);
            nameTV = (TextView) v.findViewById(R.id.row_club_name);
            dateTV = (TextView) v.findViewById(R.id.row_club_date);
            locationTV = (TextView) v.findViewById(R.id.row_club_location);
            rowLL = (RelativeLayout) v.findViewById(R.id.row_club_ll);
            imageIV = (ImageView) v.findViewById(R.id.row_club_image);
        }
    }
}
