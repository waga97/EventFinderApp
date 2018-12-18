package com.example.arshad.uea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

public class OrganizerEventParticipant extends AppCompatActivity {

    private Toolbar orgeventpartToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_event_participant);

        orgeventpartToolbar = findViewById(R.id.org_event_part_toolbar);
        setSupportActionBar(orgeventpartToolbar);
        getSupportActionBar().setTitle("View Participant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                Intent backIntent = new Intent(OrganizerEventParticipant.this, OrganizerView.class);
                startActivity(backIntent);
                return true;
        }

    }


}
