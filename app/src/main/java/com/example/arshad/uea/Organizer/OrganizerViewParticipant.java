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

import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrganizerViewParticipant extends AppCompatActivity {

    private Toolbar orgeventpartToolbar;

    private RecyclerView participant_list;
    private OrganizerParticipantRecyclerAdapter organizerParticipantRecyclerAdapter;
    private List<Participants> participantsList;

    private FirebaseFirestore firebaseFirestore;

    private String event_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_view_participant);

        firebaseFirestore = FirebaseFirestore.getInstance();
        event_id = getIntent().getStringExtra("event_id");

        firebaseFirestore.collection("Approved_Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String eventName = task.getResult().getString("name");

                orgeventpartToolbar = (Toolbar) findViewById(R.id.org_event_part_toolbar);
                setSupportActionBar(orgeventpartToolbar);
                getSupportActionBar().setTitle(eventName);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        });


        participant_list = findViewById(R.id.my_event_list_view);

        //RecyclerView Firebase List
        participantsList = new ArrayList<>();
        organizerParticipantRecyclerAdapter = new OrganizerParticipantRecyclerAdapter(participantsList);
        participant_list.setHasFixedSize(true);
        participant_list.setLayoutManager(new LinearLayoutManager(this));
        participant_list.setAdapter(organizerParticipantRecyclerAdapter);


        firebaseFirestore.collection("Approved_Events/" + event_id + "/Participant")
                .addSnapshotListener(OrganizerViewParticipant.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (!documentSnapshots.isEmpty()) {

                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String commentId = doc.getDocument().getId();
                                    Participants participants = doc.getDocument().toObject(Participants.class);
                                    participantsList.add(participants);
                                    organizerParticipantRecyclerAdapter.notifyDataSetChanged();


                                }
                            }

                        }

                    }
                });



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
