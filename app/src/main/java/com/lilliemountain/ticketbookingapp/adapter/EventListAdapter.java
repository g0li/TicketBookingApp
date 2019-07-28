package com.lilliemountain.ticketbookingapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.lilliemountain.ticketbookingapp.EventInfoActivity;
import com.lilliemountain.ticketbookingapp.R;
import com.lilliemountain.ticketbookingapp.model.Event;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListHolder> {
    List<Event> events;

    public EventListAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        return new EventListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListHolder holder, int position) {
        holder.eventname.setText(events.get(position).getName());
        holder.date.setText(events.get(position).getDate());
        holder.amount.setText(events.get(position).getPrice());
        holder.event=events.get(position);
        switch (events.get(position).getEventid())
        {
            case "event_001":
                holder.photo.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_music));
                break;
            case "event_002":
                holder.photo.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_art));
                break;
            case "event_003":
                holder.photo.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_coaching));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventListHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView eventname,date,amount;
        Event event;
        public EventListHolder(@NonNull final View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.photo);
            eventname=itemView.findViewById(R.id.eventname);
            date=itemView.findViewById(R.id.date);
            amount=itemView.findViewById(R.id.amount);
            itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pair<View, String> p1 = Pair.create((View)photo, "photo");
                    Pair<View, String> p2 = Pair.create((View)eventname, "eventname");
                    Pair<View, String> p3 = Pair.create((View)date, "date");
                    Pair<View, String> p4 = Pair.create((View)amount, "amount");
                    Pair<View, String> p5 = Pair.create((View)itemView.findViewById(R.id.button), "button");
                    Activity activity=(Activity)v.getContext();
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(activity, p1, p2, p3,p4,p5);
                    v.getContext().startActivity(new Intent(v.getContext(), EventInfoActivity.class).putExtra("event",event),options.toBundle());
                }
            });
        }
    }
}
