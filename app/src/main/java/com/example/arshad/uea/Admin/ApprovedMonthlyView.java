package com.example.arshad.uea.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.arshad.uea.MyEvent;
import com.example.arshad.uea.Organizer.OrganizerCreate;
import com.example.arshad.uea.Organizer.OrganizerHome;
import com.example.arshad.uea.Organizer.OrganizerParticipantRecyclerAdapter;
import com.example.arshad.uea.Organizer.OrganizerViewParticipant;
import com.example.arshad.uea.Organizer.Participants;
import com.example.arshad.uea.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ApprovedMonthlyView extends AppCompatActivity {

    private Toolbar adminmonthlyTb;

    private RecyclerView event_list;
    private ApprovedMonthlyRecyclerAdapter approvedMonthlyRecyclerAdapter;
    private List<MyEvent> myEventList;

    private TextView monthTv;

    private FirebaseFirestore firebaseFirestore;

    private String month_id;
    private String eventName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_monthly_view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        month_id = getIntent().getStringExtra("month_id");

        adminmonthlyTb = (Toolbar) findViewById(R.id.admin_monthly_tb);
        setSupportActionBar(adminmonthlyTb);
        getSupportActionBar().setTitle("Monthly View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        event_list = findViewById(R.id.approved_monthly_list_view);
        myEventList = new ArrayList<>();
        approvedMonthlyRecyclerAdapter = new ApprovedMonthlyRecyclerAdapter(myEventList);
        event_list.setHasFixedSize(true);
        event_list.setLayoutManager(new LinearLayoutManager(this));
        event_list.setAdapter(approvedMonthlyRecyclerAdapter);

        firebaseFirestore.collection("Approved_By_Month/" + month_id + "/Month")
                .addSnapshotListener(ApprovedMonthlyView.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (!documentSnapshots.isEmpty()) {

                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String commentId = doc.getDocument().getId();
                                    MyEvent event = doc.getDocument().toObject(MyEvent.class);
                                    myEventList.add(event);
                                    approvedMonthlyRecyclerAdapter.notifyDataSetChanged();


                                }
                            }

                        }

                    }
                });


        monthTv = findViewById(R.id.month_tv);

        if (month_id.equals("1")){
            monthTv.setText("January");
        }else if (month_id.equals("2")){
            monthTv.setText("February");
        }else if (month_id.equals("3")){
            monthTv.setText("March");
        }else if (month_id.equals("4")){
            monthTv.setText("April");
        }else if (month_id.equals("5")){
            monthTv.setText("May");
        }else if (month_id.equals("6")){
            monthTv.setText("June");
        }else if (month_id.equals("7")){
            monthTv.setText("July");
        }else if (month_id.equals("8")){
            monthTv.setText("August");
        }else if (month_id.equals("9")){
            monthTv.setText("September");
        }else if (month_id.equals("10")){
            monthTv.setText("October");
        }else if (month_id.equals("11")){
            monthTv.setText("November");
        }else{
            monthTv.setText("December");
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
                Intent backIntent = new Intent(ApprovedMonthlyView.this, ApprovedByMonth.class);
                startActivity(backIntent);
                return true;
        }

    }
}
