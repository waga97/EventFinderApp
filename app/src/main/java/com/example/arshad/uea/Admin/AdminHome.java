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
import android.widget.Toast;

import com.example.arshad.uea.MainActivity;
import com.example.arshad.uea.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHome extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private Toolbar adminTb;
    private BottomNavigationView adminNav;

    private PendingFragment approveFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid();





        adminTb = (Toolbar) findViewById(R.id.admin_tb);
        setSupportActionBar(adminTb);
        getSupportActionBar().setTitle("Admin");


        // FRAGMENTS
        adminNav = findViewById(R.id.declined_nav);
        approveFragment = new PendingFragment();
        initializeFragment();
        adminNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_view:

                        replaceFragment(approveFragment);
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

        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.logout_action:
                logout();
                Toast.makeText(AdminHome.this, "Successfully sign out" , Toast.LENGTH_LONG).show();
                return true;

            case R.id.approved_action:
                sendToApproved();
                return true;

            case R.id.declined_action:
                sendToDeclined();
                return true;

            case R.id.dashboard_action:
                sendToDashboard();
                return true;

            default:
                return false;

        }

    }

    private void logout(){
        firebaseAuth.signOut();
        sendToMain();
    }
    private void  sendToMain (){
        Intent logoutintent = new Intent(AdminHome.this, MainActivity.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToApproved (){
        Intent logoutintent = new Intent(AdminHome.this, ApprovedEvent.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToDeclined (){
        Intent logoutintent = new Intent(AdminHome.this, DeclinedEvent.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToDashboard (){
        Intent logoutintent = new Intent(AdminHome.this, AdminDashboard.class);
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

        fragmentTransaction.add(R.id.declined_container, approveFragment);

        fragmentTransaction.commit();

    }
}
