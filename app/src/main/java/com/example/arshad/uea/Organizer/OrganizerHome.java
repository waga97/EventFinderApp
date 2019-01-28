package com.example.arshad.uea.Organizer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arshad.uea.MainActivity;
import com.example.arshad.uea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrganizerHome extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private Button createBtn;
    private Button viewBtn;


    private Toolbar organizerToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_home);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid();

        firebaseFirestore.collection("Users").document(RegisteredUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String userName = task.getResult().getString("name");

                organizerToolbar = (Toolbar) findViewById(R.id.organizer_toolbar);
                setSupportActionBar(organizerToolbar);
                getSupportActionBar().setTitle(userName);
            }

        });

        createBtn = (Button) findViewById(R.id.create_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToCreate();
            }
        });

        viewBtn = (Button) findViewById(R.id.view_btn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToView();
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

        getMenuInflater().inflate(R.menu.organizer_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.organizer_logout:
                logout();
                Toast.makeText(OrganizerHome.this, "Successfully sign out" , Toast.LENGTH_LONG).show();
                return true;

            case R.id.organizer_account:

                Intent settingsIntent = new Intent(OrganizerHome.this, OrganizerAccount.class);
                startActivity(settingsIntent);
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

        Intent logoutintent = new Intent(OrganizerHome.this, MainActivity.class);
        startActivity(logoutintent);
        finish();
    }

    private void  sendToCreate (){

        Intent createintent = new Intent(OrganizerHome.this, OrganizerCreate.class);
        startActivity(createintent);
        finish();
    }

    private void  sendToView (){

        Intent viewintent = new Intent(OrganizerHome.this, OrganizerViewEvent.class);
        startActivity(viewintent);
        finish();
    }




}
