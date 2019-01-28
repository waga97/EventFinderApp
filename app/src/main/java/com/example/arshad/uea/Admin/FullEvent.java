package com.example.arshad.uea.Admin;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FullEvent extends AppCompatActivity {

    private Toolbar fulleventTb;

    private ImageView eventImage;
    private EditText eventName;
    private EditText eventDate;
    private EditText eventTime;
    private EditText eventVenue;
    private EditText eventDesc;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private String event_id;
    private String current_user_id;

    private Uri postImageUri  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_event);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();
        event_id = getIntent().getStringExtra("event_id");

        firebaseFirestore.collection("Pending_Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String eventName = task.getResult().getString("name");


                fulleventTb = (Toolbar) findViewById(R.id.fullevent_tb);
                setSupportActionBar(fulleventTb);
                getSupportActionBar().setTitle(eventName);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            }
        });

        eventName = findViewById(R.id.event_name_et);
        eventDate = findViewById(R.id.event_date_et);
        eventTime = findViewById(R.id.event_time_et);
        eventVenue = findViewById(R.id.event_venue_et);
        eventDesc = findViewById(R.id.event_desc_et);
        eventImage = findViewById(R.id.event_image);

        firebaseFirestore.collection("Pending_Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        String name = task.getResult().getString("name");
                        String date = task.getResult().getString("date");
                        String time = task.getResult().getString("time");
                        String venue = task.getResult().getString("venue");
                        String desc = task.getResult().getString("desc");
                        String image = task.getResult().getString("image_url");

                        eventName.setText(name);
                        eventDate.setText(date);
                        eventTime.setText(time);
                        eventVenue.setText(venue);
                        eventDesc.setText(desc);

                        postImageUri = Uri.parse(image);
                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.image_placeholder);
                        Glide.with(FullEvent.this).setDefaultRequestOptions(placeholderRequest).load(image).into(eventImage);
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(FullEvent.this, "Error : " + error, Toast.LENGTH_LONG).show();
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
                Intent backIntent = new Intent(FullEvent.this, AdminHome.class);
                startActivity(backIntent);
                return true;
        }
    }


}
