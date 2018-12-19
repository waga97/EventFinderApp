package com.example.arshad.uea.Organizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.io.IOException;

public class OrganizerEventUpdate extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Toolbar orgeventupdateToolbar;

    private ImageView eventImage;
    private EditText eventName;
    private EditText eventDate;
    private EditText eventTime;
    private EditText eventVenue;
    private EditText eventScorun;
    private EditText eventDesc;
    private Button createBtn;

    private Uri postImageUri = null;

    private ProgressBar createProg;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private String event_id;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_event_update);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();
        event_id = getIntent().getStringExtra("event_id");

        firebaseFirestore.collection("Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String eventName = task.getResult().getString("name");

                orgeventupdateToolbar = (Toolbar) findViewById(R.id.org_event_update_toolbar);
                setSupportActionBar(orgeventupdateToolbar);
                getSupportActionBar().setTitle(eventName);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

        eventName = findViewById(R.id.event_name_et);
        eventDate = findViewById(R.id.event_date_et);
        eventTime = findViewById(R.id.event_time_et);
        eventVenue = findViewById(R.id.event_venue_et);
        eventScorun = findViewById(R.id.event_scorun_et);
        eventDesc = findViewById(R.id.event_desc_et);
        eventImage = findViewById(R.id.event_image);
        createBtn = findViewById(R.id.create_btn);
        createProg = findViewById(R.id.create_prog);

        createBtn.setEnabled(false);

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        firebaseFirestore.collection("Events").document(event_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        String name = task.getResult().getString("name");
                        String date = task.getResult().getString("date");
                        String time = task.getResult().getString("time");
                        String venue = task.getResult().getString("venue");
                        String scorun = task.getResult().getString("scorun");
                        String desc = task.getResult().getString("desc");
                        String image = task.getResult().getString("image_url");

                        postImageUri = Uri.parse(image);

                        eventName.setText(name);
                        eventDate.setText(date);
                        eventTime.setText(time);
                        eventVenue.setText(venue);
                        eventScorun.setText(scorun);
                        eventDesc.setText(desc);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.image_placeholder);

                        Glide.with(OrganizerEventUpdate.this).setDefaultRequestOptions(placeholderRequest).load(image).into(eventImage);


                    }

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(OrganizerEventUpdate.this, "Error : " + error, Toast.LENGTH_LONG).show();

                }


                createBtn.setEnabled(true);

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
                Intent backIntent = new Intent(OrganizerEventUpdate.this, OrganizerViewEvent.class);
                startActivity(backIntent);
                return true;
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            postImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), postImageUri);
                eventImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
