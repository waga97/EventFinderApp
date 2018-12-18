package com.example.arshad.uea;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class OrganizerCreate extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;



    private Toolbar organizercreateToolbar;

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

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id;

    private Bitmap compressedImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_create);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();

        organizercreateToolbar = (Toolbar) findViewById(R.id.organizercreate_toolbar);
        setSupportActionBar(organizercreateToolbar);
        getSupportActionBar().setTitle("Create Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventName = findViewById(R.id.event_name_et);
        eventDate = findViewById(R.id.event_date_et);
        eventTime = findViewById(R.id.event_time_et);
        eventVenue = findViewById(R.id.event_venue_et);
        eventScorun = findViewById(R.id.event_scorun_et);
        eventDesc = findViewById(R.id.event_desc_et);
        eventImage = findViewById(R.id.event_image);
        createBtn = findViewById(R.id.create_btn);
        createProg = findViewById(R.id.create_prog);

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = eventName.getText().toString();
                final String date = eventDate.getText().toString();
                final String time = eventTime.getText().toString();
                final String venue = eventVenue.getText().toString();
                final String scorun = eventScorun.getText().toString();
                final String desc = eventDesc.getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time) &&!TextUtils.isEmpty(venue) &&!TextUtils.isEmpty(scorun) &&!TextUtils.isEmpty(desc) && postImageUri != null){
                    createProg.setVisibility(View.VISIBLE);

                    StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                    ref.putFile(postImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String downloadUri = taskSnapshot.getDownloadUrl().toString();

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = currentUser.getUid();





                            final Map<String, Object> postMap = new HashMap<>();
                            postMap.put("image_url", downloadUri);
                            postMap.put("name", name);
                            postMap.put("date", date);
                            postMap.put("time", time);
                            postMap.put("venue", venue);
                            postMap.put("scorun", scorun);
                            postMap.put("desc", desc);
                            postMap.put("user_id", current_user_id);
                            postMap.put("timestamp", FieldValue.serverTimestamp());



                            firebaseFirestore.collection("Users").document(RegisteredUserID).collection("Events").add(postMap);



                            firebaseFirestore.collection("Events").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    if(task.isSuccessful()){



                                        Toast.makeText(OrganizerCreate.this, "Successfully created", Toast.LENGTH_LONG).show();
                                        Intent mainIntent = new Intent(OrganizerCreate.this, OrganizerHome.class);
                                        startActivity(mainIntent);
                                        finish();
                                    } else {
                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(OrganizerCreate.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                    createProg.setVisibility(View.INVISIBLE);
                                }
                            });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Error handling

                        }
                    });


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
                Intent backIntent = new Intent(OrganizerCreate.this, OrganizerHome.class);
                startActivity(backIntent);
                return true;
        }

    }
    private void  sendToMain (){

        Intent logoutintent = new Intent(OrganizerCreate.this, MainActivity.class);
        startActivity(logoutintent);
        finish();
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
