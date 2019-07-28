package com.lilliemountain.ticketbookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.lilliemountain.ticketbookingapp.model.Event;

public class EventInfoActivity extends AppCompatActivity {
    ImageView photo;
    TextView eventname,date,amount,time,description;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.activity_event_info);
        event=getIntent().getParcelableExtra("event");
        photo=findViewById(R.id.photo);
        eventname=findViewById(R.id.eventname);
        date=findViewById(R.id.date);
        amount=findViewById(R.id.amount);
        time=findViewById(R.id.time);
        description=findViewById(R.id.description);

        eventname.setText(event.getName());
        date.setText(event.getDate());
        amount.setText(event.getPrice());
        time.setText(event.getTime());
        description.setText(event.getDescription());
        switch (event.getEventid())
        {
            case "event_001":
                photo.setImageDrawable(getDrawable(R.drawable.ic_music));
                break;
            case "event_002":
                photo.setImageDrawable(getDrawable(R.drawable.ic_art));
                break;
            case "event_003":
                photo.setImageDrawable(getDrawable(R.drawable.ic_coaching));
                break;
        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.initializeInstance(EventInfoActivity.this);
                SharedPrefManager.getInstance().setValue(event.getEventid(),event.getEventid());
                Snackbar.make(v,event.getName()+" added to cart.",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
