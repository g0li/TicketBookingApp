package com.lilliemountain.ticketbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lilliemountain.ticketbookingapp.adapter.EventListAdapter;
import com.lilliemountain.ticketbookingapp.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EventListAdapter eventListAdapter;
    List<Event> events=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_event_list);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventListAdapter=new EventListAdapter(events);
        recyclerView.setAdapter(eventListAdapter);
        FirebaseFirestore.getInstance().collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ProgressDialog dialog=new ProgressDialog(EventListActivity.this);
                dialog.setTitle("Loading events...");
                dialog.setCancelable(false);
                dialog.show();
                if(task.isSuccessful())
                {
                    dialog.dismiss();
                    List<DocumentSnapshot> ds=task.getResult().getDocuments();
                    for (int i = 0; i < ds.size(); i++) {
                        events.add(ds.get(i).toObject(Event.class));
                    }
                    eventListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.cart:
                startActivity(new Intent(EventListActivity.this,CartActivity.class));
                break;
            case R.id.ticket:
                startActivity(new Intent(EventListActivity.this,MyTicketActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
