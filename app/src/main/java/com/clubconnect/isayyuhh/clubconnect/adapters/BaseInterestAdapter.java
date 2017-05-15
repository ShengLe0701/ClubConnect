package com.clubconnect.isayyuhh.clubconnect.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.objects.SubInterest;

import java.util.List;

/**
 * Created by isayyuhh on 5/4/16.
 */
public abstract class BaseInterestAdapter extends RecyclerView.Adapter<BaseInterestAdapter.InterestViewHolder> {

    private int id;

    protected int counter;
    protected Context context;
    protected FloatingActionButton fab;
    protected List<SubInterest> interests;

    public BaseInterestAdapter(Context context, List<SubInterest> interests,
                               FloatingActionButton fab, int id) {
        this.id = id;
        this.fab = fab;
        this.context = context;
        this.interests = interests;
    }

    @Override
    public InterestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(this.id, viewGroup, false);
        return new InterestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InterestViewHolder holder, int position) {
        holder.bind(interests.get(position));
    }

    protected abstract void setEnums(InterestViewHolder holder);

    public class InterestViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout ll;
        public ImageView image;
//        public ImageView added;

        public InterestViewHolder(View v) {
            super(v);
            this.title = (TextView) v.findViewById(R.id.row_interest_name);
            this.ll = (LinearLayout) v.findViewById(R.id.row_interest_ll);
            this.image = (ImageView) v.findViewById(R.id.image);
//            this.added = (ImageView) v.findViewById(R.id.row_interest_added);
        }

        public void bind(final SubInterest category) {
            this.title.setText(category.name);
            this.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter += category.added ? -1 : 1;
                    category.added = !category.added;
                    title.setTextColor(category.added ? Color.parseColor("#1975fe")
                            : Color.BLACK);

                    if (counter >= 3) fab.show();
                    else fab.hide();
                }
            });
//            Picasso.with(context).load(category.added ? R.drawable.add_selected
//                    : R.drawable.add_unselected).into(this.added);
            setEnums(this);
        }

    }

    @Override
    public int getItemCount() {
        return interests.size();
    }
}
