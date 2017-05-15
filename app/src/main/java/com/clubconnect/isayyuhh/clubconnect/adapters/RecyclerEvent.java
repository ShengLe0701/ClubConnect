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
import com.clubconnect.isayyuhh.clubconnect.activities.DetailEvent;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.clubconnect.isayyuhh.clubconnect.objects.Events;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerEvent extends RecyclerView.Adapter<RecyclerEvent.ClassEventViewHolder> {
    private ArrayList<Event> itemArr;
    private Context context;
    private Activity activity;

    public RecyclerEvent(ArrayList<Event> itemArr, Context context, Activity activity) {
        this.itemArr = itemArr;
        this.context = context;
        this.activity = activity;
    }

    public RecyclerEvent(Events itemArr, Context context, Activity activity) {
        this.itemArr = new ArrayList<>(itemArr.list());
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ClassEventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_club, viewGroup, false);
        return new ClassEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassEventViewHolder holder, final int i) {
        final Event event = itemArr.get(i);
        holder.nameTV.setText(event.title);
        holder.locationTV.setText(event.location);
        holder.dateTV.setText(event.fullDate);
        Picasso.with(context).load(event.imageURL).resize(600, 240).centerCrop().into(holder.imageIV);
        holder.rowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailEvent.class);
                intent.putExtra("ID", itemArr.get(i).id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArr.size();
    }

    public static class ClassEventViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV;
        private TextView dateTV;
        private TextView locationTV;
        private RelativeLayout rowLL;
        private ImageView imageIV;

        public ClassEventViewHolder(View v) {
            super(v);
            this.nameTV = (TextView) v.findViewById(R.id.row_club_name);
            this.dateTV = (TextView) v.findViewById(R.id.row_club_date);
            this.locationTV = (TextView) v.findViewById(R.id.row_club_location);
            this.rowLL = (RelativeLayout) v.findViewById(R.id.row_club_ll);
            this.imageIV = (ImageView) v.findViewById(R.id.row_club_image);
        }
    }
}
