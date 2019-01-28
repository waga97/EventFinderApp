package com.example.arshad.uea.Admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arshad.uea.MainActivity;
import com.example.arshad.uea.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ApprovedEvent extends AppCompatActivity {

    private Toolbar approvedeventTb;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private BottomNavigationView approvedNav;

    private ApprovedFragment approvedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_event);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        approvedeventTb = (Toolbar) findViewById(R.id.approvedevent_tb);
        setSupportActionBar(approvedeventTb);
        getSupportActionBar().setTitle("Approved Event List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // FRAGMENTS
        approvedNav = findViewById(R.id.declined_nav);
        approvedFragment = new ApprovedFragment();
        initializeFragment();
        approvedNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_view:

                        replaceFragment(approvedFragment);
                        return true;


                    default:
                        return false;


                }

            }
        });
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
                Intent backIntent = new Intent(ApprovedEvent.this, AdminHome.class);
                startActivity(backIntent);
                return true;
        }

    }
    private void  sendToMain (){

        Intent logoutintent = new Intent(ApprovedEvent.this, MainActivity.class);
        startActivity(logoutintent);
        finish();
    }

    private void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.declined_container, fragment);
        fragmentTransaction.commit();


    }

    private void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.declined_container, approvedFragment);

        fragmentTransaction.commit();

    }
}
