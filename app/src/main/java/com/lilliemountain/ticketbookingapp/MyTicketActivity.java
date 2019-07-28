package com.lilliemountain.ticketbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lilliemountain.ticketbookingapp.adapter.MyTicketsAdapter;
import com.lilliemountain.ticketbookingapp.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class MyTicketActivity extends AppCompatActivity {
    List<Ticket> tickets=new ArrayList<>();
    RecyclerView recyclerView;
    MyTicketsAdapter myTicketsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        recyclerView=findViewById(R.id.recyclerView);
        myTicketsAdapter=new MyTicketsAdapter(tickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myTicketsAdapter);
        FirebaseFirestore.getInstance().collection("tickets").whereEqualTo("email","thisismy@email.com").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> ds=task.getResult().getDocuments();
                    for (int i = 0; i < ds.size(); i++) {
                        tickets.add(ds.get(i).toObject(Ticket.class));
                    }
                    myTicketsAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
