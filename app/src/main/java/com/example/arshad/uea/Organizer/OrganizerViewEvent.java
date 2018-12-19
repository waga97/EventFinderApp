package com.example.arshad.uea.Organizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arshad.uea.MainActivity;
import com.example.arshad.uea.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class OrganizerViewEvent extends AppCompatActivity {

    private Toolbar organizerviewToolbar;

    private OrganizerViewEventFragment organizerViewEventFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_view_event);

        organizerviewToolbar = (Toolbar) findViewById(R.id.organizerview_toolbar);
        setSupportActionBar(organizerviewToolbar);
        getSupportActionBar().setTitle("View Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       organizerViewEventFragment = new OrganizerViewEventFragment();
       initializeFragment();


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {

            sendToMain();

        }

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
                Intent backIntent = new Intent(OrganizerViewEvent.this, OrganizerHome.class);
                startActivity(backIntent);
                return true;
        }

    }
    private void  sendToMain (){

        Intent logoutintent = new Intent(OrganizerViewEvent.this, MainActivity.class);
        startActivity(logoutintent);
        finish();
    }



    private void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.main_container, organizerViewEventFragment);

        fragmentTransaction.commit();

    }


}
