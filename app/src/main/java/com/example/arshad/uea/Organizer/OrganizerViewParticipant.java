package com.example.arshad.uea.Organizer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrganizerViewParticipant extends AppCompatActivity {

    private Toolbar orgeventpartToolbar;

    private RecyclerView participant_listview;
    private OrganizerParticipantRecyclerAdapter organizerParticipantRecyclerAdapter;
    private List<Participant> participantList;

    private FirebaseFirestore firebaseFirestore;

    private String event_id;

    Participant retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_view_participant);

        firebaseFirestore = FirebaseFirestore.getInstance();
        event_id = getIntent().getStringExtra("event_id");

        firebaseFirestore.collection("Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String eventName = task.getResult().getString("name");

                orgeventpartToolbar = (Toolbar) findViewById(R.id.org_event_part_toolbar);
                setSupportActionBar(orgeventpartToolbar);
                getSupportActionBar().setTitle(eventName);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        });


        participant_listview = findViewById(R.id.participant_list);

        //RecyclerView Firebase List
        participantList = new ArrayList<>();
        organizerParticipantRecyclerAdapter = new OrganizerParticipantRecyclerAdapter(participantList);
        participant_listview.setHasFixedSize(true);
        participant_listview.setLayoutManager(new LinearLayoutManager(this));
        participant_listview.setAdapter(organizerParticipantRecyclerAdapter);













    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.organizer_create_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            default:
                Intent backIntent = new Intent(OrganizerViewParticipant.this, OrganizerViewEvent.class);
                startActivity(backIntent);
                return true;
        }

    }


}
