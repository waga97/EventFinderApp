package com.example.arshad.uea.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.arshad.uea.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class AdminDashboard extends AppCompatActivity {

    private android.support.v7.widget.Toolbar adminDashboardTb;

    private TextView pendingCount;
    private TextView approveCount;
    private TextView declineCount;


    private ImageView pendingBtn;
    private ImageView approvedBtn;
    private ImageView declinedBtn;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        adminDashboardTb = findViewById(R.id.admin_dashboard_tb);
        setSupportActionBar(adminDashboardTb);
        getSupportActionBar().setTitle("Admin Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseFirestore.collection("Pending_Events/").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                pendingCount = findViewById(R.id.pending_count);

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    pendingCount.setText(String.valueOf(count));

                } else if(documentSnapshots.isEmpty()){

                    pendingCount.setText("0");

                }

            }
        });

        pendingBtn = findViewById(R.id.pending_btn);
        pendingCount = findViewById(R.id.pending_count);

        pendingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToPending();

            }
        });

        pendingCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToPending();

            }
        });

        firebaseFirestore.collection("Approved_Events/").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                approveCount = findViewById(R.id.approve_count);

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    approveCount.setText(String.valueOf(count));

                } else if(documentSnapshots.isEmpty()) {

                    approveCount.setText("0");

                }

            }
        });

        approvedBtn = findViewById(R.id.approve_btn);
        approveCount = findViewById(R.id.approve_count);

        approvedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToApproved();

            }
        });

        approveCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToApproved();

            }
        });

        firebaseFirestore.collection("Declined_Events/").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                declineCount = findViewById(R.id.decline_count);

                if(!documentSnapshots.isEmpty()){

                    int count = documentSnapshots.size();
                    declineCount.setText(String.valueOf(count));

                } else if(documentSnapshots.isEmpty()) {

                    declineCount.setText("0");

                }

            }
        });

        declinedBtn = findViewById(R.id.decline_btn);
        declineCount = findViewById(R.id.decline_count);

        declinedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToDeclined();

            }
        });

        declineCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToDeclined();

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
                Intent backIntent = new Intent(AdminDashboard.this, AdminHome.class);
                startActivity(backIntent);
                return true;
        }

    }

    private void  sendToApproved (){
        Intent logoutintent = new Intent(AdminDashboard.this, ApprovedByMonth.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToDeclined (){
        Intent logoutintent = new Intent(AdminDashboard.this, DeclinedEvent.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToPending (){
        Intent logoutintent = new Intent(AdminDashboard.this, AdminHome.class);
        startActivity(logoutintent);
        finish();
    }
}
