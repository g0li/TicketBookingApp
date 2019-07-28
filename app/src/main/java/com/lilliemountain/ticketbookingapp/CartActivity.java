package com.lilliemountain.ticketbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lilliemountain.ticketbookingapp.adapter.TicketAdapter;
import com.lilliemountain.ticketbookingapp.model.Event;
import com.lilliemountain.ticketbookingapp.model.Ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView total;
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;
    int totalInt=0;
    String tempStringAMount;
    List<Event> eventidList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        total=findViewById(R.id.total);
        recyclerView=findViewById(R.id.recyclerView);
        ticketAdapter=new TicketAdapter(eventidList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ticketAdapter);
        SharedPrefManager.initializeInstance(this);
        FirebaseFirestore.getInstance().collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    eventidList.clear();
                    List<DocumentSnapshot> ds=task.getResult().getDocuments();
                    for (int i = 0; i < ds.size(); i++) {
                        if(SharedPrefManager.getInstance().getValue(ds.get(i).toObject(Event.class).getEventid())!=null)
                            eventidList.add(ds.get(i).toObject(Event.class));
                    }
                    for (int i = 0; i < eventidList.size(); i++) {
                         tempStringAMount=eventidList.get(i).getPrice().split(" ")[1];
                        tempStringAMount=tempStringAMount.split("\\/")[0];
                    }

                    if(tempStringAMount!=null)
                    {
                        totalInt =totalInt+ Integer.parseInt(tempStringAMount);
                        total.setText("₹ "+totalInt+"/-");
                    }
                    ticketAdapter.notifyDataSetChanged();
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventidList.size()>0)
                {
                    for (int i = 0; i < eventidList.size(); i++) {
                        final Ticket ticket=new Ticket(new Timestamp(new Date()),"thisismy@email.com",eventidList.get(i));
                        final int finalI = i;
                        FirebaseFirestore.getInstance().collection("tickets").document().set(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                SharedPrefManager.getInstance().remove(eventidList.get(finalI).getEventid());
                                Toast.makeText(CartActivity.this, "Ticket booked for "+ticket.getEvent().getName(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    onBackPressed();

                }
            }
        });
    }
}
