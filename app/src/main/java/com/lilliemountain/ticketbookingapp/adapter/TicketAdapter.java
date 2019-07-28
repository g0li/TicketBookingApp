package com.lilliemountain.ticketbookingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lilliemountain.ticketbookingapp.R;
import com.lilliemountain.ticketbookingapp.model.Event;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketHolder> {
    List<Event> events;

    public TicketAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new TicketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        holder.name.setText(events.get(position).getName());
        holder.date.setText(events.get(position).getDate());
        holder.amount.setText(events.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class TicketHolder extends RecyclerView.ViewHolder {
        TextView name,date,amount;
        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.date);
            amount=itemView.findViewById(R.id.amount);
        }
    }
}
